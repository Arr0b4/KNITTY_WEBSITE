package com.knitty.knitty_backend.services;

import com.knitty.knitty_backend.repositories.CuentaConsultasRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CuentaService {
    public record Diseno(Integer id, String nombre, BigDecimal precio) {}
    public record Resumen(AuthService.Perfil usuario, List<Diseno> wishlist,
                          List<Diseno> compras, List<Diseno> ventas,
                          int cantidadComprada, int cantidadVendida, BigDecimal ingresosBrutos) {}

    private final AuthService auth;
    private final CuentaConsultasRepository consultas;

    public CuentaService(AuthService auth, CuentaConsultasRepository consultas) {
        this.auth = auth;
        this.consultas = consultas;
    }

    @Transactional(readOnly = true)
    public Resumen resumen(String token) {
        AuthService.Perfil usuario = auth.perfil(token);
        List<Diseno> wishlist = convertir(consultas.favoritos(usuario.id()));
        List<Diseno> compras = convertir(consultas.comprasPagadas(usuario.id()));
        List<Diseno> ventas = convertir(consultas.ventasPagadas(usuario.id()));
        BigDecimal bruto = ventas.stream().map(Diseno::precio).reduce(BigDecimal.ZERO, BigDecimal::add);
        return new Resumen(usuario, wishlist, compras, ventas, compras.size(), ventas.size(), bruto);
    }

    private List<Diseno> convertir(List<Object[]> filas) {
        return filas.stream().map(r -> new Diseno((Integer) r[0], (String) r[1], (BigDecimal) r[2])).toList();
    }
}
