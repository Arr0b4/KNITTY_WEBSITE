package com.knitty.knitty_backend.services;

import com.knitty.knitty_backend.models.Resena;
import com.knitty.knitty_backend.repositories.ResenaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ResenaService {
    private final ResenaRepository repository;
    public ResenaService(ResenaRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true)
    public List<Resena> listarInternamente() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public Optional<Resena> buscarInternamente(Integer id) { return repository.findById(id); }
    // Altas, cambios y bajas se agregarán con validación y autorización.
}
