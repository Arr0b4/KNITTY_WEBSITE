package com.knitty.knitty_backend.controllers;

import com.knitty.knitty_backend.services.DetallePedidoService;
import org.springframework.stereotype.Controller;

// Reservado para endpoints autenticados. No exponer datos privados sin Spring Security.
@Controller
public class DetallePedidoController {
    private final DetallePedidoService service;
    public DetallePedidoController(DetallePedidoService service) { this.service = service; }
}
