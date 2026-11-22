package com.knitty.knitty_backend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "detalle_wishlist")
public class DetalleWishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_wishlist")
    private Integer id;
    @Column(name = "fecha_agregado")
    private LocalDateTime fechaAgregado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_wishlist")
    private Wishlist wishlist;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patron")
    private Patron patron;
    public DetalleWishlist() {}
    public Integer getId() { return id; }
    public LocalDateTime getFechaAgregado() { return fechaAgregado; }
    public void setFechaAgregado(LocalDateTime value) { this.fechaAgregado = value; }
    public Wishlist getWishlist() { return wishlist; }
    public void setWishlist(Wishlist value) { this.wishlist = value; }
    public Patron getPatron() { return patron; }
    public void setPatron(Patron value) { this.patron = value; }
}
