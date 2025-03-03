package com.example.weatherApp;

import com.example.weatherApp.cache.CacheService;
import com.example.weatherApp.dto.WeatherClientResponse;
import com.example.weatherApp.feign.WeatherClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class CacheServiceTest extends BaseTest {

    @Mock
    private WeatherClient weatherClient;

    @Autowired
    private CacheService cacheService;

    private final String location = "San Jose";
    private final int days = 3;


    @Test
    void testCacheMissAndHit() throws JsonProcessingException {
        ReflectionTestUtils.setField(cacheService, "API_KEY", "test-api-key");
        ReflectionTestUtils.setField(cacheService, "weatherClient", weatherClient);
        // Mock cached response
        WeatherClientResponse cachedResponse = generateWeatherClientResponse();

        when(weatherClient.getWeather("test-api-key", location, days)).thenReturn(cachedResponse);

        // Call the method for the first time
        WeatherClientResponse response = cacheService.getWeatherForecast(location, days);

        // Assertions
        assertNotNull(response);
        response = cacheService.getWeatherForecast(location, days);//second call
        assertNotNull(response);

        // Verify that the API call is only made once
        verify(weatherClient).getWeather(any(), any(), anyInt());
    }

}
