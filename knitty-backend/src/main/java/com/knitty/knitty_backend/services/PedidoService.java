package com.knitty.knitty_backend.services;

import com.knitty.knitty_backend.models.Pedido;
import com.knitty.knitty_backend.repositories.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    private final PedidoRepository repository;
    public PedidoService(PedidoRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true)
    public List<Pedido> listarInternamente() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public Optional<Pedido> buscarInternamente(Integer id) { return repository.findById(id); }
    // Altas, cambios y bajas se agregarán con validación y autorización.
}
