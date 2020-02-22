package com.mapper;

import com.exception.BadRequestDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.request.LoginRequest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class RequestDeserializerTest {

    RequestDeserializer mapper = new RequestDeserializer();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        
    }

    @Test
    public void mapSucceedsWithValidInput() throws BadRequestDataException {
        LoginRequest data = mapper.map("{\n" +
                "  \"id\": \"1\"\n" +
                "}");
        assertEquals("1", data.getId());
    }

    @Test
    public void map_ThrowsBadRequestDataException_OnInvalidInput() throws BadRequestDataException {
        exception.expect(BadRequestDataException.class);
        exception.expectMessage("Unable to parse request body, invalid JSON");

        LoginRequest data = mapper.map("invalid body");
    }
}
