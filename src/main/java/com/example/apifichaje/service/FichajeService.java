package com.example.apifichaje.service;


import com.example.apifichaje.model.Fichaje;
import com.example.apifichaje.model.Usuario;
import com.example.apifichaje.repository.FichajeRepository;
import com.example.apifichaje.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class FichajeService {

    private final FichajeRepository repo;

    private final UsuarioRepository usuarioRepository;

    public FichajeService(FichajeRepository repo, UsuarioRepository usuarioRepository) {
        this.repo = repo;
        this.usuarioRepository = usuarioRepository;
    }

    public Fichaje ficharEntrada(String codigo) {

            Optional<Usuario> user = usuarioRepository.findByCodigo(codigo);

            if(!user.isPresent()){
                throw new RuntimeException("Usuario no encontrado");
            }else{
                Usuario usuario = user.get();

                Optional<Fichaje> fichaje = repo.findByUsuarioAndFechaHoraSalidaIsNull(usuario);

                if(fichaje.isPresent()){
                    throw new RuntimeException("Ya tienes una entrada registrada sin salida");
                }

                Fichaje newFichaje = new Fichaje ();

                newFichaje.setUsuario(usuario);
                newFichaje.setFechaHoraEntrada( LocalDateTime.now());
                repo.save(newFichaje);


                return newFichaje;


            }

    }


    public Fichaje ficharSalida(String codigo) {

        Optional<Usuario> user = usuarioRepository.findByCodigo(codigo);

        if (!user.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        } else {
            Usuario usuario = user.get();

            Optional<Fichaje> fichaje = repo.findByUsuarioAndFechaHoraSalidaIsNull(usuario);
            if(!fichaje.isPresent()){
                throw new RuntimeException("No hay una entrada registrada");
            }

            Fichaje ficharsalida = fichaje.get();
            ficharsalida.setFechaHoraSalida( LocalDateTime.now());
            repo.save(ficharsalida);

            return ficharsalida;
        }
    }

    public Fichaje ficahjeManual(String codigo, LocalDateTime fechaHoraEntrada, LocalDateTime fechaHoraSalida) {
        Optional<Usuario> user = usuarioRepository.findByCodigo(codigo);
        if (!user.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        } else {
            Fichaje fichaje = new Fichaje ();
            fichaje.setUsuario(user.get());
            fichaje.setFechaHoraEntrada( fechaHoraEntrada);
            fichaje.setFechaHoraSalida( fechaHoraSalida);

            repo.save(fichaje);
            return  fichaje;
        }
    }




}


