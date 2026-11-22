package com.knitty.knitty_backend.controllers;

import com.knitty.knitty_backend.models.Patron;
import com.knitty.knitty_backend.services.PatronService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/patrones")
public class PatronController {
    private final PatronService service;
    public PatronController(PatronService service) { this.service = service; }
    public record Vista(Integer id, String nombre, String descripcion, BigDecimal precio, String nivelDificultad, String imagenUrl, Boolean disponible, Integer idCategoria) {}
    private static Vista map(Patron e) { return new Vista(e.getId(), e.getNombre(), e.getDescripcion(), e.getPrecio(), e.getNivelDificultad(), e.getImagenUrl(), e.getDisponible(), e.getCategoria() == null ? null : e.getCategoria().getId()); }
    @GetMapping
    public List<Vista> listar() { return service.listarInternamente().stream().map(PatronController::map).toList(); }
    @GetMapping("/{id}")
    public ResponseEntity<Vista> obtener(@PathVariable Integer id) {
        return service.buscarInternamente(id).map(PatronController::map)
            .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
