package com.knitty.knitty_backend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "patron")
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patron")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    @Column(name = "precio")
    private BigDecimal precio;
    @Column(name = "nivel_dificultad")
    private String nivelDificultad;
    @Column(name = "archivo_pdf", length = 500)
    private String archivoPdf;
    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;
    @Column(name = "disponible")
    private Boolean disponible;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
    public Patron() {}
    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String value) { this.nombre = value; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String value) { this.descripcion = value; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal value) { this.precio = value; }
    public String getNivelDificultad() { return nivelDificultad; }
    public void setNivelDificultad(String value) { this.nivelDificultad = value; }
    public String getArchivoPdf() { return archivoPdf; }
    public void setArchivoPdf(String value) { this.archivoPdf = value; }
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String value) { this.imagenUrl = value; }
    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean value) { this.disponible = value; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario value) { this.usuario = value; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria value) { this.categoria = value; }
}
