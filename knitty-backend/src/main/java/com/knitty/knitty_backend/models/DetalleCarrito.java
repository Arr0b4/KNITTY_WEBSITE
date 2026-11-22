package com.knitty.knitty_backend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "detalle_carrito")
public class DetalleCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_carrito")
    private Integer id;
    @Column(name = "fecha_agregado")
    private LocalDateTime fechaAgregado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrito")
    private Carrito carrito;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patron")
    private Patron patron;
    public DetalleCarrito() {}
    public Integer getId() { return id; }
    public LocalDateTime getFechaAgregado() { return fechaAgregado; }
    public void setFechaAgregado(LocalDateTime value) { this.fechaAgregado = value; }
    public Carrito getCarrito() { return carrito; }
    public void setCarrito(Carrito value) { this.carrito = value; }
    public Patron getPatron() { return patron; }
    public void setPatron(Patron value) { this.patron = value; }
}
