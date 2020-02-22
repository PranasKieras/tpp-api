package com.controller;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.service.TppLoginService;
import com.servicedata.LoginData;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spark.Request;
import spark.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TppControllerTest {

    private Injector injector;

    @Rule
    public ExpectedException expect = ExpectedException.none();

    private TppController loginController;
    @Mock
    private Request request;
    @Mock
    private Response response;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(TppLoginService.class).toInstance(new TppLoginService() {
                    @Override
                    public LoginData login() {
                        return null;
                    }
                });
            }
        });
        MockitoAnnotations.initMocks(this);
        loginController = injector.getInstance(TppController.class);

    }

    @Test
    public void handleLogin_ReturnsLoginData_OnCorrectInput() {
        String data = loginController.handleLogin(request, response);
        assertEquals(data, "{\"id\": \"" + "id" + "\"}");
        verify(response, times(1)).status(200);
        verify(response, times(1)).type("application/json");
    }

    @Test(expected = NullPointerException.class)
    public void handleLogin_ThrowsNullPointerException_OnNullInput() {
        loginController.handleLogin(null, null);
    }
}