package com.knitty.knitty_backend.controllers;

import com.knitty.knitty_backend.models.Publicacion;
import com.knitty.knitty_backend.services.PublicacionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {
    private final PublicacionService service;
    public PublicacionController(PublicacionService service) { this.service = service; }
    public record Vista(Integer id, String titulo, String descripcion, String imagenUrl, LocalDateTime fechaPublicacion) {}
    private static Vista map(Publicacion e) { return new Vista(e.getId(), e.getTitulo(), e.getDescripcion(), e.getImagenUrl(), e.getFechaPublicacion()); }
    @GetMapping
    public List<Vista> listar() { return service.listarInternamente().stream().map(PublicacionController::map).toList(); }
    @GetMapping("/{id}")
    public ResponseEntity<Vista> obtener(@PathVariable Integer id) {
        return service.buscarInternamente(id).map(PublicacionController::map)
            .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
