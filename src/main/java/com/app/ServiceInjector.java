package com.app;

import com.datasource.DataSourceProvider;
import com.datasource.impl.H2DataSourceProvider;
import com.service.RemoteLoginService;
import com.dao.UserDAO;
import com.service.impl.GatewayLoginService;
import com.dao.impl.UserDAOImpl;
import com.google.inject.AbstractModule;
import com.service.PSULoginService;
import com.service.impl.PSULoginServiceImpl;

class ServiceInjector extends AbstractModule {

    /**
     * sets up injections for main application
     */
    @Override
    public void configure() {
        bind(PSULoginService.class).to(PSULoginServiceImpl.class);
        bind(UserDAO.class).to(UserDAOImpl.class);
        bind(RemoteLoginService.class).to(GatewayLoginService.class);
        bind(DataSourceProvider.class).to(H2DataSourceProvider.class);
    }
}
