package com.service;


import com.service.entities.PSULoginTO;
import com.service.entities.ValidateTokenTO;

public interface RemoteLoginService {

    String login(PSULoginTO loginTO);

    String validateToken(ValidateTokenTO validateTokenTO);
}
