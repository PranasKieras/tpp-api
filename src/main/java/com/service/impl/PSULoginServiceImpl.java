package com.service.impl;

import com.dao.UserDAO;
import com.dao.entity.PSUser;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.rest.request.LoginRequest;
import com.service.PSULoginService;
import com.service.RemoteLoginService;
import com.service.entities.CreateUserTO;
import com.service.entities.FetchUserTO;
import com.service.entities.PSULoginTO;
import com.service.entities.ValidateTokenTO;
import com.service.entities.LoginData;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

@Singleton
public class PSULoginServiceImpl implements PSULoginService {

    @Inject
    RemoteLoginService loginService;
    @Inject
    UserDAO userDAO;

    @Override
    public LoginData login(@NonNull LoginRequest loginRequest) {
        final FetchUserTO fetchUserTO = new FetchUserTO(
                loginRequest.getPersonalId(),
                loginRequest.getBankLoginId(),
                loginRequest.getPhoneNumber());

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
                request.getPersonalId(),
                request.getBankLoginId(),
                request.getPhoneNumber());

        CompletableFuture.supplyAsync(() -> loginService.login(loginTO))
                .thenComposeAsync(token -> getCreateUserCF("token", request)).join();

        return new LoginData();
    }

    private CompletableFuture<PSUser> getCreateUserCF(@NonNull String token, @NonNull LoginRequest request) {
        final CreateUserTO createUserTO = new CreateUserTO(
                request.getPersonalId(),
                request.getBankLoginId(),
                request.getPhoneNumber(),
                token);

        return CompletableFuture.supplyAsync(() -> userDAO.createUser(createUserTO));
    }

}
