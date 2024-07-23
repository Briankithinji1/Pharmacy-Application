package main.java.com.example.Pharmacy.Application.user.repository;

import jakarta.transaction.Transactional;
import main.java.com.example.Pharmacy.Application.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
}
