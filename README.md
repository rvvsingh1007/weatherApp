# weatherApp
This Application can be used to get Weather info for a given Address ( city or zip code)
Prerequisite: Java 17 and maven 3.5 or later
To compile run> mvn clean compile
To run> mvn spring-boot:run

The app should be up and running port 8080
To Verify curl --location 'http://localhost:8080/weatherApp/actuator/health'

To Look at API docs Type the url in browser: http://localhost:8080/weatherApp/swagger-ui/index.html

Sample Call to get get wether info:  curl --location 'http://localhost:8080/weatherApp/api/weather/forecast/address' \
--header 'Content-Type: application/json' \
--data '{
"street": "6229 Gunter Way",
"city": "San Jose",
"state": "CA",
"zipCode": 95123,
"country": "USA"
}'

Response:
{
"city": "San Jose",
"country": "USA",
"currentTemp": 50.0,
"maxTemp": 53.8,
"minTemp": 37.9,
"unit": "Fahrenheit",
"localtime": "2025-03-03 09:18",
"timeZone": "America/Los_Angeles",
"cacheHit": false,
"forecast": {
"forecastday": [
{
"date": "2025-03-03",
"day": {
"maxtemp_f": 53.8,
"mintemp_f": 37.9,
"maxwind_mph": 10.5,
"totalprecip_mm": 0.0,
"condition": {
"text": "Overcast ",
"icon": "//cdn.weatherapi.com/weather/64x64/day/122.png",
"code": 1009
}
},
"astro": {
"sunrise": "06:35 AM",
"sunset": "06:04 PM",
"moonrise": "08:33 AM",
"moonset": "10:53 PM"
}
},
{
"date": "2025-03-04",
"day": {
"maxtemp_f": 58.1,
"mintemp_f": 38.8,
"maxwind_mph": 6.5,
"totalprecip_mm": 0.0,
"condition": {
"text": "Cloudy ",
"icon": "//cdn.weatherapi.com/weather/64x64/day/119.png",
"code": 1006
}
},
"astro": {
"sunrise": "06:33 AM",
"sunset": "06:05 PM",
"moonrise": "09:07 AM",
"moonset": "No moonset"
}
},
{
"date": "2025-03-05",
"day": {
"maxtemp_f": 49.3,
"mintemp_f": 39.9,
"maxwind_mph": 5.6,
"totalprecip_mm": 28.43,
"condition": {
"text": "Heavy rain",
"icon": "//cdn.weatherapi.com/weather/64x64/day/308.png",
"code": 1195
}
},
"astro": {
"sunrise": "06:32 AM",
"sunset": "06:06 PM",
"moonrise": "09:48 AM",
"moonset": "12:09 AM"
}
}
]
}
}



