package com.example.apifichaje.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity

public class Fichaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn (name= "usuario_id")
    private Usuario usuario;
    private LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;

}
