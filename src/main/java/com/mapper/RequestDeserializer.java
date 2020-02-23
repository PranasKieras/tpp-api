package com.mapper;

import com.exception.BadRequestDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.request.LoginRequest;
import lombok.NonNull;

import java.util.stream.Collectors;

public class RequestDeserializer {

    ObjectMapper mapper = new ObjectMapper();

    public LoginRequest map(@NonNull String body) throws BadRequestDataException {
        try {
            return mapper.readValue(body, LoginRequest.class);
        } catch (MismatchedInputException e){
            throw new BadRequestDataException(String.format("The fields %s are mandatory",
                    e.getPath()
                            .stream()
                            .map(JsonMappingException.Reference::getFieldName)
                            .collect(Collectors.toList())));
        } catch (JsonProcessingException e) {
            throw new BadRequestDataException("Unable to parse request body, invalid JSON");
        }
    }

}
