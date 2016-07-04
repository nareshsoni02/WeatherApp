package com.example.weather.service;

import com.example.weather.WeatherAppProperties;
import com.example.weather.domain.Weather;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

/**
 * Created by moksha on 03/07/2016.
 */
public class WeatherServiceImplTest {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    private static final String QUERY_PARAM = "weather?q={city}&APPID={key}";

    private WeatherService weatherService;

    private MockRestServiceServer server;

    private static final String KEY = "test-ABC";

    private static final String CITY = "London";

    private RestTemplate restTemplate;

    @Before
    public void setup() {
        restTemplate = new RestTemplate();
        intializeWeatherService(KEY,BASE_URL+QUERY_PARAM);
        this.server = MockRestServiceServer.createServer(restTemplate);
    }

    @After
    public void tearDown() {
        restTemplate = null;
        this.weatherService = null;
        this.server = null;
    }

    @Test
    public void verifyCreateWeatherServiceImplObject(){
        assertNotNull(weatherService);
    }

    @Test
    public void verifyGetWeatherForUnauthorizedError(){
        this.server.expect(requestTo(BASE_URL + "weather?q="+CITY+"&APPID="+KEY)).andRespond(
                withStatus(HttpStatus.UNAUTHORIZED));
        Weather weather = this.weatherService.getWeather(CITY);
        assertNull(weather);
        this.server.verify();
    }

    @Test
    public void verifyGetWeatherFor404Error(){
        this.server.expect(requestTo(BASE_URL + "weather?q="+CITY+"&APPID="+KEY)).andRespond(
                withStatus(HttpStatus.NOT_FOUND));
        Weather weather = this.weatherService.getWeather(CITY);
        assertNull(weather);
        this.server.verify();
    }

    @Test
    public void verifyGetWeatherFor500Error(){
        this.server.expect(requestTo(BASE_URL + "weather?q="+CITY+"&APPID="+KEY)).andRespond(
                withServerError());
        Weather weather = this.weatherService.getWeather(CITY);
        assertNull(weather);
        this.server.verify();
    }

    @Test
    public void verifyGetWeatherForEmptyCity(){
        this.server.expect(requestTo(BASE_URL + "weather?q="+CITY+"&APPID="+KEY)).andRespond(
                withSuccess(new ClassPathResource("london.json", getClass()), MediaType.APPLICATION_JSON));
        String emptyCity = "";
        Weather weather = this.weatherService.getWeather(emptyCity);
        assertNull(weather);
    }

    @Test
    public void verifyGetWeatherForNullCity(){
        this.server.expect(requestTo(BASE_URL + "weather?q="+CITY+"&APPID="+KEY)).andRespond(
                withSuccess(new ClassPathResource("london.json", getClass()), MediaType.APPLICATION_JSON));
        String nullCity = null;
        Weather weather = this.weatherService.getWeather(nullCity);
        assertNull(weather);
    }

    @Test
    public void verifyGetWeatherForEmptyAPIUrl(){
        String emptyUrl = "";
        String key = KEY;
        intializeWeatherService(key,emptyUrl);
        this.server.expect(requestTo(BASE_URL + "weather?q="+CITY+"&APPID="+KEY)).andRespond(
                withSuccess(new ClassPathResource("london.json", getClass()), MediaType.APPLICATION_JSON));

        Weather weather = this.weatherService.getWeather(CITY);

        assertNull(weather);
    }

    @Test
    public void verifyGetWeatherForNullAPIUrl(){
        String nullUrl = null;
        String key = KEY;
        intializeWeatherService(key,nullUrl);
        this.server.expect(requestTo(BASE_URL + "weather?q="+CITY+"&APPID="+KEY)).andRespond(
                withSuccess(new ClassPathResource("london.json", getClass()), MediaType.APPLICATION_JSON));

        Weather weather = this.weatherService.getWeather(CITY);

        assertNull(weather);
    }

    @Test
    public void verifyGetWeatherForEmptyAPIKey(){
        String emmptyKey = "";
        String url = BASE_URL + QUERY_PARAM;
        intializeWeatherService(emmptyKey,url);
        this.server.expect(requestTo(BASE_URL + "weather?q="+CITY+"&APPID="+KEY)).andRespond(
                withSuccess(new ClassPathResource("london.json", getClass()), MediaType.APPLICATION_JSON));

        Weather weather = this.weatherService.getWeather(CITY);

        assertNull(weather);
    }

    @Test
    public void verifyGetWeatherForNullAPIKey(){
        String emmptyKey = null;
        String url = BASE_URL + QUERY_PARAM;
        intializeWeatherService(emmptyKey, url);
        this.server.expect(requestTo(BASE_URL + "weather?q="+CITY+"&APPID="+KEY)).andRespond(
                withSuccess(new ClassPathResource("london.json", getClass()), MediaType.APPLICATION_JSON));

        Weather weather = this.weatherService.getWeather(CITY);

        assertNull(weather);
    }

    private void intializeWeatherService(String Key, String url) {
        WeatherAppProperties properties = new WeatherAppProperties();
        properties.getApi().setKey(Key);
        properties.getApi().setUrl(url);
        this.weatherService = new WeatherServiceImpl(restTemplate, properties);
    }

    @Test
    public void verifyGetWeatherIsWorkingForProperInput() throws Exception{
        this.server.expect(requestTo(BASE_URL + "weather?q="+CITY+"&APPID="+KEY)).andRespond(
                withSuccess(new ClassPathResource("london.json", getClass()), MediaType.APPLICATION_JSON));
        Weather weather = this.weatherService.getWeather(CITY);
        assertThat(weather.getName()).isEqualTo(CITY);
        assertThat(weather.getCountryCode()).isEqualTo("GB");
        assertThat(weather.getSunrise()).isEqualTo(1467517798L);
        assertThat(weather.getSunset()).isEqualTo(1467577173L);
        assertThat(weather.getTemperature()).isEqualTo(292.9);
        assertThat(weather.getTimestamp()).isEqualTo(1467572673L);
        assertThat(weather.getWeatherDescription()).isEqualTo("scattered clouds");
        assertThat(weather.getWeatherIcon()).isEqualTo("03d");
        assertThat(weather.getWeatherId()).isEqualTo(802);
        assertThat(weather.getWeatherMain()).isEqualTo("Clouds");
        this.server.verify();
    }
}