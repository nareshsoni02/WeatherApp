package com.example.weather.domain;

import javax.validation.constraints.NotNull;

/**
 * Created by moksha on 02/07/2016.
 */
public class WeatherSearchForm {
    @NotNull
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String toString() {
        return "WeatherSearchForm(City: " + this.city + ")";
    }
}
