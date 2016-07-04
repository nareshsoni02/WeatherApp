package com.example.weather.domain;

import com.example.weather.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * Created by moksha on 04/07/2016.
 */
public class WeatherTest {
    private Weather weather;

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
    public void verifyCreateWeatherObject(){
        assertNotNull(weather);
    }

    @Test
    public void verifyGetName(){
        assertThat(weather.getName()).isEqualTo("London");
    }

    @Test
    public void verifyGetCountryCode(){
        assertThat(weather.getCountryCode()).isEqualTo("GB");
    }

    @Test
    public void verifyGetWeatherIcon(){
        assertThat(weather.getWeatherIcon()).isEqualTo("03d");
    }

    @Test
    public void verifyGetSunrise(){
        assertThat(weather.getSunrise()).isEqualTo(1467517798L);
    }

    @Test
    public void verifyGetSunset(){
        assertThat(weather.getSunset()).isEqualTo(1467577173L);
    }

    @Test
    public void verifyGetTemperature(){
        assertThat(weather.getTemperature()).isEqualTo(292.9);
    }

    @Test
    public void verifyGetTimestamp(){
        assertThat(weather.getTimestamp()).isEqualTo(1467572673L);
    }

    @Test
    public void verifyGetWeatherDescription(){
        assertThat(weather.getWeatherDescription()).isEqualTo("scattered clouds");
    }

    @Test
    public void verifyGetWeatherId(){
        assertThat(weather.getWeatherId()).isEqualTo(802);
    }

    @Test
    public void verifyGetWeatherMain(){
        assertThat(weather.getWeatherMain()).isEqualTo("Clouds");
    }
}