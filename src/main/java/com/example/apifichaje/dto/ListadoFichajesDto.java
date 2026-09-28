package com.example.apifichaje.dto;

import java.time.LocalDateTime;

public record ListadoFichajesDto(Long id, LocalDateTime fechaHoraEntrada, LocalDateTime fechaHoraSalida, Long totalMinutos) {

}
