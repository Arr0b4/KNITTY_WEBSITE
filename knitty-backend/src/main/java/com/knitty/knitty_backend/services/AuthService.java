package com.knitty.knitty_backend.services;

import com.knitty.knitty_backend.models.Usuario;
import com.knitty.knitty_backend.repositories.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {
    public record Perfil(Integer id, String nombre, String email) {}
    public record Sesion(String token, Perfil usuario) {}
    private record SesionGuardada(Integer usuarioId, Instant expira) {}

    private final UsuarioRepository usuarios;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final SecureRandom aleatorio = new SecureRandom();
    private final Map<String, SesionGuardada> sesiones = new ConcurrentHashMap<>();

    public AuthService(UsuarioRepository usuarios) { this.usuarios = usuarios; }

    public Sesion registrar(String nombre, String email, String password) {
        String correo = normalizar(email);
        if (usuarios.existsByEmailIgnoreCase(correo)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre.trim());
        usuario.setEmail(correo);
        usuario.setContrasenaHash(encoder.encode(password));
        usuario.setFechaRegistro(LocalDateTime.now());
        try {
            usuario = usuarios.saveAndFlush(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }
        return iniciarSesion(usuario);
    }

    public Sesion acceder(String email, String password) {
        Usuario usuario = usuarios.findByEmailIgnoreCase(normalizar(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas"));
        if (!encoder.matches(password, usuario.getContrasenaHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas");
        }
        return iniciarSesion(usuario);
    }

    public Perfil perfil(String token) {
        SesionGuardada sesion = sesiones.get(token);
        if (sesion == null || Instant.now().isAfter(sesion.expira())) {
            sesiones.remove(token);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sesión no válida o caducada");
        }
        return usuarios.findById(sesion.usuarioId()).map(this::toPerfil)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado"));
    }

    public void cerrarSesion(String token) { sesiones.remove(token); }

    private Sesion iniciarSesion(Usuario usuario) {
        byte[] bytes = new byte[32];
        aleatorio.nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        sesiones.put(token, new SesionGuardada(usuario.getId(), Instant.now().plusSeconds(7200)));
        return new Sesion(token, toPerfil(usuario));
    }

    private Perfil toPerfil(Usuario usuario) { return new Perfil(usuario.getId(), usuario.getNombre(), usuario.getEmail()); }
    private String normalizar(String email) { return email.trim().toLowerCase(java.util.Locale.ROOT); }
}
