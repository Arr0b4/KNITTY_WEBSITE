package com.knitty.knitty_backend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer id;
    @Column(name = "importe")
    private BigDecimal importe;
    @Column(name = "metodo_pago")
    private String metodoPago;
    @Column(name = "estado_pago")
    private String estadoPago;
    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
    public Pago() {}
    public Integer getId() { return id; }
    public BigDecimal getImporte() { return importe; }
    public void setImporte(BigDecimal value) { this.importe = value; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String value) { this.metodoPago = value; }
    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String value) { this.estadoPago = value; }
    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime value) { this.fechaPago = value; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido value) { this.pedido = value; }
}
