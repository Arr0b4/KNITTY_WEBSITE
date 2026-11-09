package com.knitty.knitty_backend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "resena")
public class Resena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resena")
    private Integer id;
    @Column(name = "puntuacion", columnDefinition = "TINYINT UNSIGNED")
    private Byte puntuacion;
    @Column(name = "comentario", columnDefinition = "TEXT")
    private String comentario;
    @Column(name = "fecha_resena")
    private LocalDateTime fechaResena;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patron")
    private Patron patron;
    public Resena() {}
    public Integer getId() { return id; }
    public Byte getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Byte puntuacion) {
        this.puntuacion = puntuacion;
    }
    public String getComentario() { return comentario; }
    public void setComentario(String value) { this.comentario = value; }
    public LocalDateTime getFechaResena() { return fechaResena; }
    public void setFechaResena(LocalDateTime value) { this.fechaResena = value; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario value) { this.usuario = value; }
    public Patron getPatron() { return patron; }
    public void setPatron(Patron value) { this.patron = value; }
}
