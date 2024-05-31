package com.example.bibliomax.service;

import com.example.bibliomax.DTO.RetornaBibliotecarioDto;
import com.example.bibliomax.exceptions.ObjectNotFoundException;
import com.example.bibliomax.model.Bibliotecario;
import com.example.bibliomax.model.BibliotecarioDto;
import com.example.bibliomax.repository.BibliotecarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class BibliotecarioService {

    @Autowired
    private BibliotecarioRepository bibliotecarioRepository;

    public Bibliotecario cadastrarBibliotecario(Bibliotecario bibliotecario) {
        return bibliotecarioRepository.save(bibliotecario);
    }

    public List<Bibliotecario> listarBibliotecarios() {
        return bibliotecarioRepository.findAll();
    }

    public Bibliotecario buscarBibliotecarioPorId(Long id) {
        return bibliotecarioRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Bibliotecario não encontrado!"));
    }

    public Bibliotecario atualizaBibliotecario(Long id, BibliotecarioDto bibliotecarioDto) {
        Bibliotecario bibliotecario = buscarBibliotecarioPorId(id);
        if (bibliotecarioDto.nome() != null) {
            bibliotecario.setNome(bibliotecarioDto.nome());
        }
        if (bibliotecarioDto.telefone() != null) {
            bibliotecario.setTelefone(bibliotecarioDto.telefone());
        }
        if (bibliotecarioDto.email() != null) {
            bibliotecario.setEmail(bibliotecarioDto.email());
        }
        if (bibliotecarioDto .senha() != null){
            bibliotecario.setSenha(bibliotecarioDto.senha());
        }
        return bibliotecario;
    }
}
