package com.teamidea.testtask.weatherparser;

import java.io.IOException;
import java.net.MalformedURLException;
import org.json.simple.parser.ParseException;

/**
 * Class with main method
 * 
 * @author Inna Kishinevskaya
 * 
 */
public class WParserView {
	/** Output constant */
	private static final String NOT_FOUND_TEXT = "not found";
	/** Output constant */
	private static final String LONGEST_DAY_TEXT = "The longest day";
	/** Output constant */
	private static final String DAY_WITH_MIN_DIFFERENCE_TEXT = "The day with minimal difference between real and feeling night temperature";
	/** Controller implements communication between view and logic classes */
	private WParserController wpController;

	/**
	 * Constructor sets controller
	 */
	public WParserView() {
		setWpController(new WParserController());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WParserView wpView;
		wpView = new WParserView();
		try {
			System.out.println("CITY: " + Settings.CITY.getData());
			String weatherApi = wpView.getWeatherApi();
			// parses Weather API JSON and fills WeatherDay objects list
			wpView.readJsonFromApi(weatherApi);
			String period = wpView.getWpController().getPeriod();
			System.out.println(String.format("PERIOD: %s", (period != null) ? period : NOT_FOUND_TEXT));
			// Calculates and outputs the day with minimal difference between real and
			// feeling like night temperatures
			wpView.outputDayWithMinDifference();
			// Calculates and outputs the longest day
			wpView.outputLongestDay();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets Weather API HTTPS address from settings with geographical coordinates
	 * data
	 * 
	 * @param wpView
	 * @return
	 * @throws ParseException
	 */
	public String getWeatherApi() throws ParseException {
		// gets Geocoding API HTTPS address from settings
		String geoApi = getGeoApiFromSettings();
		// gets Geocoding API JSON as string
		String jsonString = getJsonStringFromApi(geoApi);
		// gets formatted geographical coordinates of the city (latitude, longitude)
		String cityLanLon = getCityLatLon(jsonString);
		// gets Weather API HTTPS address from settings with geographical coordinates
		// data
		String weatherApi = getWeatherApiFromSettings(cityLanLon);
		return weatherApi;
	}

	/**
	 * Gets Weather API HTTPS address from settings with geographical coordinates
	 * data
	 * 
	 * @param cityLanLon formatted geographical coordinates of the city
	 * @return
	 */
	private String getWeatherApiFromSettings(String cityLanLon) {
		return String.format(Settings.WEATHER_API.getData(), cityLanLon, Settings.API_KEY.getData()).toString();
	}

	/**
	 * @param jsonString Geocoding API JSON as string
	 * @return formatted geographical coordinates of the city
	 * @throws ParseException
	 */
	private String getCityLatLon(String jsonString) throws ParseException {
		return getWpController().getCityLatLon(jsonString);
	}

	/**
	 * @return Geocoding API HTTPS address from settings
	 */
	private String getGeoApiFromSettings() {
		return String.format(Settings.GEO_API.getData(), Settings.CITY.getData(), Settings.API_KEY.getData())
				.toString();
	}

	/**
	 * Parses JSON and fills list of WeatherDay objects
	 * 
	 * @param httpsApi HTTPS Weather JSON
	 * @throws ParseException
	 */
	public void readJsonFromApi(String httpsApi) throws ParseException {
		String jsonString = getJsonStringFromApi(httpsApi);
		getWpController().parseJsonString(jsonString);
	}

	/**
	 * Calculates and outputs the longest day
	 */
	public void outputLongestDay() {
		WeatherDay wDay = getMaxLengthDay();
		System.out.println(LONGEST_DAY_TEXT);
		System.out.println((wDay != null) ? wDay.toString() : NOT_FOUND_TEXT);
	}

	/**
	 * Calculates and outputs the day with minimal difference between real and
	 * feeling like night temperatures
	 */
	public void outputDayWithMinDifference() {
		WeatherDay wDay = getMinDifferenceDay();
		System.out.println(DAY_WITH_MIN_DIFFERENCE_TEXT);
		System.out.println((wDay != null) ? wDay.toString() : NOT_FOUND_TEXT);
	}

	/**
	 * @return the longest day
	 */
	public WeatherDay getMaxLengthDay() {
		return getWpController().getMaxLengthDay();
	}

	/**
	 * @return the day with minimal difference between real and feeling like night
	 *         temperatures
	 */
	public WeatherDay getMinDifferenceDay() {
		return getWpController().getMinDifferenceDay();
	}

	/**
	 * @param httpsApi
	 * @return JSON from API as string
	 */
	private String getJsonStringFromApi(String httpsApi) {
		try {
			return getWpController().getJsonStringFromApi(httpsApi);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * @return WParserController
	 */
	public WParserController getWpController() {
		return wpController;
	}

	/**
	 * @param wpController
	 */
	public void setWpController(WParserController wpController) {
		this.wpController = wpController;
	}

}
