package com.example.weatherApp.mapper;

import com.example.weatherApp.dto.ForecastData;
import com.example.weatherApp.dto.WeatherClientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Mapper
public interface WeatherDataMapper {

    @Mapping(target = "currentTemp", source = "weatherClientResponse.current.temp_f")
    @Mapping(target = "maxTemp", source = "weatherClientResponse.forecast", qualifiedByName = "extractMaxTemp")
    @Mapping(target = "minTemp", source = "weatherClientResponse.forecast", qualifiedByName = "extractMinTemp")
    @Mapping(target = "localtime", source = "weatherClientResponse.location.tz_id", qualifiedByName = "calculateCurrentLocalTime")
    @Mapping(target = "timeZone", source = "weatherClientResponse.location.tz_id")
    @Mapping(target = "forecast", source = "weatherClientResponse.forecast")
    @Mapping(target = "city", source = "weatherClientResponse.location.name")
    @Mapping(target = "country", source = "weatherClientResponse.location.country")
    ForecastData mapToForecast(WeatherClientResponse weatherClientResponse);

    @Named("extractMaxTemp")
    default float extractMaxTemp(WeatherClientResponse.Forecast forecast) {
        return (forecast != null && !forecast.getForecastday().isEmpty()) ? forecast.getForecastday().get(0).getDay().getMaxtemp_f(): (float) 0.0;
    }

    @Named("extractMinTemp")
    default float extractMinTemp(WeatherClientResponse.Forecast forecast) {
        return (forecast != null && !forecast.getForecastday().isEmpty()) ? forecast.getForecastday().get(0).getDay().getMintemp_f(): (float) 0.0;
    }

    @Named("calculateCurrentLocalTime")
    default String calculateLocalTime(String timeZone) {
        try {
            ZoneId zoneId = ZoneId.of(timeZone);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), zoneId);
            return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception e) {
            return "Invalid Timezone";
        }
    }
}
