package com.example.Pharmacy.Application.config.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = """
            select t from Token t inner join User u\s
            on t.user.id = u.id\s
            where t.user.id = :userId and (t.expired = false or t.revoked = false)
            """)
    List<Token> findAllValidTokenByUser(Long userId);

    @Query("""
            SELECT t FROM Token t
            WHERE t.token = :token
            """)
    Optional<Token> findByToken(String token);
}
