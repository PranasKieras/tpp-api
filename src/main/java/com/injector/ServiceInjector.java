package com.injector;

import com.dao.LoginDAO;
import com.dao.UserDAO;
import com.dao.impl.LoginDAOImpl;
import com.dao.impl.UserDAOImpl;
import com.google.inject.AbstractModule;
import com.mapper.RequestDeserializer;
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
        bind(LoginDAO.class).to(LoginDAOImpl.class);
    }
}
