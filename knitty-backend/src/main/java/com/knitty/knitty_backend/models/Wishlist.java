package com.knitty.knitty_backend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_wishlist")
    private Integer id;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", unique = true)
    private Usuario usuario;
    public Wishlist() {}
    public Integer getId() { return id; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime value) { this.fechaCreacion = value; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario value) { this.usuario = value; }
}
