package com.example.Pharmacy.Application.user.dao;

import com.example.Pharmacy.Application.user.model.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> selectUserByEmail(String email);
}
