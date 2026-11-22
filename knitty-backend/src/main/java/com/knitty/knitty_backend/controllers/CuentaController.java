package com.knitty.knitty_backend.controllers;

import com.knitty.knitty_backend.services.CuentaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/cuenta")
public class CuentaController {
    private final CuentaService cuenta;
    public CuentaController(CuentaService cuenta) { this.cuenta = cuenta; }

    @GetMapping("/resumen")
    public CuentaService.Resumen resumen(@RequestHeader("Authorization") String autorizacion) {
        if (!autorizacion.startsWith("Bearer ") || autorizacion.length() <= 7) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sesión requerida");
        }
        return cuenta.resumen(autorizacion.substring(7).trim());
    }
}
