package com.controller;

import com.exception.BadRequestDataException;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mapper.RequestDeserializer;
import com.request.LoginRequest;
import com.service.TppLoginService;
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

    @Mock
    private Request request;
    @Mock
    private Response response;
    @Mock
    private TppLoginService tppLoginService;
    @Mock
    private RequestDeserializer requestDeserializer;

    private LoginRequest loginRequest;

    private TppController loginController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        createTestInjections();
        loginController = injector.getInstance(TppController.class);

    }

    @Test
    public void handleLogin_ReturnsLoginData_OnCorrectInput() throws BadRequestDataException {
        String data = loginController.handleLogin(request, response);
        assertEquals(data, "{\"id\": \"" + "id" + "\"}");
        verify(response, times(1)).status(200);
        verify(response, times(1)).type("application/json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void handleLogin_ThrowsNullPointerException_OnNullInput() throws BadRequestDataException {
        loginController.handleLogin(null, null);
    }

    @Test
    public void handleLogin_CallsTppLoginService_OnValidInput() throws BadRequestDataException {
        String requestBody = "{\"id\": \"" + "id" + "\"}";
        loginRequest = new LoginRequest("", "", "");
        when(request.body()).thenReturn(requestBody);
        when(requestDeserializer.map(requestBody)).thenReturn(loginRequest);

        loginController.handleLogin(request, response);

        verify(tppLoginService, times(1)).login(loginRequest);
    }

    @Test
    public void handleLogin_RethrowsBadRequestDataException_OnBadSerializationValue() throws BadRequestDataException {
        expect.expect(BadRequestDataException.class);

        when(requestDeserializer.map(anyString())).thenThrow(new BadRequestDataException("Bad request data"));
        when(request.body()).thenReturn("{\"id\": \"" + "id" + "\"}");

        loginController.handleLogin(request, response);
    }

    @Test
    public void handleLogin_CallsRequestDeserializer_OnValidInput() throws BadRequestDataException {
        String requestBody = "{\"id\": \"" + "id" + "\"}";
        when(request.body()).thenReturn(requestBody);
        when(requestDeserializer.map(requestBody)).thenReturn(loginRequest);

        loginController.handleLogin(request, response);

        verify(requestDeserializer, times(1)).map(requestBody);
    }


    private void createTestInjections(){
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(TppLoginService.class).toInstance(tppLoginService);
                bind(RequestDeserializer.class).toInstance(requestDeserializer);
            }
        });
    }
}