package com.knitty.knitty_backend.controllers;

import com.knitty.knitty_backend.models.Resena;
import com.knitty.knitty_backend.services.ResenaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {
    private final ResenaService service;
    public ResenaController(ResenaService service) { this.service = service; }
    public record Vista(Integer id, Byte puntuacion, String comentario, LocalDateTime fechaResena) {}
    private static Vista map(Resena e) { return new Vista(e.getId(), e.getPuntuacion(), e.getComentario(), e.getFechaResena()); }
    @GetMapping
    public List<Vista> listar() { return service.listarInternamente().stream().map(ResenaController::map).toList(); }
    @GetMapping("/{id}")
    public ResponseEntity<Vista> obtener(@PathVariable Integer id) {
        return service.buscarInternamente(id).map(ResenaController::map)
            .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
