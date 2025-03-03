package com.example.weatherApp.config;

import com.example.weatherApp.mapper.WeatherDataMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public WeatherDataMapper weatherDataMapper() {
        return Mappers.getMapper(WeatherDataMapper.class);
    }
}
