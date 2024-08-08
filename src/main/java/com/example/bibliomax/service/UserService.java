package com.example.bibliomax.service;

import com.example.bibliomax.model.Role;
import com.example.bibliomax.model.User;
import com.example.bibliomax.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User cadastrarUser(String email, String senha, Role role) {
        senha = passwordEncoder.encode(senha);
        User user = new User(email, senha, role);
        return repository.save(user);
    }

}
