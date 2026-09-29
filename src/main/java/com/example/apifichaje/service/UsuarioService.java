package com.example.apifichaje.service;

import com.example.apifichaje.model.Usuario;
import com.example.apifichaje.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String entradaUsuario(String nombre, String contrasena) {
        if (!(nombre  != null && contrasena != null)) {
            throw new RuntimeException("Datos no validos");
        }


        Optional<Usuario> usuario = usuarioRepository.findByNombreAndContrasena(nombre,contrasena);

        if (!usuario.isPresent()) {
            throw new RuntimeException("Usuario o contrasela incorrectos");
        }


        return usuario.get().getCodigo();

    }

}
