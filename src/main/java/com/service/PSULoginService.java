package com.service;

import com.rest.request.LoginRequest;
import com.service.entities.LoginData;

public interface PSULoginService {

    LoginData login(LoginRequest loginRequest);
}
