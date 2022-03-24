package com.teamidea.testtask.weatherparser;

import java.util.Date;

import org.json.simple.JSONObject;

/**
 * Objects of this class contains weather information from json object
 * 
 * @author Inna Kishinevskaya
 *
 */
public class WeatherDay {
	private static final String STRING_VIEW = "DATE: %tF; LENGTH OF A DAY: %.2f hours; DIFFERENCE BETWEEN REAL AND FEELING LIKE NIGHT TEMPERATURE %.2f °C";
	private long longDate;
	private double realNightTemperature;
	private double feelsLikeNightTemperature;
	private double temperatureDifference;
	private long sunriseTime;
	private long sunsetTime;
	private double dayLength;

	/**
	 * @param jsonObject
	 */
	public WeatherDay(JSONObject jsonObject) {
		longDate = (Long) jsonObject.get(JsonTag.DATE_TAG.getJsonTag());
		realNightTemperature = getNightTemperature(jsonObject, JsonTag.TEMPERATURE_TAG.getJsonTag());
		feelsLikeNightTemperature = getNightTemperature(jsonObject, JsonTag.FEELS_LIKE_TAG.getJsonTag());
		sunriseTime = (Long) jsonObject.get(JsonTag.SUNRISE_TAG.getJsonTag());
		sunsetTime = (Long) jsonObject.get(JsonTag.SUNSET_TAG.getJsonTag());
		dayLength = (sunsetTime - sunriseTime) / 3600.0;
		temperatureDifference = Math.abs(realNightTemperature - feelsLikeNightTemperature);
	}

	public long getDoubleDate() {
		return longDate;
	}

	public void setDoubleDate(long doubleDate) {
		this.longDate = doubleDate;
	}

	public Date getDate() {
		return new Date(longDate * 1000);
	}

	public double getRealNightTemperature() {
		return realNightTemperature;
	}

	public void setRealNightTemperature(double realNightTemperature) {
		this.realNightTemperature = realNightTemperature;
	}

	public double getFeelsLikeNightTemperature() {
		return feelsLikeNightTemperature;
	}

	public void setFeelsLikeNightTemperature(double feelsLikeNightTemperature) {
		this.feelsLikeNightTemperature = feelsLikeNightTemperature;
	}

	public long getSunriseTime() {
		return sunriseTime;
	}

	public void setSunriseTime(long sunriseTime) {
		this.sunriseTime = sunriseTime;
	}

	public long getSunsetTime() {
		return sunsetTime;
	}

	public void setSunsetTime(long sunsetTime) {
		this.sunsetTime = sunsetTime;
	}

	public double getDayLength() {
		return dayLength;
	}

	public void setDayLength(double dayLength) {
		this.dayLength = dayLength;
	}

	private Double getNightTemperature(JSONObject jsonObject, String tag) {
		JSONObject tempObject = (JSONObject) jsonObject.get(tag);
		Double nightTemperature = (Double) tempObject.get(JsonTag.NIGHT_TAG.getJsonTag());
		return nightTemperature;
	}

	public double getTemperatureDifference() {
		return temperatureDifference;
	}

	public void setTemperatureDifference(double temperatureDifference) {
		this.temperatureDifference = temperatureDifference;
	}

	public String toString() {
		return String.format(STRING_VIEW, getDate(), dayLength, temperatureDifference);
	}
}
