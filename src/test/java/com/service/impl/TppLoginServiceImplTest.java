package com.service.impl;

import com.controller.TppController;
import com.dao.LoginDAO;
import com.dao.UserDAO;
import com.dao.entity.PSUser;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.request.LoginRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class TppLoginServiceImplTest {

    @Mock
    private LoginDAO loginDao;
    @Mock
    private UserDAO userDAO;

    private LoginRequest loginRequest;

    private TppLoginServiceImpl tppLoginServiceImpl;

    private Injector injector;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        createTestInjections();
        tppLoginServiceImpl = injector.getInstance(TppLoginServiceImpl.class);
        loginRequest = new LoginRequest("1","123", "1234");

    }

    @Test
    public void login_CallsUserDaoFetchOne_OnValidInput() {
        tppLoginServiceImpl.login(loginRequest);

        verify(userDAO, times(1)).fetchOne();
    }

    @Test
    public void login_CallsUserDaoCreateUser_OnUserNotPresent() {
        when(userDAO.fetchOne()).thenReturn(Optional.empty());

        tppLoginServiceImpl.login(loginRequest);

        verify(userDAO, times(1)).createUser();
    }

    @Test
    public void login_DoesNotCallUserDaoCreateUser_OnUserPresent() {
        when(userDAO.fetchOne()).thenReturn(Optional.of(new PSUser()));

        tppLoginServiceImpl.login(loginRequest);

        verify(userDAO, times(0)).createUser();
    }

    @Test
    public void login_CallsLoginDao_OnValidInput() {
        tppLoginServiceImpl.login(loginRequest);

        verify(loginDao, times(1)).login();
    }

    private void createTestInjections() {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(UserDAO.class).toInstance(userDAO);
                bind(LoginDAO.class).toInstance(loginDao);
            }
        });
    }

}
