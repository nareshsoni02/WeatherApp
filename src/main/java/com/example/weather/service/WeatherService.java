package com.example.weather.service;

import com.example.weather.domain.Weather;

/**
 * Created by moksha on 03/07/2016.
 */
public interface WeatherService {
	public Weather getWeather(String city);
}
