package com.example.weatherApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class ForecastData {

    private String city;
    private String country;
    private float currentTemp;
    private float maxTemp;
    private float minTemp;
    private final String unit = "Fahrenheit";
    private String localtime;
    private String timeZone;
    private boolean cacheHit;
    private Forecast forecast;


    @Data
    public static class Forecast {
        private List<ForecastDay> forecastday;
    }

    @Data
    public static class ForecastDay {
        private String date;
        private Day day;
        private Astro astro;
    }

    @Data
    public static class Day {
        private float maxtemp_f;
        private float mintemp_f;
        private float maxwind_mph;
        private float totalprecip_mm;
        private Condition condition;
    }

    @Data
    public static class Astro {
        private String sunrise;
        private String sunset;
        private String moonrise;
        private String moonset;
    }
    @Data
    public static class Condition {
        private String text;
        private String icon;
        private int code;
    }
}
