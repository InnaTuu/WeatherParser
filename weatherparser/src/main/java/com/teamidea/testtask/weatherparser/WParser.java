package com.teamidea.testtask.weatherparser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.ListIterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Implements logic of receiving JSON from API and processing of received data
 * 
 * @author Inna Kishinevskaya
 *
 */
public class WParser {
	private static final int DAYS_COUNT = 5;
	/**
	 * list of WeatherDay objects
	 */
	private ArrayList<WeatherDay> dayWeatherList = new ArrayList<WeatherDay>();

	/**
	 * Reads JSON from HTTPS API address as string
	 * 
	 * @param httpsApi HTTPS API address
	 * @return JSON from API as string
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public String getJsonStringFromApi(String httpsApi) throws MalformedURLException, IOException {
		URL url;
		url = new URL(httpsApi);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		InputStream responseStream = connection.getInputStream();
		Scanner scanner = new Scanner(responseStream);
		scanner.useDelimiter("\\A");
		String result = scanner.hasNext() ? scanner.next() : "";
		scanner.close();
		return result;
	}

	/**
	 * @return list of WeatherDay objects
	 */
	public ArrayList<WeatherDay> getDayWeatherList() {
		return dayWeatherList;
	}

	/**
	 * Fills WeatherDay list with data from JSON object
	 * 
	 * @param jsonObject
	 */
	private void parseJSONObject(JSONObject jsonObject) {
		JSONArray dayWeatherArray = (JSONArray) jsonObject.get(JsonTag.DAILY_TAG.getJsonTag());
		ListIterator<?> itr1 = dayWeatherArray.listIterator();
		int i = 0;
		while (i++ < DAYS_COUNT && itr1.hasNext()) {
			JSONObject next = (JSONObject) itr1.next();
			WeatherDay wDay = new WeatherDay(next);
			dayWeatherList.add(wDay);
		}
	}

	/**
	 * @return the WeatherDay object from WeatheDay objects list with the longest
	 *         time between sunrise and sunset
	 */
	public WeatherDay getMaxLengthDay() {
		if (dayWeatherList.size() == 0)
			return null;
		double maxLength = 0;
		int index = 0;
		for (int i = 0; i < dayWeatherList.size(); i++) {
			double length = dayWeatherList.get(i).getDayLength();
			if (length > maxLength) {
				maxLength = length;
				index = i;
			}
		}
		return dayWeatherList.get(index);
	}

	/**
	 * @return the WeatherDay object from WeatherDay objects list with minimal
	 *         difference between real and feeling like night temperatures
	 */
	public WeatherDay getMinDifferenceDay() {
		if (dayWeatherList.size() == 0)
			return null;
		double minDifference = dayWeatherList.get(0).getTemperatureDifference();
		int index = 0;
		for (int i = 0; i < dayWeatherList.size(); i++) {
			double difference = dayWeatherList.get(i).getTemperatureDifference();
			if (difference < minDifference) {
				minDifference = difference;
				index = i;
			}
		}
		return dayWeatherList.get(index);
	}

	/**
	 * Parses JSON data and fills WeatherDay list
	 * 
	 * @param jsonString JSON weather data
	 * @throws ParseException
	 */
	public void parseJsonString(String jsonString) throws ParseException {
		JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonString);
		parseJSONObject(jsonObject);
	}

	/**
	 * Gets formatted geographical coordinates of the city from Geocoding JSON
	 * 
	 * @param jsonString Geocoding API JSON as string
	 * @return formatted geographical coordinates of the city
	 * @throws ParseException
	 */
	public String getCityLatLon(String jsonString) throws ParseException {
		JSONArray jsonArray = (JSONArray) new JSONParser().parse(jsonString);
		JSONObject jsonObject = (JSONObject) jsonArray.listIterator().next();
		Double cityLatitude = (Double) jsonObject.get("lat");
		Double cityLongitude = (Double) jsonObject.get("lon");
		String cityLatLon = getFormattedLanLon(cityLatitude, cityLongitude);
		return cityLatLon;
	}

	/**
	 * @param cityLatitude  double latitude
	 * @param cityLongitude double longitude
	 * @return formatted geographical coordinates of the city as string
	 */
	private String getFormattedLanLon(Double cityLatitude, Double cityLongitude) {
		Formatter formatter = new Formatter();
		String cityLatLon = formatter.format(Settings.CITY_LAT_LON.getData(), cityLatitude, cityLongitude).toString();
		formatter.close();
		return cityLatLon;
	}

	/**
	 * 
	 * @return string period of dates
	 */
	public String getPeriod() {
		int size = dayWeatherList.size();
		if (size == 0)
			return null;
		return String.format("%tF - %tF", dayWeatherList.get(0).getDate(), dayWeatherList.get(size - 1).getDate());
	}

}
