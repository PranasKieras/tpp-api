package com.dao.impl;

import com.dao.UserDAO;
import com.dao.entity.PSUser;

import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    @Override
    public Optional<PSUser> fetchOne() {
        return Optional.empty();
    }

    @Override
    public PSUser createUser() {
        return null;
    }
}
