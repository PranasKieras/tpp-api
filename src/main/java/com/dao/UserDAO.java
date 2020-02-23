package com.dao;

import com.dao.entity.PSUser;

import java.util.Optional;

public interface UserDAO {
    Optional<PSUser> fetchOne();

    PSUser createUser();
}
