package com.example.weather.controller;

import com.example.weather.WeatherAppProperties;
import com.example.weather.domain.Weather;
import com.example.weather.domain.WeatherSearchForm;
import com.example.weather.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by moksha on 03/07/2016.
 */
@Controller
public class WeatherSearchController{
	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherSearchController.class);

	private final WeatherService weatherService;

	private final WeatherAppProperties properties;

	public WeatherSearchController(WeatherService weatherService, WeatherAppProperties properties) {
		this.weatherService = weatherService;
		this.properties = properties;
	}

	@RequestMapping(value = "/weather_search", method = RequestMethod.GET)
	public ModelAndView showWeatherSearchForm() {
		LOGGER.debug("Received request for weather search view");
		return new ModelAndView("weather_search", "searchForm", new WeatherSearchForm());
	}

	@RequestMapping(value = "/weather_search", method = RequestMethod.POST)
	public ModelAndView searchWeather(@Valid @ModelAttribute("searchForm") WeatherSearchForm form, BindingResult bindingResult) {
		LOGGER.debug("Received request to search weather {}, with result={}", form, bindingResult);
		String view = "weather_search";
		ModelMap model = new ModelMap();

		if (!"".equals(form.getCity())) {
			List<WeatherSummary> weatherSummaryList = getSummary(form.getCity());
			if (weatherSummaryList != null && weatherSummaryList.size() > 0) {
				view = "weather_summary";
				model.addAttribute("weather_summary", weatherSummaryList);
			}
		}
		return new ModelAndView(view, model);
	}

	protected List<WeatherSummary> getSummary(String city){
		List<WeatherSummary> summary = new ArrayList<>();
		Weather weather = this.weatherService.getWeather(city);
		if(weather!=null) {
			summary.add(new WeatherSummary(city, weather));
		}
		return summary;
	}
}
