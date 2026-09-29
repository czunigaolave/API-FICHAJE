package com.example.apifichaje.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTOINCREMENTO PARA BD COMO MYSQL
    private Long id;
    @Column (unique = true)
    private String nombre;
    private String contrasena;
    @Column(unique = true)
    private String codigo;

    public Usuario(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }
    public Usuario() {}


}
