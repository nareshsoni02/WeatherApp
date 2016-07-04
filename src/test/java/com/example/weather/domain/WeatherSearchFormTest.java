package com.example.weather.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * Created by moksha on 04/07/2016.
 */
public class WeatherSearchFormTest {
    private WeatherSearchForm weatherSearchForm;
    private String city = "London";

    @Before
    public void setup() throws Exception {
        weatherSearchForm = new WeatherSearchForm();
        weatherSearchForm.setCity(city);
    }

    @After
    public void tearDown() {
        weatherSearchForm = null;
    }

    @Test
    public void verifyCreateWeatherSearchFormObject(){
        assertNotNull(weatherSearchForm);
    }

    @Test
    public void verifyCity(){
        assertThat(weatherSearchForm.getCity()).isEqualTo(city);
    }
}