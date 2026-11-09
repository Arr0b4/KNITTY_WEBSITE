package com.knitty.knitty_backend.controllers;

import com.knitty.knitty_backend.services.PedidoService;
import org.springframework.stereotype.Controller;

// Reservado para endpoints autenticados. No exponer datos privados sin Spring Security.
@Controller
public class PedidoController {
    private final PedidoService service;
    public PedidoController(PedidoService service) { this.service = service; }
}
