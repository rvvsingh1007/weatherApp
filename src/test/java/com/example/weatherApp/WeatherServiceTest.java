package com.example.weatherApp;

import com.example.weatherApp.cache.CacheService;
import com.example.weatherApp.dto.Address;
import com.example.weatherApp.dto.ForecastData;
import com.example.weatherApp.dto.WeatherClientResponse;
import com.example.weatherApp.exception.InvalidInputException;
import com.example.weatherApp.mapper.WeatherDataMapper;
import com.example.weatherApp.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest extends BaseTest {

    @Mock
    private CacheService cacheService;
    @Mock
    private CacheManager cacheManager;
    @Spy
    private final WeatherDataMapper weatherDataMapper = Mappers.getMapper(WeatherDataMapper.class);;

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private Cache cache;

    @Test
    public void cacheHitScenarioUsingLocation() throws JsonProcessingException {
        Cache.ValueWrapper valueWrapper = mock(Cache.ValueWrapper.class);
        when(cacheManager.getCache(anyString())).thenReturn(cache);
        when(cache.get(any())).thenReturn(valueWrapper);
        WeatherClientResponse weatherClientResponse = generateWeatherClientResponse();
        when(cacheService.getWeatherForecast(anyString(), anyInt())).thenReturn(weatherClientResponse);
        ForecastData forecastData = weatherService.getWeatherForecast("San Jose", 3);
        verify(cacheManager).getCache(anyString());
        verify(cache).get(any());
        verify(cacheService).getWeatherForecast("San Jose", 3);
        assert(forecastData.getCity().equals("San Jose"));
        assert(forecastData.isCacheHit());
    }

    @Test
    public void cacheMissScenarioUsingLocation() throws JsonProcessingException {
        Cache.ValueWrapper valueWrapper = mock(Cache.ValueWrapper.class);
        when(cacheManager.getCache(anyString())).thenReturn(cache);
        when(cache.get(any())).thenReturn(null);
        WeatherClientResponse weatherClientResponse = generateWeatherClientResponse();
        when(cacheService.getWeatherForecast(anyString(), anyInt())).thenReturn(weatherClientResponse);
        ForecastData forecastData = weatherService.getWeatherForecast("San Jose", 3);
        verify(cacheManager).getCache(anyString());
        verify(cache).get(any());
        verify(cacheService).getWeatherForecast("San Jose", 3);
        assert(forecastData.getCity().equals("San Jose"));
        assert(!forecastData.isCacheHit());
    }

    @Test
    public void negativeCityAndZipNotSet() throws JsonProcessingException {
        Address address = new Address();
        address.setStreet("6229 Gunter Way");
        address.setCountry("USA");
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            weatherService.getWeatherForecast(address, 3);
        });
        assertEquals("City name and zipCode both cannot be empty", exception.getMessage());
    }

    @Test
    public void positiveCityOnlySet() throws JsonProcessingException {
        Address address = new Address();
        address.setStreet("6229 Gunter Way");
        address.setCity("San Jose");
        address.setCountry("USA");
        when(cacheManager.getCache(anyString())).thenReturn(cache);
        Cache.ValueWrapper valueWrapper = mock(Cache.ValueWrapper.class);
        when(cache.get(any())).thenReturn(valueWrapper);
        WeatherClientResponse weatherClientResponse = generateWeatherClientResponse();
        when(cacheService.getWeatherForecast(anyString(), anyInt())).thenReturn(weatherClientResponse);
        ForecastData forecastData = weatherService.getWeatherForecast(address, 3);
        verify(cacheManager).getCache(anyString());
        verify(cache).get(any());
        verify(cacheService).getWeatherForecast("San Jose", 3);
        assert(forecastData.getCity().equals("San Jose"));
        assert(forecastData.isCacheHit());
    }

    @Test
    public void positiveCityAndZipSet() throws JsonProcessingException {
        Address address = new Address();
        address.setStreet("6229 Gunter Way");
        address.setCity("San Jose");
        address.setZipCode(95123);
        address.setCountry("USA");
        when(cacheManager.getCache(anyString())).thenReturn(cache);
        Cache.ValueWrapper valueWrapper = mock(Cache.ValueWrapper.class);
        when(cache.get(any())).thenReturn(valueWrapper);
        WeatherClientResponse weatherClientResponse = generateWeatherClientResponse();
        when(cacheService.getWeatherForecast(anyString(), anyInt())).thenReturn(weatherClientResponse);
        ForecastData forecastData = weatherService.getWeatherForecast(address, 3);
        verify(cacheManager).getCache(anyString());
        verify(cache).get(any());
        verify(cacheService).getWeatherForecast("95123", 3);
        assert(forecastData.getCity().equals("San Jose"));
        assert(forecastData.isCacheHit());
    }

}
