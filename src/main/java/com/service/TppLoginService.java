package com.service;

import com.request.LoginRequest;
import com.servicedata.LoginData;

public interface TppLoginService {

    LoginData login(LoginRequest loginRequest);
}
