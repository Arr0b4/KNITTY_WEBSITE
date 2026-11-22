package com.knitty.knitty_backend.controllers;

import com.knitty.knitty_backend.services.DetalleWishlistService;
import org.springframework.stereotype.Controller;

// Reservado para endpoints autenticados. No exponer datos privados sin Spring Security.
@Controller
public class DetalleWishlistController {
    private final DetalleWishlistService service;
    public DetalleWishlistController(DetalleWishlistService service) { this.service = service; }
}
