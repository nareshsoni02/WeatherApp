package com.example.weather.controller;

import com.example.weather.domain.Weather;
import com.example.weather.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * Created by moksha on 03/07/2016.
 */
public class WeatherSummaryTest {
    private static final String CITY = "London";
    private WeatherSummary weatherSummary;
    private Weather weather;


    @Before
    public void setup() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        weather = mapper.readValue(new ClassPathResource("london.json", WeatherService.class).getInputStream(),
                Weather.class);
        weatherSummary = new WeatherSummary(CITY, weather);
    }

    @After
    public void tearDown() {
        weatherSummary = null;
        weather = null;
    }

    @Test
    public void verifyCreateWeatherSummaryObject(){
        assertNotNull(weatherSummary);
    }

    @Test
    public void verifyGetCity(){
        assertThat(weatherSummary.getCity()).isEqualTo(CITY);
    }

    @Test
    public void verifyGetCode(){
        assertThat(weatherSummary.getCode()).isEqualTo(802);
    }

    @Test
    public void verifyGetCountryCode(){
        assertThat(weatherSummary.getCountryCode()).isEqualTo("GB");
    }

    @Test
    public void verifyGetIcon(){
        assertThat(weatherSummary.getIcon()).isEqualTo("03d");
    }

    @Test
    public void verifyGetSearchedCity(){
        assertThat(weatherSummary.getSearchedCity()).isEqualTo("London");
    }

    @Test
    public void verifyGetSunrise(){
        assertThat(weatherSummary.getSunrise()).isEqualTo("04:49");
    }

    @Test
    public void verifyGetSunset(){
        assertThat(weatherSummary.getSunset()).isEqualTo("09:19");
    }

    @Test
    public void verifyGetTemperatureC(){
        assertThat(weatherSummary.getTemperatureC()).isEqualTo("19.90");
    }

    @Test
    public void verifyGetTemperatureF(){
        assertThat(weatherSummary.getTemperatureF()).isEqualTo("67.82");
    }

    @Test
    public void verifyGetTimestamp(){
        assertThat(weatherSummary.getTimestamp()).isEqualTo("2016.07.03 20:04");
    }

    @Test
    public void verifyGetWeatherDescription(){
        assertThat(weatherSummary.getWeatherDescription()).isEqualTo("scattered clouds");
    }

    @Test
    public void verifyGetWeatherMain(){
        assertThat(weatherSummary.getWeatherMain()).isEqualTo("Clouds");
    }

    @Test
    public void verifyGetFormattedDateTimeForNullDate(){
        String pattern = "hh:mm";
        Instant nullInstant = null;
        assertThat(weatherSummary.getFormattedDateTime(nullInstant,pattern)).isEqualTo("");
    }

    @Test
    public void verifyGetFormattedDateTimeForEmptyPattern(){
        String emptyPattern = "";
        Instant instant = Instant.parse("2016-10-23T10:12:35Z");
        assertThat(weatherSummary.getFormattedDateTime(instant,emptyPattern)).isEqualTo("");
    }

    @Test
    public void verifyGetFormattedDateTimeForNullPattern(){
        String emptyPattern = "";
        Instant instant = Instant.parse("2016-10-23T10:12:35Z");
        assertThat(weatherSummary.getFormattedDateTime(instant,emptyPattern)).isEqualTo("");
    }

    @Test
    public void verifyGetFormattedDateTimeForProperDateAndPattern(){
        String sunRiseSetTimepattern = "hh:mm";
        Instant instant = Instant.parse("2016-10-23T10:12:35Z");
        assertThat(weatherSummary.getFormattedDateTime(instant,sunRiseSetTimepattern)).isEqualTo("11:12");
    }

    @Test
    public void verifyCelsiusToFahrenheitForZeroTemperature(){
        double temperature = 0.0;
        assertThat(weatherSummary.celsiusToFahrenheit(temperature)).isEqualTo(32.0);
    }

    @Test
    public void verifyCelsiusToFahrenheitForProperTemperature(){
        double temperature = 19.75;
        assertThat(weatherSummary.celsiusToFahrenheit(temperature)).isEqualTo(67.55);
    }

    @Test
    public void verifyGetTimeInInstantFormatForZeroValue(){
        long timestamp = 0L;
        Instant expected = Instant.parse("1970-01-01T00:00:00Z");
        assertThat(weatherSummary.getTimeInInstantFormat(timestamp)).isEqualTo(expected);
    }

    @Test
    public void verifyGetTimeInInstantFormatForProperValue(){
        long timestamp = 1467572673L;
        Instant expected = Instant.parse("2016-07-03T19:04:33Z");
        assertThat(weatherSummary.getTimeInInstantFormat(timestamp)).isEqualTo(expected);
    }

    @Test
    public void verifyKelvinToCelsiusForZeroTemperature(){
        double temperature = 0.0;
        assertThat(weatherSummary.kelvinToCelsius(temperature)).isEqualTo(-273.0);
    }

    @Test
    public void verifyKelvinToCelsiusForProperTemperature(){
        double temperature = 292.9;
        assertThat(weatherSummary.kelvinToCelsius(temperature)).isEqualTo(19.899999999999977);
    }

    @Test
    public void verifyGetTemperatureDigitFormattingForZeroTemperature(){
        double temperature = 0.0;
        assertThat(weatherSummary.getTemperatureDigitFormatting(temperature)).isEqualTo("0.00");
    }

    @Test
    public void verifyGetTemperatureDigitFormattingForProperTemperature(){
        double temperature = 19.899999999999977;
        assertThat(weatherSummary.getTemperatureDigitFormatting(temperature)).isEqualTo("19.90");
    }
}