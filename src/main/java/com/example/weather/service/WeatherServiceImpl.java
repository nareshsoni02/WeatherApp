package com.example.weather.service;

import com.example.weather.WeatherAppProperties;
import com.example.weather.domain.Weather;
import com.example.weather.handler.OWMResponseErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

/**
 * Created by moksha on 03/07/2016.
 */
@Service
public class WeatherServiceImpl implements WeatherService{

	private static final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

	private final String apiUrl;

	private final RestTemplate restTemplate;

	private final String apiKey;

	public WeatherServiceImpl(RestTemplate restTemplate, WeatherAppProperties properties) {
		this.restTemplate = restTemplate;
		this.restTemplate.setErrorHandler(new OWMResponseErrorHandler());
		this.apiKey = properties.getApi().getKey();
		this.apiUrl = properties.getApi().getUrl();
	}

	@Cacheable("weather")
	public Weather getWeather(String city){
		logger.debug("Requesting current weather for {}", city);
		logger.debug("API key {} , url {}", apiKey, apiUrl);
		Weather weather = null;
		if(validParameters(city)) {
			URI url = new UriTemplate(this.apiUrl).expand(city, this.apiKey);

			weather = invoke(url, Weather.class);
		}
		return weather;
	}

	private boolean validParameters(String city) {
		return city !=null && !"".equals(city) && apiKey !=null && !"".equals(apiKey) && apiUrl!=null && !"".equals(apiUrl);
	}

	private <T> T invoke(URI url, Class<T> responseType){
		T weather = null;
		try {
			RequestEntity<?> request = RequestEntity.get(url)
					.accept(MediaType.APPLICATION_JSON).build();
			ResponseEntity<T> exchange = this.restTemplate
					.exchange(request, responseType);
			weather = exchange.getBody();
		} catch(Exception e){
				logger.error("An error occurred while calling openweathermap.org API endpoint:  " + e.getMessage());
		}

		return weather;
	}
}
