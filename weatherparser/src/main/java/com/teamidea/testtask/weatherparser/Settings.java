package com.teamidea.testtask.weatherparser;

public enum Settings {
	WEATHER_API(
			"https://api.openweathermap.org/data/2.5/onecall?%s&exclude=current,minutely,hourly,alerts&units=metric&appid=%s"),
	GEO_API("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s"),
	API_KEY("499660b1638e0853141b46e6a3f5df5a"), CITY("Ulyanovsk"), CITY_LAT_LON("lat=%f&lon=%f");

	private String data;

	private Settings(String data) {
		this.setData(data);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
