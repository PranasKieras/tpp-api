package com.injector;

import com.google.inject.AbstractModule;

public class ServiceInjector extends AbstractModule {

    /**
     * sets up injections for main application
     */
    @Override
    protected void configure() {
//        bind(MoneyTransferService.class).to(SimpleMoneyTransferService.class);
//        bind(DataSourceProvider.class).to(MockDataSourceProvider.class);
    }
}
