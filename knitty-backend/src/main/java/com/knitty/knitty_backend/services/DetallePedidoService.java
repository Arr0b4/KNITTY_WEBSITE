package com.knitty.knitty_backend.services;

import com.knitty.knitty_backend.models.DetallePedido;
import com.knitty.knitty_backend.repositories.DetallePedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DetallePedidoService {
    private final DetallePedidoRepository repository;
    public DetallePedidoService(DetallePedidoRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true)
    public List<DetallePedido> listarInternamente() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public Optional<DetallePedido> buscarInternamente(Integer id) { return repository.findById(id); }
    // Altas, cambios y bajas se agregarán con validación y autorización.
}
