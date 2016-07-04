package com.example.weather.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by moksha on 03/07/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather implements Serializable {

	private String name;

	private long timestamp;

	private double temperature;

	private Integer weatherId;

	private String weatherIcon;

	private String weatherMain;

	private String weatherDescription;

	private String countryCode;

	private long sunrise;

	private long sunset;

	@JsonProperty("name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("timestamp")
	public long getTimestamp() {
		return this.timestamp;
	}

	@JsonSetter("dt")
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public double getTemperature() {
		return this.temperature;
	}

	public void setTemperature(double temperatureKelvin) {
		this.temperature = temperatureKelvin;
	}

	@JsonProperty("main")
	public void setMain(Map<String, Object> main) {
		double kelvinTemp = Double.parseDouble(main.get("temp").toString());
		setTemperature(kelvinTemp);
	}

	public Integer getWeatherId() {
		return this.weatherId;
	}

	public void setWeatherId(Integer weatherId) {
		this.weatherId = weatherId;
	}

	public String getWeatherIcon() {
		return this.weatherIcon;
	}

	public void setWeatherIcon(String weatherIcon) {
		this.weatherIcon = weatherIcon;
	}

	public String getWeatherMain() {
		return weatherMain;
	}

	public void setWeatherMain(String weatherMain) {
		this.weatherMain = weatherMain;
	}

	public String getWeatherDescription() {
		return weatherDescription;
	}

	public void setWeatherDescription(String weatherDescription) {
		this.weatherDescription = weatherDescription;
	}

	@JsonProperty("weather")
	public void setWeather(List<Map<String, Object>> weatherEntries) {
		Map<String, Object> weather = weatherEntries.get(0);
		setWeatherId((Integer) weather.get("id"));
		setWeatherIcon((String) weather.get("icon"));
		setWeatherMain((String) weather.get("main"));
		setWeatherDescription((String) weather.get("description"));
	}

	@JsonProperty("sunrise")
	public long getSunrise() {
		return this.sunrise;
	}

	@JsonSetter("sunrise")
	public void setSunrise(long timestamp) {
		this.sunrise = timestamp;
	}

	@JsonProperty("sunset")
	public long getSunset() {
		return this.sunset;
	}

	@JsonSetter("sunset")
	public void setSunset(long timestamp) {
		this.sunset = timestamp;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@JsonProperty("sys")
	public void setSys(Map<String, Object> sys) {
		setCountryCode((String) sys.get("country"));
		setSunrise((int)sys.get("sunrise"));
		setSunset((int)sys.get("sunset"));

	}
}
