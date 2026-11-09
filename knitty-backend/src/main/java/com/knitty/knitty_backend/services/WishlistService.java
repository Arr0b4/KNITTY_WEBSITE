package com.knitty.knitty_backend.services;

import com.knitty.knitty_backend.models.Wishlist;
import com.knitty.knitty_backend.repositories.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {
    private final WishlistRepository repository;
    public WishlistService(WishlistRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true)
    public List<Wishlist> listarInternamente() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public Optional<Wishlist> buscarInternamente(Integer id) { return repository.findById(id); }
    // Altas, cambios y bajas se agregarán con validación y autorización.
}
