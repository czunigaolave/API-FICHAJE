package com.example.apifichaje.dto;

import java.time.LocalDateTime;

public record FichajeManualRQ(LocalDateTime fechaHoraEntrada, LocalDateTime fechaHoraSalida) {
}
