package com.knitty.knitty_backend.controllers;

import com.knitty.knitty_backend.services.PagoService;
import org.springframework.stereotype.Controller;

// Reservado para endpoints autenticados. No exponer datos privados sin Spring Security.
@Controller
public class PagoController {
    private final PagoService service;
    public PagoController(PagoService service) { this.service = service; }
}
