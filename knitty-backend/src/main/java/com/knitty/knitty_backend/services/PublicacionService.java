package com.knitty.knitty_backend.services;

import com.knitty.knitty_backend.models.Publicacion;
import com.knitty.knitty_backend.repositories.PublicacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PublicacionService {
    private final PublicacionRepository repository;
    public PublicacionService(PublicacionRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true)
    public List<Publicacion> listarInternamente() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public Optional<Publicacion> buscarInternamente(Integer id) { return repository.findById(id); }
    // Altas, cambios y bajas se agregarán con validación y autorización.
}
