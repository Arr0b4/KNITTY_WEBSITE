package com.knitty.knitty_backend.controllers;

import com.knitty.knitty_backend.services.CarritoService;
import org.springframework.stereotype.Controller;

// Reservado para endpoints autenticados. No exponer datos privados sin Spring Security.
@Controller
public class CarritoController {
    private final CarritoService service;
    public CarritoController(CarritoService service) { this.service = service; }
}
