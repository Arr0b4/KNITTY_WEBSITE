package com.knitty.knitty_backend.services;

import com.knitty.knitty_backend.models.DetalleWishlist;
import com.knitty.knitty_backend.repositories.DetalleWishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DetalleWishlistService {
    private final DetalleWishlistRepository repository;
    public DetalleWishlistService(DetalleWishlistRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true)
    public List<DetalleWishlist> listarInternamente() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public Optional<DetalleWishlist> buscarInternamente(Integer id) { return repository.findById(id); }
    // Altas, cambios y bajas se agregarán con validación y autorización.
}
