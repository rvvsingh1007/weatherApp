package com.example.weatherApp.service;

import com.example.weatherApp.cache.CacheService;
import com.example.weatherApp.dto.Address;
import com.example.weatherApp.dto.ForecastData;
import com.example.weatherApp.dto.WeatherClientResponse;
import com.example.weatherApp.exception.InvalidInputException;
import com.example.weatherApp.feign.WeatherClient;
import com.example.weatherApp.mapper.WeatherDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final CacheService cacheService;
    private final CacheManager cacheManager;
    private final WeatherDataMapper weatherDataMapper;

    public ForecastData getWeatherForecast(String location, int days) {
        Cache cache = cacheManager.getCache("weatherCache");
        boolean cacheHit = false;
        var cacheResponse = cache.get(location + "_" + days);
        if(cacheResponse != null) {
            cacheHit = true;
        }
        ForecastData forecastData = weatherDataMapper.mapToForecast(cacheService.getWeatherForecast(location, days));
        forecastData.setCacheHit(cacheHit);
        return forecastData;
    }

    public ForecastData getWeatherForecast(Address address, int days) {
        if(StringUtils.isBlank(address.getCity()) && address.getZipCode() == null) {
            throw new InvalidInputException("City name and zipCode both cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if(address.getZipCode() != null) {
            return getWeatherForecast(String.valueOf(address.getZipCode()), days);
        }
        return getWeatherForecast(address.getCity(), days);
    }
}
