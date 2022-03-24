package com.teamidea.testtask.weatherparser;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.parser.ParseException;

/**
 * Controller implements communication between view and logic classes
 * 
 * @author Inna Kishinevskaya
 * 
 */
public class WParserController {
	// WParser implements logic of receiving JSON from API and processing of
	// received data
	private WParser wParser;

	/**
	 * Constructor sets parser
	 */
	public WParserController() {
		wParser = new WParser();
	}

	/**
	 * @return WParser
	 */
	public WParser getWParser() {
		return wParser;
	}

	/**
	 * @param wParser
	 */
	public void setWParser(WParser wParser) {
		this.wParser = wParser;
	}

	/**
	 * @param httpsApi HTTPS API address
	 * @return JSON from API as string
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public String getJsonStringFromApi(String httpsApi) throws MalformedURLException, IOException {
		return wParser.getJsonStringFromApi(httpsApi);
	}

	/**
	 * @return the day with minimal difference between real and feeling like
	 *         temperatures
	 */
	public WeatherDay getMinDifferenceDay() {
		return wParser.getMinDifferenceDay();
	}

	/**
	 * @return the longest day
	 */
	public WeatherDay getMaxLengthDay() {
		return wParser.getMaxLengthDay();
	}

	/**
	 * Parses JSON data and fills WeatherDay list
	 * 
	 * @param jsonString JSON weather data as string
	 * @throws ParseException
	 */
	public void parseJsonString(String jsonString) throws ParseException {
		wParser.parseJsonString(jsonString);
	}

	/**
	 * @param jsonString Geocoding API JSON as string
	 * @return formatted geographical coordinates of the city
	 * @throws ParseException
	 */
	public String getCityLatLon(String jsonString) throws ParseException {
		return wParser.getCityLatLon(jsonString);
	}

	/**
	 * 
	 * @return string period of dates
	 */
	public String getPeriod() {
		return wParser.getPeriod();
	}

}
