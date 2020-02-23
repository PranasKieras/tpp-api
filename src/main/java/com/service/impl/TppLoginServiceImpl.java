package com.service.impl;

import com.dao.LoginDAO;
import com.dao.UserDAO;
import com.google.inject.Inject;
import com.request.LoginRequest;
import com.service.TppLoginService;
import com.servicedata.LoginData;
import lombok.NonNull;

public class TppLoginServiceImpl implements TppLoginService {

    @Inject
    LoginDAO loginDao;
    @Inject
    UserDAO userDAO;

    @Override
    public LoginData login(@NonNull LoginRequest loginRequest) {
        userDAO.fetchOne()
                .orElseGet(() -> userDAO.createUser());

        return loginDao.login();
    }
}
