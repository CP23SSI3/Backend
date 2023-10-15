package com.example.internhub.dtos;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserInputDTOConverter implements Converter<String, UserInputDTO> {

    private final ObjectMapper objectMapper;

    public UserInputDTOConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public UserInputDTO convert(String source) {
        try {
            return objectMapper.readValue(source, UserInputDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
