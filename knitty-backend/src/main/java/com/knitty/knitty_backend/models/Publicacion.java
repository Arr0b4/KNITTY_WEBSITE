package com.knitty.knitty_backend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "publicacion")
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_publicacion")
    private Integer id;
    @Column(name = "titulo", length = 150)
    private String titulo;
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;
    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    public Publicacion() {}
    public Integer getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String value) { this.titulo = value; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String value) { this.descripcion = value; }
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String value) { this.imagenUrl = value; }
    public LocalDateTime getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDateTime value) { this.fechaPublicacion = value; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario value) { this.usuario = value; }
}
