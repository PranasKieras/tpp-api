package com.rest;

import com.exception.BadRequestDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.rest.request.LoginRequest;
import com.google.inject.Singleton;
import lombok.NonNull;

import static java.util.stream.Collectors.toList;

@Singleton
class RequestDeserializer {

    ObjectMapper mapper = new ObjectMapper();

    public LoginRequest map(@NonNull String body) throws BadRequestDataException {
        try {
            return mapper.readValue(body, LoginRequest.class);
        } catch (MismatchedInputException e){
            throw new BadRequestDataException(String.format("The field %s are mandatory",
                    e.getPath()
                            .stream()
                            .map(JsonMappingException.Reference::getFieldName)
                            .collect(toList())));
        } catch (JsonProcessingException e) {
            throw new BadRequestDataException("Unable to parse request body, invalid JSON");
        }
    }

}
