package com.example.bibliomax.service;

import com.example.bibliomax.exceptions.UserAlreadyRegisteredException;
import com.example.bibliomax.model.Role;
import com.example.bibliomax.model.User;
import com.example.bibliomax.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User cadastrarUser(String email, String senha, Role role) {
        if(repository.existsByUsername(email)) {
            throw new UserAlreadyRegisteredException("User already registered.");
        }
        senha = passwordEncoder.encode(senha);
        User user = new User(email, senha, role);
        return repository.save(user);
    }

}
