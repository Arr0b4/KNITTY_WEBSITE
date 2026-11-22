package com.knitty.knitty_backend.controllers;

import com.knitty.knitty_backend.services.WishlistService;
import org.springframework.stereotype.Controller;

// Reservado para endpoints autenticados. No exponer datos privados sin Spring Security.
@Controller
public class WishlistController {
    private final WishlistService service;
    public WishlistController(WishlistService service) { this.service = service; }
}
