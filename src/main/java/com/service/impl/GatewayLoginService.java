package com.service.impl;

import com.google.inject.Singleton;
import com.service.RemoteLoginService;
import com.service.entities.PSULoginTO;
import com.service.entities.ValidateTokenTO;

@Singleton
public class GatewayLoginService implements RemoteLoginService {
    @Override
    public String login(PSULoginTO loginTO) {
        return null;
    }

    @Override
    public String validateToken(ValidateTokenTO validateTokenTO) {
        return null;
    }
}
