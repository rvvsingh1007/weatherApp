package com.example.weatherApp.feign.error;

import com.example.weatherApp.exception.InvalidInputException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("Feign error in method {}: Status {}, Reason {}", methodKey, response.status(), response.reason());
        if (response.status() == HttpStatus.BAD_REQUEST.value()) {
            return new InvalidInputException("Invalid request: Check city or zipCode", HttpStatus.BAD_REQUEST);
        }
        return new Default().decode(methodKey, response);
    }
}
