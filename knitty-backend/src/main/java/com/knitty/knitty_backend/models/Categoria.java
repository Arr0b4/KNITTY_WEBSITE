package com.knitty.knitty_backend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    public Categoria() {}
    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String value) { this.nombre = value; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String value) { this.descripcion = value; }
}
