package com.dao;

import com.dao.entity.PSUser;
import com.service.entities.CreateUserTO;
import com.service.entities.FetchUserTO;

import java.util.Optional;

public interface UserDAO {
    Optional<PSUser> fetchUser(FetchUserTO fetchUserTO);

    PSUser createUser(CreateUserTO createUserTO);
}
