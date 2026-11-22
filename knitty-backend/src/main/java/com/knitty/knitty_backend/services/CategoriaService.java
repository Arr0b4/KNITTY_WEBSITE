package com.knitty.knitty_backend.services;

import com.knitty.knitty_backend.models.Categoria;
import com.knitty.knitty_backend.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    private final CategoriaRepository repository;
    public CategoriaService(CategoriaRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true)
    public List<Categoria> listarInternamente() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public Optional<Categoria> buscarInternamente(Integer id) { return repository.findById(id); }
}
