package com.example.apifichaje.repository;

import com.example.apifichaje.model.Fichaje;
import com.example.apifichaje.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FichajeRepository extends JpaRepository<Fichaje, Long> {

    Optional<Fichaje> findByUsuarioAndFechaHoraSalidaIsNull(Usuario usuario);
}
