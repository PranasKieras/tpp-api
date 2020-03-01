package com.service;

import com.request.LoginRequest;
import com.service.entities.LoginData;

public interface TppLoginService {

    LoginData login(LoginRequest loginRequest);
}
