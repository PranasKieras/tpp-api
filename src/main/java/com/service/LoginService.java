package com.service;


import com.service.entities.PSULoginTO;
import com.service.entities.ValidateTokenTO;

public interface LoginService {


    String login(PSULoginTO loginTO);


    String validateToken(ValidateTokenTO validateTokenTO);
}
