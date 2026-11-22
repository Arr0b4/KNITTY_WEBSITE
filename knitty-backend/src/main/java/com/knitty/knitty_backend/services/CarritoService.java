package com.knitty.knitty_backend.services;

import com.knitty.knitty_backend.models.Carrito;
import com.knitty.knitty_backend.repositories.CarritoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {
    private final CarritoRepository repository;
    public CarritoService(CarritoRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true)
    public List<Carrito> listarInternamente() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public Optional<Carrito> buscarInternamente(Integer id) { return repository.findById(id); }
    // Altas, cambios y bajas se agregarán con validación y autorización.
}
