package com.knitty.knitty_backend.services;

import com.knitty.knitty_backend.models.Usuario;
import com.knitty.knitty_backend.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    public UsuarioService(UsuarioRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true)
    public List<Usuario> listarInternamente() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarInternamente(Integer id) { return repository.findById(id); }
    // Altas, cambios y bajas se agregarán con validación y autorización.
}
