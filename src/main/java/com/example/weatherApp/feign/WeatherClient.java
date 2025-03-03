package com.example.weatherApp.feign;

import com.example.weatherApp.config.FeignClientConfig;
import com.example.weatherApp.dto.WeatherClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherClient", url = "${feign.client.config.weatherClient.targetUrl}", configuration = FeignClientConfig.class)
public interface WeatherClient {

    @GetMapping("/forecast.json")
    WeatherClientResponse getWeather(@RequestParam("key") String apiKey,
                                     @RequestParam("q") String location,
                                     @RequestParam("days") int days);
}
