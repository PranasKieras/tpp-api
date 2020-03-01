package com.rest;

import com.exception.BadRequestDataException;
import com.rest.request.LoginRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class RequestDeserializerTest {

    RequestDeserializer mapper = new RequestDeserializer();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void mapSucceedsWithValidInput() throws BadRequestDataException {
        LoginRequest data = mapper.map("{\n" +
                "  \"personalId\": \"1\",\n" +
                "  \"bankLoginId\": \"1234\",\n" +
                "  \"phoneNumber\": \"1234\"\n" +
                "}");
        assertEquals("1", data.getPersonalId());
    }

    @Test
    public void map_ThrowsBadRequestDataException_OnInvalidJson() throws BadRequestDataException {
        exception.expect(BadRequestDataException.class);
        exception.expectMessage("Unable to parse request body, invalid JSON");

        mapper.map("invalid body");
    }

    @Test
    public void map_ThrowsBadRequestDataException_OnMissingId() throws BadRequestDataException {
        exception.expect(BadRequestDataException.class);
        exception.expectMessage("The field [personalId] are mandatory");

        mapper.map("{\n" +
                "  \"bankLoginId\": \"1234\",\n" +
                "  \"phoneNumber\": \"1234\"\n" +
                "}");
    }

    @Test
    public void map_ThrowsBadRequestDataException_OnMissingPhoneNumber() throws BadRequestDataException {
        exception.expect(BadRequestDataException.class);
        exception.expectMessage("The field [phoneNumber] are mandatory");

        mapper.map("{\n" +
                "  \"personalId\": \"1\",\n" +
                "  \"bankLoginId\": \"1234\"\n" +
                "}");
    }

    @Test
    public void map_ThrowsBadRequestDataException_OnMissingCustomerNo() throws BadRequestDataException {
        exception.expect(BadRequestDataException.class);
        exception.expectMessage("The field [bankLoginId] are mandatory");

        mapper.map("{\n" +
                "  \"personalId\": \"1\",\n" +
                "  \"phoneNumber\": \"1234\"\n" +
                "}");
    }
}
