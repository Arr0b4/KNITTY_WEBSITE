package com.knitty.knitty_backend.controllers;

import com.knitty.knitty_backend.models.Categoria;
import com.knitty.knitty_backend.services.CategoriaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService service;
    public CategoriaController(CategoriaService service) { this.service = service; }
    public record Vista(Integer id, String nombre, String descripcion) {}
    private static Vista map(Categoria e) { return new Vista(e.getId(), e.getNombre(), e.getDescripcion()); }
    @GetMapping
    public List<Vista> listar() { return service.listarInternamente().stream().map(CategoriaController::map).toList(); }
    @GetMapping("/{id}")
    public ResponseEntity<Vista> obtener(@PathVariable Integer id) {
        return service.buscarInternamente(id).map(CategoriaController::map)
            .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
