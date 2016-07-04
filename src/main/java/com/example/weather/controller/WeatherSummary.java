package com.example.weather.controller;

import com.example.weather.domain.Weather;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Created by moksha on 03/07/2016.
 */
class WeatherSummary {
	private static final String TIME_PATTERN = "hh:mm";

	private final String searchedCity;

	private final String city;

	private final Integer code;

	private final String icon;

	private final String temperatureC;

	private final String temperatureF;

	private String weatherMain;

	private String weatherDescription;

	private String countryCode;

	private String sunrise;

	private String sunset;

	private String timestamp;

	WeatherSummary(String searchedCity, Weather weather) {
		this.searchedCity = searchedCity;
		this.city = weather.getName();
		this.code = weather.getWeatherId();
		this.icon = weather.getWeatherIcon();
		double temperatureCelsius = kelvinToCelsius(weather.getTemperature());
		this.temperatureC = getTemperatureDigitFormatting(temperatureCelsius);
		this.temperatureF = getTemperatureDigitFormatting(celsiusToFahrenheit(temperatureCelsius));
		this.weatherMain = weather.getWeatherMain();
		this.weatherDescription = weather.getWeatherDescription();
		this.countryCode = weather.getCountryCode();
		this.sunrise =  getFormattedDateTime(getTimeInInstantFormat(weather.getSunrise()), TIME_PATTERN);
		this.sunset = getFormattedDateTime(getTimeInInstantFormat(weather.getSunset()), TIME_PATTERN);
		this.timestamp = getFormattedDateTime(getTimeInInstantFormat(weather.getTimestamp()),"yyyy.MM.dd HH:mm");
	}

	public String getSearchedCity() {
		return this.searchedCity;
	}

	public String getCity() {
		return this.city;
	}


	public Integer getCode() {
		return this.code;
	}

	public String getIcon() {
		return this.icon;
	}

	public String getTemperatureC() {
		return this.temperatureC;
	}

	public String getTemperatureF() {
		return this.temperatureF;
	}

	public String getWeatherMain() {
		return weatherMain;
	}

	public String getWeatherDescription() {
		return weatherDescription;
	}

	public String getSunrise() {
		return this.sunrise;
	}

	public String getSunset() {
		return this.sunset;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public String getTimestamp() {
		return this.timestamp;
	}

	protected String getFormattedDateTime(Instant timeStamp, String pattern) {
		String formattedString = "";
		if(timeStamp!=null && pattern!=null && !"".equals(pattern)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
			LocalDateTime ldt = LocalDateTime.ofInstant(timeStamp, ZoneId.systemDefault());
			formattedString = ldt.format(formatter);
		}
		return formattedString;
	}

	protected double celsiusToFahrenheit(double temperature) {
		return  (temperature * 9/5.0) +32;
	}

	protected Instant getTimeInInstantFormat(long sunriseTimestamp) {
		return Instant.ofEpochMilli(sunriseTimestamp * 1000);
	}

	protected double kelvinToCelsius(double temperatureKelvin) {
		return temperatureKelvin - 273.0;
	}

	protected String getTemperatureDigitFormatting(double temperature) {
		return String.format("%4.2f", temperature);
	}

}
