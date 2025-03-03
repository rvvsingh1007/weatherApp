package com.example.weatherApp;

import com.example.weatherApp.dto.WeatherClientResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseTest {

    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected WeatherClientResponse generateWeatherClientResponse() throws JsonProcessingException {
        String json = "{\n" +
                "    \"location\": {\n" +
                "        \"name\": \"San Jose\",\n" +
                "        \"country\": \"USA\",\n" +
                "        \"tz_id\": \"America/Los_Angeles\",\n" +
                "        \"localtime\": \"2025-03-02 11:12\"\n" +
                "    },\n" +
                "    \"current\": {\n" +
                "        \"temp_f\": 52.3,\n" +
                "        \"wind_mph\": 6.5,\n" +
                "        \"humidity\": 82,\n" +
                "        \"last_updated\": \"2025-03-02 11:00\",\n" +
                "        \"condition\": {\n" +
                "            \"text\": \"Partly cloudy\",\n" +
                "            \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/116.png\",\n" +
                "            \"code\": 1003\n" +
                "        }\n" +
                "    },\n" +
                "    \"forecast\": {\n" +
                "        \"forecastday\": [\n" +
                "            {\n" +
                "                \"date\": \"2025-03-02\",\n" +
                "                \"day\": {\n" +
                "                    \"maxtemp_f\": 48.0,\n" +
                "                    \"mintemp_f\": 41.4,\n" +
                "                    \"maxwind_mph\": 16.1,\n" +
                "                    \"totalprecip_mm\": 1.11,\n" +
                "                    \"condition\": {\n" +
                "                        \"text\": \"Patchy rain nearby\",\n" +
                "                        \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/176.png\",\n" +
                "                        \"code\": 1063\n" +
                "                    }\n" +
                "                },\n" +
                "                \"astro\": {\n" +
                "                    \"sunrise\": \"06:36 AM\",\n" +
                "                    \"sunset\": \"06:03 PM\",\n" +
                "                    \"moonrise\": \"08:03 AM\",\n" +
                "                    \"moonset\": \"09:38 PM\"\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"date\": \"2025-03-03\",\n" +
                "                \"day\": {\n" +
                "                    \"maxtemp_f\": 49.5,\n" +
                "                    \"mintemp_f\": 39.4,\n" +
                "                    \"maxwind_mph\": 8.5,\n" +
                "                    \"totalprecip_mm\": 0.03,\n" +
                "                    \"condition\": {\n" +
                "                        \"text\": \"Overcast \",\n" +
                "                        \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/122.png\",\n" +
                "                        \"code\": 1009\n" +
                "                    }\n" +
                "                },\n" +
                "                \"astro\": {\n" +
                "                    \"sunrise\": \"06:35 AM\",\n" +
                "                    \"sunset\": \"06:04 PM\",\n" +
                "                    \"moonrise\": \"08:33 AM\",\n" +
                "                    \"moonset\": \"10:53 PM\"\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"date\": \"2025-03-04\",\n" +
                "                \"day\": {\n" +
                "                    \"maxtemp_f\": 51.3,\n" +
                "                    \"mintemp_f\": 38.5,\n" +
                "                    \"maxwind_mph\": 6.5,\n" +
                "                    \"totalprecip_mm\": 0.0,\n" +
                "                    \"condition\": {\n" +
                "                        \"text\": \"Overcast \",\n" +
                "                        \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/122.png\",\n" +
                "                        \"code\": 1009\n" +
                "                    }\n" +
                "                },\n" +
                "                \"astro\": {\n" +
                "                    \"sunrise\": \"06:33 AM\",\n" +
                "                    \"sunset\": \"06:05 PM\",\n" +
                "                    \"moonrise\": \"09:07 AM\",\n" +
                "                    \"moonset\": \"No moonset\"\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        return objectMapper.readValue(json, WeatherClientResponse.class);
    }
}
