package com.knitty.knitty_backend.controllers;

import com.knitty.knitty_backend.services.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    public record Registro(@NotBlank @Size(max = 100) String nombre,
                           @NotBlank @Email @Size(max = 150) String email,
                           @NotBlank @Size(min = 8, max = 72) String password) {}
    public record Acceso(@NotBlank @Email String email, @NotBlank String password) {}

    private final AuthService auth;
    public AuthController(AuthService auth) { this.auth = auth; }

    @PostMapping("/registro")
    public ResponseEntity<AuthService.Sesion> registrar(@Valid @RequestBody Registro datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(auth.registrar(datos.nombre(), datos.email(), datos.password()));
    }

    @PostMapping("/login")
    public AuthService.Sesion acceder(@Valid @RequestBody Acceso datos) {
        return auth.acceder(datos.email(), datos.password());
    }

    @GetMapping("/yo")
    public AuthService.Perfil yo(@RequestHeader("Authorization") String cabecera) {
        return auth.perfil(extraerToken(cabecera));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> salir(@RequestHeader("Authorization") String cabecera) {
        auth.cerrarSesion(extraerToken(cabecera));
        return ResponseEntity.noContent().build();
    }

    private String extraerToken(String cabecera) {
        if (!cabecera.startsWith("Bearer ") || cabecera.length() <= 7)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Falta el token de sesión");
        return cabecera.substring(7).trim();
    }
}
