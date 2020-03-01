package com.rest;

import com.exception.BadRequestDataException;
import com.google.inject.Inject;
import com.rest.request.LoginRequest;
import com.service.PSULoginService;
import lombok.NonNull;
import spark.Request;
import spark.Response;

public class TppController {

    @Inject
    PSULoginService tppLoginService;
    @Inject
    RequestDeserializer requestDeserializer;

    public String handleLogin(@NonNull Request request, @NonNull Response response) throws BadRequestDataException {
        LoginRequest loginRequest = requestDeserializer.map(request.body());
        tppLoginService.login(loginRequest);
        response.status(200);
        response.type("application/json");
        return "{\"id\": \"" + "id" + "\"}";
    }
}
