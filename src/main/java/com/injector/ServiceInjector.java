package com.injector;

import com.service.LoginService;
import com.dao.UserDAO;
import com.service.impl.LoginServiceImpl;
import com.dao.impl.UserDAOImpl;
import com.google.inject.AbstractModule;
import com.service.TppLoginService;
import com.service.impl.TppLoginServiceImpl;

public class ServiceInjector extends AbstractModule {

    /**
     * sets up injections for main application
     */
    @Override
    public void configure() {
        bind(TppLoginService.class).to(TppLoginServiceImpl.class);
        bind(UserDAO.class).to(UserDAOImpl.class);
        bind(LoginService.class).to(LoginServiceImpl.class);
    }
}
