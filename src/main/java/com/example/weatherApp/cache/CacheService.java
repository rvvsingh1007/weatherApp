package com.example.weatherApp.cache;

import com.example.weatherApp.dto.ForecastData;
import com.example.weatherApp.dto.WeatherClientResponse;
import com.example.weatherApp.feign.WeatherClient;
import com.example.weatherApp.mapper.WeatherDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheService {
    private final WeatherClient weatherClient;

    @Value("${feign.client.config.weatherClient.apikey}")
    private String API_KEY;

    @Cacheable(value = "weatherCache", key = "#location + '_' + #days")
    public WeatherClientResponse getWeatherForecast(String location, int days) {
        log.info("Calling Weather Service for Location {}", location);
        return weatherClient.getWeather(API_KEY, location, days);
    }
}
