package com.teamidea.testtask.weatherparser;

public enum JsonTag {
	DAILY_TAG("daily"), NIGHT_TAG("night"), DATE_TAG("dt"), TEMPERATURE_TAG("temp"), FEELS_LIKE_TAG("feels_like"),
	SUNRISE_TAG("sunrise"), SUNSET_TAG("sunset"), LATITUDE_TAG("lat"), LONGITUDE_TAG("lon");

	private String jsonTag;

	private JsonTag(String jsonTag) {
		this.setJsonTag(jsonTag);
	}

	public String getJsonTag() {
		return jsonTag;
	}

	public void setJsonTag(String jsonTag) {
		this.jsonTag = jsonTag;
	}

}
