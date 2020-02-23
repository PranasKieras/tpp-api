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
    public void mapSucceedsWithValidInput() throws BadRequestDataException, JsonProcessingException {
        LoginRequest data = mapper.map("{\n" +
                "  \"id\": \"1\",\n" +
                "  \"phoneNo\": \"1234\",\n" +
                "  \"customerNo\": \"1234\"\n" +
                "}");
        assertEquals("1", data.getId());
    }

    @Test
    public void map_ThrowsBadRequestDataException_OnInvalidJson() throws BadRequestDataException, JsonProcessingException {
        exception.expect(BadRequestDataException.class);
        exception.expectMessage("Unable to parse request body, invalid JSON");

        LoginRequest data = mapper.map("invalid body");
    }

    @Test
    public void map_ThrowsBadRequestDataException_OnMissingId() throws BadRequestDataException, JsonProcessingException {
        exception.expect(BadRequestDataException.class);
        exception.expectMessage("The fields [id] are mandatory");
        LoginRequest data = mapper.map("{\n" +
                "  \"phoneNo\": \"1234\",\n" +
                "  \"customerNo\": \"1234\"\n" +
                "}");
    }

    @Test
    public void map_ThrowsBadRequestDataException_OnMissingPhoneNumber() throws BadRequestDataException, JsonProcessingException {
        exception.expect(BadRequestDataException.class);
        exception.expectMessage("The fields [phoneNo] are mandatory");
        LoginRequest data = mapper.map("{\n" +
                "  \"id\": \"1\",\n" +
                "  \"customerNo\": \"1234\"\n" +
                "}");
    }

    @Test
    public void map_ThrowsBadRequestDataException_OnMissingCustomerNo() throws BadRequestDataException, JsonProcessingException {
        exception.expect(BadRequestDataException.class);
        exception.expectMessage("The fields [customerNo] are mandatory");
        LoginRequest data = mapper.map("{\n" +
                "  \"id\": \"1\",\n" +
                "  \"phoneNo\": \"1234\"\n" +
                "}");
    }
}
