package com.example.bibliomax.controller;

import com.example.bibliomax.dto.TokenDto;
import com.example.bibliomax.infra.security.TokenService;
import com.example.bibliomax.model.User;
import com.example.bibliomax.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    @PostMapping
    public ResponseEntity<TokenDto> logar(@RequestBody UserDto userDto){
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDto.email(), userDto.senha());
        Authentication authentication = authenticationManager.authenticate(auth);
        String token = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenDto(token));
    }
}
