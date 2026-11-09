package com.knitty.knitty_backend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_pedido")
    private Integer id;
    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patron")
    private Patron patron;
    public DetallePedido() {}
    public Integer getId() { return id; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal value) { this.precioUnitario = value; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido value) { this.pedido = value; }
    public Patron getPatron() { return patron; }
    public void setPatron(Patron value) { this.patron = value; }
}
