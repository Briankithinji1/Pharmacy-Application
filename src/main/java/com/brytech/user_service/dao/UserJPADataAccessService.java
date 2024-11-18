package com.brytech.user_service.dao;

import com.brytech.user_service.model.User;
import com.brytech.user_service.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userJPA")
public class UserJPADataAccessService implements UserDao {

    private final UserRepository userRepository;

    public UserJPADataAccessService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> selectUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
