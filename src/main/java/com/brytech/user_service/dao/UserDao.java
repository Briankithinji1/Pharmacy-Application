package com.brytech.user_service.dao;

import com.brytech.user_service.model.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> selectUserByEmail(String email);
}
