package com.example.weatherApp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Address {
    private String street;
    private String city;
    private String state;
    private Integer zipCode;
    private String country;
}
