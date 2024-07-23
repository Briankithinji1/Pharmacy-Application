package main.java.com.example.Pharmacy.Application.user.dao;

import main.java.com.example.Pharmacy.Application.user.model.User;
import main.java.com.example.Pharmacy.Application.user.repository.UserRepository;
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
