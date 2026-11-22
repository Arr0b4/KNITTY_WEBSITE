package com.knitty.knitty_backend.services;

import com.knitty.knitty_backend.models.Pago;
import com.knitty.knitty_backend.repositories.PagoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {
    private final PagoRepository repository;
    public PagoService(PagoRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true)
    public List<Pago> listarInternamente() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public Optional<Pago> buscarInternamente(Integer id) { return repository.findById(id); }
    // Altas, cambios y bajas se agregarán con validación y autorización.
}
