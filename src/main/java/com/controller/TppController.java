package com.controller;

import com.exception.BadRequestDataException;
import com.google.inject.Inject;
import com.mapper.RequestDeserializer;
import com.request.LoginRequest;
import com.service.TppLoginService;
import com.servicedata.LoginData;
import lombok.NonNull;
import spark.Request;
import spark.Response;

public class TppController {

    @Inject
    TppLoginService tppLoginService;
    @Inject
    RequestDeserializer requestDeserializer;

    public String handleLogin(@NonNull Request request, @NonNull Response response) throws BadRequestDataException {
        LoginRequest loginRequest = requestDeserializer.map(request.body());
        LoginData data = tppLoginService.login(loginRequest);
        response.status(200);
        response.type("application/json");
        return "{\"id\": \"" + "id" + "\"}";
    }
}
