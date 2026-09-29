package com.example.apifichaje.controller;

import com.example.apifichaje.dto.FichajeManualRQ;
import com.example.apifichaje.dto.ListadoFichajesDto;
import com.example.apifichaje.service.FichajeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/fichajes")
public class FichajeController {
     private final FichajeService fichajeService;

    public FichajeController(FichajeService fichajeService) {
        this.fichajeService = fichajeService;
    }

    @GetMapping
    public List<ListadoFichajesDto> listarFichaje(@RequestHeader("Codigo-X") String codigo) {
        return fichajeService.listFichaje(codigo);
    }

    @GetMapping("/activo")
    public LocalDateTime activo(@RequestHeader("Codigo-X") String codigo) {
        return fichajeService.horaEntradaActiva(codigo);
    }

    @PostMapping("/entrada")
    public ListadoFichajesDto entrada(@RequestHeader("Codigo-X") String codigo) {
        return fichajeService.ficharEntrada(codigo);
    }

    @PatchMapping("/salida")
    public ListadoFichajesDto salida(@RequestHeader("Codigo-X") String codigo) {
        return fichajeService.ficharSalida(codigo);
    }

    @PostMapping("/manual")
    public ListadoFichajesDto manual(@RequestHeader("Codigo-X") String codigo, @RequestBody FichajeManualRQ fichajeManualRQ) {
        return fichajeService.fichajeManual(codigo,fichajeManualRQ.fechaHoraEntrada(),fichajeManualRQ.fechaHoraSalida());
    }

}
