package com.service.impl;

import com.dao.UserDAO;
import com.dao.entity.PSUser;
import com.google.inject.Inject;
import com.request.LoginRequest;
import com.service.LoginService;
import com.service.TppLoginService;
import com.service.entities.CreateUserTO;
import com.service.entities.FetchUserTO;
import com.service.entities.PSULoginTO;
import com.service.entities.ValidateTokenTO;
import com.service.entities.LoginData;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public class TppLoginServiceImpl implements TppLoginService {

    @Inject
    LoginService loginService;
    @Inject
    UserDAO userDAO;

    @Override
    public LoginData login(@NonNull LoginRequest loginRequest) {
        final FetchUserTO fetchUserTO = new FetchUserTO(
                loginRequest.getId(),
                loginRequest.getCustomerNo(),
                loginRequest.getPhoneNo());
        return userDAO.fetchUser(fetchUserTO)
                .map(this::validateToken)
                .orElseGet(() -> loginAndCreateUser(loginRequest));

    }

    private LoginData validateToken(@NonNull PSUser user){
        final ValidateTokenTO validateTokenTO = new ValidateTokenTO(
                user.getPersonalId(),
                user.getBankLoginId(),
                user.getPhoneNumber(),
                user.getLoginToken());
        loginService.validateToken(validateTokenTO);
        return new LoginData();
    }

    private LoginData loginAndCreateUser(@NonNull LoginRequest request){
        final PSULoginTO loginTO = new PSULoginTO(
                request.getId(),
                request.getCustomerNo(),
                request.getPhoneNo());
        CompletableFuture.supplyAsync(() -> loginService.login(loginTO))
                .thenComposeAsync(token -> getCreateUserCF(token, request)).join();
        return new LoginData();
    }

    private CompletableFuture<PSUser> getCreateUserCF(@NonNull String token, @NonNull LoginRequest request) {
        final CreateUserTO createUserTO = new CreateUserTO(
                request.getId(),
                request.getCustomerNo(),
                request.getPhoneNo(),
                token);
        return CompletableFuture.supplyAsync(() -> userDAO.createUser(createUserTO));
    }

}
