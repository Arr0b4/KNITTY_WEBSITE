package com.knitty.knitty_backend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer id;
    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "estado")
    private String estado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    public Pedido() {}
    public Integer getId() { return id; }
    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDateTime value) { this.fechaPedido = value; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal value) { this.total = value; }
    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario value) { this.usuario = value; }
}
