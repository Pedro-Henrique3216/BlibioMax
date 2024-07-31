package com.example.bibliomax.repository;

import com.example.bibliomax.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository {

    UserDetails findUserByEmail(String email);
}
