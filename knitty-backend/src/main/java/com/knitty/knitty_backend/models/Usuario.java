package com.knitty.knitty_backend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "email", length = 150)
    private String email;
    @Column(name = "contrasena_hash")
    private String contrasenaHash;
    @Column(name = "foto_perfil", length = 500)
    private String fotoPerfil;
    @Column(name = "biografia", columnDefinition = "TEXT")
    private String biografia;
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    public Usuario() {}
    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String value) { this.nombre = value; }
    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }
    public String getContrasenaHash() { return contrasenaHash; }
    public void setContrasenaHash(String value) { this.contrasenaHash = value; }
    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String value) { this.fotoPerfil = value; }
    public String getBiografia() { return biografia; }
    public void setBiografia(String value) { this.biografia = value; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime value) { this.fechaRegistro = value; }
}
