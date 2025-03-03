package com.example.weatherApp;

import com.example.weatherApp.controller.WeatherController;
import com.example.weatherApp.dto.Address;
import com.example.weatherApp.dto.ForecastData;
import com.example.weatherApp.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
@AutoConfigureMockMvc
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherService weatherService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetWeatherForZip() throws Exception {
        ForecastData mockResponse = generateResponseData();
        when(weatherService.getWeatherForecast("95123", 3)).thenReturn(mockResponse);

        MvcResult result = mockMvc.perform(get("/api/weather/forecast/95123?days=3").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        ForecastData response = objectMapper.readValue(
                result.getResponse().getContentAsString(), ForecastData.class);
        assertEquals("San Jose", response.getCity());
    }

    @Test
    public void testGetWeatherForAddress() throws Exception {
        Address address = new Address();
        address.setStreet("6229 Gunter Way");
        address.setCity("San Jose");
        address.setZipCode(95123);
        ForecastData mockResponse = generateResponseData();
        when(weatherService.getWeatherForecast("95123", 3)).thenReturn(mockResponse);
        when(weatherService.getWeatherForecast(address, 3)).thenCallRealMethod();

        MvcResult result = mockMvc.perform(post("/api/weather/forecast/address?days=3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(address)) // Convert Address to JSON
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        ForecastData response = objectMapper.readValue(
                result.getResponse().getContentAsString(), ForecastData.class);
        assertEquals("San Jose", response.getCity());
    }

    @Test
    public void testGetWeatherForAddressWithCityOnly() throws Exception {
        Address address = new Address();
        address.setStreet("6229 Gunter Way");
        address.setCity("San Jose");
        address.setCountry("USA");
        ForecastData mockResponse = generateResponseData();
        when(weatherService.getWeatherForecast("San Jose", 3)).thenReturn(mockResponse);
        when(weatherService.getWeatherForecast(address, 3)).thenCallRealMethod();

        MvcResult result = mockMvc.perform(post("/api/weather/forecast/address?days=3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(address)) // Convert Address to JSON
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        ForecastData response = objectMapper.readValue(
                result.getResponse().getContentAsString(), ForecastData.class);
        assertEquals("San Jose", response.getCity());
    }

    @Test
    public void testGetWeatherForAddressNegative() throws Exception {
        Address address = new Address();
        address.setStreet("6229 Gunter Way");
        address.setCountry("USA");//both city and zipcode are missing

        ForecastData mockResponse = generateResponseData();
        when(weatherService.getWeatherForecast("95123", 3)).thenReturn(mockResponse);
        when(weatherService.getWeatherForecast(address, 3)).thenCallRealMethod();

        MvcResult result = mockMvc.perform(post("/api/weather/forecast/address?days=3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(address)) // Convert Address to JSON
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("City name and zipCode both cannot be empty"));
    }

    private ForecastData generateResponseData() throws JsonProcessingException {
        String jsonStr = "{\n" +
                "    \"city\": \"San Jose\",\n" +
                "    \"country\": \"USA\",\n" +
                "    \"currentTemp\": 52.2,\n" +
                "    \"maxTemp\": 58.5,\n" +
                "    \"minTemp\": 39.9,\n" +
                "    \"unit\": \"Fahrenheit\",\n" +
                "    \"localtime\": \"2025-03-01 19:49\",\n" +
                "    \"timeZone\": \"America/Los_Angeles\",\n" +
                "    \"cacheHit\": false,\n" +
                "    \"forecast\": {\n" +
                "        \"forecastday\": [\n" +
                "            {\n" +
                "                \"date\": \"2025-03-01\",\n" +
                "                \"day\": {\n" +
                "                    \"maxtemp_f\": 58.5,\n" +
                "                    \"mintemp_f\": 39.9,\n" +
                "                    \"maxwind_mph\": 8.9,\n" +
                "                    \"totalprecip_mm\": 0.0,\n" +
                "                    \"condition\": {\n" +
                "                        \"text\": \"Partly Cloudy \",\n" +
                "                        \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/116.png\",\n" +
                "                        \"code\": 1003\n" +
                "                    }\n" +
                "                },\n" +
                "                \"astro\": {\n" +
                "                    \"sunrise\": \"06:38 AM\",\n" +
                "                    \"sunset\": \"06:02 PM\",\n" +
                "                    \"moonrise\": \"07:36 AM\",\n" +
                "                    \"moonset\": \"08:23 PM\"\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"date\": \"2025-03-02\",\n" +
                "                \"day\": {\n" +
                "                    \"maxtemp_f\": 50.0,\n" +
                "                    \"mintemp_f\": 38.6,\n" +
                "                    \"maxwind_mph\": 15.2,\n" +
                "                    \"totalprecip_mm\": 0.03,\n" +
                "                    \"condition\": {\n" +
                "                        \"text\": \"Overcast \",\n" +
                "                        \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/122.png\",\n" +
                "                        \"code\": 1009\n" +
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
                "                    \"maxtemp_f\": 51.1,\n" +
                "                    \"mintemp_f\": 42.6,\n" +
                "                    \"maxwind_mph\": 9.4,\n" +
                "                    \"totalprecip_mm\": 0.0,\n" +
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
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        return objectMapper.readValue(jsonStr, ForecastData.class);
    }
}
