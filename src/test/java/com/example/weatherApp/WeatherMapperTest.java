package com.example.weatherApp;

import com.example.weatherApp.dto.ForecastData;
import com.example.weatherApp.mapper.WeatherDataMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WeatherMapperTest extends BaseTest {

    private final WeatherDataMapper weatherDataMapperMapper = Mappers.getMapper(WeatherDataMapper.class);



    @Test
    public void mapClientResponseToForecast() throws JsonProcessingException {
        ForecastData forecastData = weatherDataMapperMapper.mapToForecast(generateWeatherClientResponse());
        String timeForTimeZone = weatherDataMapperMapper.calculateLocalTime(forecastData.getTimeZone());
        assert(forecastData.getCity().equals("San Jose"));
        assert(forecastData.getLocaltime().equals(timeForTimeZone));
    }


}
