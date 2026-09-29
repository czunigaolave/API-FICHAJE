package com.example.apifichaje.controller;

import com.example.apifichaje.dto.LoginRQ;
import com.example.apifichaje.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

   private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
       this.usuarioService = usuarioService;
   }

   @PostMapping("/login")
    public String login(@RequestBody LoginRQ loginRQ){
       return usuarioService.entradaUsuario( loginRQ.nombre(),loginRQ.contrasena());
   }

}
