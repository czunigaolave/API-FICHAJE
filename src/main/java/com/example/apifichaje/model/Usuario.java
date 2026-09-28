package com.example.apifichaje.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTOINCREMENTO PARA BD COMO MYSQL
    private Long id;
    private String nombre;
    @Column(unique = true)
    private String codigo;

    public Usuario(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }
    public Usuario() {}


}
