package com.example.weatherApp.controller;

import com.example.weatherApp.dto.Address;
import com.example.weatherApp.dto.ForecastData;
import com.example.weatherApp.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @Operation(summary = "Get weather forecast", description = "Retrieves weather forecast for a given city or zipCode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved forecast"),
            @ApiResponse(responseCode = "400", description = "Invalid city name or zipCode"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/forecast/{cityOrZip}")
    public ResponseEntity<ForecastData> getWeatherByCity(@PathVariable String cityOrZip, @RequestParam(defaultValue = "3") int days) {
        ForecastData forecastData = weatherService.getWeatherForecast(cityOrZip, days);
        return ResponseEntity.ok(forecastData);
    }


    @Operation(summary = "Get weather forecast by address", description = "Retrieves weather forecast for a given address object.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved forecast"),
            @ApiResponse(responseCode = "400", description = "Invalid address"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/forecast/address")
    public ResponseEntity<ForecastData> getWeatherByAddress(@RequestBody Address address, @RequestParam(defaultValue = "3") int days) {
        return ResponseEntity.ok(weatherService.getWeatherForecast(address, days));
    }

}
