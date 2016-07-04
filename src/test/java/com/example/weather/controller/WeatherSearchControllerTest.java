package com.example.weather.controller;

import com.example.weather.WeatherAppProperties;
import com.example.weather.domain.Weather;
import com.example.weather.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by moksha on 04/07/2016.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(WeatherSearchController.class)
public class WeatherSearchControllerTest {
    private static final String CITY = "London";
    private Weather weather;

    @MockBean
    private WeatherService weatherService;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setup() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        weather = mapper.readValue(new ClassPathResource("london.json", WeatherService.class).getInputStream(),
                Weather.class);
    }

    @After
    public void tearDown() {
        weather = null;
    }

    @Test
    public void verifyCreateWeatherSearchControllerObject(){
        WeatherSearchController weatherSearchController = new WeatherSearchController(weatherService,new WeatherAppProperties());
        assertNotNull(weatherSearchController);
    }


    @Test
    public void verifyShowWeatherSearchFormInvocation() throws Exception {
        given(this.weatherService.getWeather(CITY)).willReturn(weather);
        this.mvc.perform(get("/weather_search"))
                .andExpect(status().isOk()).andExpect(view().name("weather_search"));

        verifyNoMoreInteractions(weatherService);

    }

    @Test
    public void verifySearchWeatherInvocation() throws Exception {
        given(this.weatherService.getWeather(CITY)).willReturn(weather);

        this.mvc.perform(post("/weather_search")
                .param("city", CITY))
                .andExpect(status().isOk())
                .andExpect(view().name("weather_summary"))
                .andExpect(model().attributeExists("weather_summary"))
                .andExpect(model().attribute("weather_summary", hasSize(1)));

        verify(this.weatherService).getWeather(CITY);
    }

    @Test
    public void verifySearchWeatherInvocationForEmptyCity() throws Exception {
        given(this.weatherService.getWeather(CITY)).willReturn(weather);

        String emptyCity = "";
        this.mvc.perform(post("/weather_search")
                .param("city", emptyCity))
                .andExpect(status().isOk())
                .andExpect(view().name("weather_search"))
                .andExpect(model().attributeDoesNotExist("weather_summary"));

        verifyNoMoreInteractions(weatherService);
    }

    @Test
    public void verifyNullResponseFromWeatherService() throws Exception {
        given(this.weatherService.getWeather(CITY)).willReturn(null);

        this.mvc.perform(post("/weather_search")
                .param("city", CITY))
                .andExpect(status().isOk())
                .andExpect(view().name("weather_search"))
                .andExpect(model().attributeDoesNotExist("weather_summary"));

        verify(this.weatherService).getWeather(CITY);
    }
}