package com.knitty.knitty_backend.controllers;

import com.knitty.knitty_backend.services.DetalleCarritoService;
import org.springframework.stereotype.Controller;

// Reservado para endpoints autenticados. No exponer datos privados sin Spring Security.
@Controller
public class DetalleCarritoController {
    private final DetalleCarritoService service;
    public DetalleCarritoController(DetalleCarritoService service) { this.service = service; }
}
