package com.example.Pharmacy.Application.config.security;

import com.example.Pharmacy.Application.user.dao.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements UserDetailsService {

    private final UserDao userDao;

    public SecurityService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.selectUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Username " + username + " not found"
                ));
    }
}
