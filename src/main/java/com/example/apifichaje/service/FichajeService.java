package com.example.apifichaje.service;


import com.example.apifichaje.dto.ListadoFichajesDto;
import com.example.apifichaje.model.Fichaje;
import com.example.apifichaje.model.Usuario;
import com.example.apifichaje.repository.FichajeRepository;
import com.example.apifichaje.repository.UsuarioRepository;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

        if (!user.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        } else {
            Usuario usuario = user.get();

            Optional<Fichaje> fichaje = repo.findByUsuarioAndFechaHoraSalidaIsNull(usuario);

            if (fichaje.isPresent()) {
                throw new RuntimeException("Ya tienes una entrada registrada sin salida");
            }

            Fichaje newFichaje = new Fichaje();

            newFichaje.setUsuario(usuario);
            newFichaje.setFechaHoraEntrada(LocalDateTime.now());
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
            if (!fichaje.isPresent()) {
                throw new RuntimeException("No hay una entrada registrada");
            }

            Fichaje ficharsalida = fichaje.get();
            ficharsalida.setFechaHoraSalida(LocalDateTime.now());
            repo.save(ficharsalida);

            return ficharsalida;
        }
    }

    public Fichaje fichajeManual(String codigo, LocalDateTime fechaHoraEntrada, LocalDateTime fechaHoraSalida) {
        if (fechaHoraEntrada != null && fechaHoraSalida != null && fechaHoraEntrada.isBefore(fechaHoraSalida)) {

            Optional<Usuario> user = usuarioRepository.findByCodigo(codigo);
            if (!user.isPresent()) {
                throw new RuntimeException("Usuario no encontrado");
            } else {
                Usuario usuario = user.get();
                Optional<Fichaje> fichaje = repo.findByUsuarioAndFechaHoraSalidaIsNull(usuario);
                if (fichaje.isPresent()) {
                    throw new RuntimeException("Ya tienes una entrada registrada sin salida");
                }
                Fichaje fichar = new Fichaje();
                fichar.setUsuario(user.get());
                fichar.setFechaHoraEntrada(fechaHoraEntrada);
                fichar.setFechaHoraSalida(fechaHoraSalida);
                repo.save(fichar);
                return fichar;
            }
        } else {
            throw new RuntimeException("FechaHoraEntrada o FechaHoraSalida no valido");
        }

    }

    public List<ListadoFichajesDto> listFichaje(String codigo) {
        Optional<Usuario> user = usuarioRepository.findByCodigo(codigo);
        if (!user.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        List<Fichaje> list = repo.findByUsuarioOrderByFechaHoraEntradaDesc(user.get());
        if (list.isEmpty()) {
            throw new RuntimeException("No hay una entrada registrada");
        }
        ArrayList<ListadoFichajesDto> listadoFichajesDto = new ArrayList<>();

        for (Fichaje fichaje : list) {

            LocalDateTime fin;

            if (fichaje.getFechaHoraSalida() != null) {
                fin = fichaje.getFechaHoraSalida();
            } else {
                fin = LocalDateTime.now();
            }

            Long id = fichaje.getId();
            LocalDateTime fechaHoraEntrada = fichaje.getFechaHoraEntrada();
            Duration duracion = Duration.between(fechaHoraEntrada, fin);
            Long totalMinutos = duracion.toMinutes();

            ListadoFichajesDto lista = new ListadoFichajesDto(id, fechaHoraEntrada, fichaje.getFechaHoraSalida(), totalMinutos);
            listadoFichajesDto.add(lista);


        }
        return listadoFichajesDto;
    }

    public LocalDateTime horaEntradaActiva(String codigo) {

        Optional<Usuario> user = usuarioRepository.findByCodigo(codigo);

        if (!user.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Optional<Fichaje> fichaje = repo.findByUsuarioAndFechaHoraSalidaIsNull(user.get());
        if (fichaje.isPresent()) {
            return fichaje.get().getFechaHoraEntrada();
        }
        return null;


    }


}


