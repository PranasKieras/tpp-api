package com.dao.impl;

import com.setup.MockDataSourceProvider;
import com.dao.entity.PSUser;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.provider.DataSourceProvider;
import com.service.entities.CreateUserTO;
import com.service.entities.FetchUserTO;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class UserDAOImplTest {

    private Injector injector;

    private UserDAOImpl userDAO;

    @Before
    public void setUp() {
        createTestInjections();
        userDAO = injector.getInstance(UserDAOImpl.class);
    }

    @Test
    public void fetchOne_ReturnsUser_WhenPresent() {
        FetchUserTO fetchUserTO = new FetchUserTO("1111", "2222", "3333");

        Optional<PSUser> user = userDAO.fetchUser(fetchUserTO);

        assertTrue(user.isPresent());
        assertUserEquals(user.get(), "1111", "2222", "3333", "token1");
    }

    @Test
    public void fetchOne_ReturnsDifferentUser_WhenPresent() {
        FetchUserTO fetchUserTO = new FetchUserTO("2222", "3333", "4444");

        Optional<PSUser> user = userDAO.fetchUser(fetchUserTO);

        assertTrue(user.isPresent());
        assertUserEquals(user.get(), "2222", "3333", "4444", "token2");
    }


    @Test
    public void fetchOne_ReturnsEmptyUserContainer_WhenNotPresent() {
        FetchUserTO fetchUserTO = new FetchUserTO("2222", "2222", "3333");

        Optional<PSUser> user = userDAO.fetchUser(fetchUserTO);

        assertTrue(user.isEmpty());
    }

    @Test
    public void create_CreatesUser_OnValidInput() {
        CreateUserTO createUserTO = new CreateUserTO("5555", "6666", "7777", "token8");
        FetchUserTO fetchUserTO = new FetchUserTO("5555", "6666", "7777");

        //user not present before insert
        Optional<PSUser> user = userDAO.fetchUser(fetchUserTO);
        assertTrue(user.isEmpty());

        //successful insert
        PSUser createUser = userDAO.createUser(createUserTO);
        assertUserEquals(createUser, "5555", "6666", "7777", "token8");

        //user present after insert
        user = userDAO.fetchUser(fetchUserTO);
        assertTrue(user.isPresent());
    }

    private void assertUserEquals(PSUser psUser, String personalId, String bankLoginId, String phoneNumber, String token) {
        assertEquals(personalId, psUser.getPersonalId());
        assertEquals(bankLoginId, psUser.getBankLoginId());
        assertEquals(phoneNumber, psUser.getPhoneNumber());
        assertEquals(token, psUser.getLoginToken());
    }


    private void createTestInjections() {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(DataSourceProvider.class).to(MockDataSourceProvider.class);
            }
        });
    }
}