package com.service.impl;

import com.service.LoginService;
import com.dao.UserDAO;
import com.dao.entity.PSUser;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.request.LoginRequest;
import com.service.entities.CreateUserTO;
import com.service.entities.FetchUserTO;
import com.service.entities.PSULoginTO;
import com.service.entities.ValidateTokenTO;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentCaptor.*;

public class TppLoginServiceImplTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();
    @Mock
    private LoginService loginService;
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
        ArgumentCaptor<FetchUserTO> captor = forClass(FetchUserTO.class);
        when(loginService.login(any(PSULoginTO.class))).thenReturn("token1");

        tppLoginServiceImpl.login(loginRequest);

        verify(userDAO, times(1)).fetchUser(captor.capture());
        assertFetchUserEquals(captor);
    }


    @Test
    public void login_DoesNotCallCreateUserAndLogin_OnUserPresent() {
        when(userDAO.fetchUser(any(FetchUserTO.class))).thenReturn(Optional.of(new PSUser("1", "123", "1234", "token")));
        ArgumentCaptor<ValidateTokenTO> captor = forClass(ValidateTokenTO.class);

        tppLoginServiceImpl.login(loginRequest);

        verify(loginService, times(1)).validateToken(captor.capture());
        verify(userDAO, times(0)).createUser(any(CreateUserTO.class));
        verify(loginService, times(0)).login(any(PSULoginTO.class));
        assertValidateToEquals(captor);
    }



    @Test
    public void login_CallsLoginAndCreateUser_OnUserNotPresent() {
        when(userDAO.fetchUser(any(FetchUserTO.class))).thenReturn(Optional.empty());
        when(loginService.login(any(PSULoginTO.class))).thenReturn("token");
        ArgumentCaptor<PSULoginTO> loginCaptor = forClass(PSULoginTO.class);
        ArgumentCaptor<CreateUserTO> createUserCaptor = forClass(CreateUserTO.class);

        tppLoginServiceImpl.login(loginRequest);

        verify(loginService, times(1)).login(loginCaptor.capture());
        assertLoginToEquals(loginCaptor);
        verify(userDAO, times(1)).createUser(createUserCaptor.capture());
        assertCreateUserEquals(createUserCaptor);
    }

    private void assertLoginToEquals(ArgumentCaptor<PSULoginTO> loginCaptor) {
        assertEquals("1", loginCaptor.getValue().getPersonalId());
        assertEquals("1234", loginCaptor.getValue().getBankLoginId());
        assertEquals("123", loginCaptor.getValue().getPhoneNumber());
    }

    private void assertCreateUserEquals(ArgumentCaptor<CreateUserTO> createUserCaptor) {
        assertEquals("1", createUserCaptor.getValue().getPersonalId());
        assertEquals("1234", createUserCaptor.getValue().getBankLoginId());
        assertEquals("123", createUserCaptor.getValue().getPhoneNumber());
        assertEquals("token", createUserCaptor.getValue().getLoginToken());
    }

    private void assertValidateToEquals(ArgumentCaptor<ValidateTokenTO> captor) {
        assertEquals("1", captor.getValue().getPersonalId());
        assertEquals("123", captor.getValue().getBankLoginId());
        assertEquals("1234", captor.getValue().getPhoneNumber());
        assertEquals("token", captor.getValue().getLoginToken());
    }

    private void assertFetchUserEquals(ArgumentCaptor<FetchUserTO> captor) {
        assertEquals("1", captor.getValue().getPersonalId());
        assertEquals("1234", captor.getValue().getBankLoginId());
        assertEquals("123", captor.getValue().getPhoneNumber());
    }


    private void createTestInjections() {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(UserDAO.class).toInstance(userDAO);
                bind(LoginService.class).toInstance(loginService);
            }
        });
    }
}
