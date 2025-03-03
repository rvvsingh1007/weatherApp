package com.example.weatherApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class WeatherClientResponse {

    private Location location;
    private Current current;
    private Forecast forecast;


    @Data
    public static class Location {
        private String name;
        private String country;
        private String tz_id;
        private String localtime;
    }

    @Data
    public static class Current {
        private float temp_f;
        private float wind_mph;
        private int humidity;
        private String last_updated;

        private Condition condition;

    }

    @Data
    public static class Condition {
        private String text;
        private String icon;
        private int code;
    }

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
}
