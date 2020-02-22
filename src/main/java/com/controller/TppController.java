package com.controller;

import com.google.inject.Inject;
import org.jooq.JSONFormat;
import org.springframework.lang.NonNull;
import spark.Request;
import spark.Response;

public class TppController {

//    @Inject
//    @Inject
//    LoginService loginService;

    private static final JSONFormat format = new JSONFormat().format(true).header(false).recordFormat(JSONFormat.RecordFormat.OBJECT);

    public String handleLogin(@NonNull Request request, @NonNull Response response){
        response.status(200);
        response.type("application/json");
        return "{\"id\": \"" + "id" + "\"}";
    }
}
