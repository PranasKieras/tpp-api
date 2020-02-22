package com.mapper;

import com.exception.BadRequestDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.request.LoginRequest;

public class RequestDeserializer {

    ObjectMapper mapper = new ObjectMapper();

    public LoginRequest map(String body) throws BadRequestDataException {
        try {
            return mapper.readValue(body, LoginRequest.class);
        } catch (JsonProcessingException e) {
            throw new BadRequestDataException("Unable to parse request body, invalid JSON");
        }
    }

}
