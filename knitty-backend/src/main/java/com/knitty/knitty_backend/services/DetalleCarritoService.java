package com.knitty.knitty_backend.services;

import com.knitty.knitty_backend.models.DetalleCarrito;
import com.knitty.knitty_backend.repositories.DetalleCarritoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DetalleCarritoService {
    private final DetalleCarritoRepository repository;
    public DetalleCarritoService(DetalleCarritoRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true)
    public List<DetalleCarrito> listarInternamente() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public Optional<DetalleCarrito> buscarInternamente(Integer id) { return repository.findById(id); }
    // Altas, cambios y bajas se agregarán con validación y autorización.
}
