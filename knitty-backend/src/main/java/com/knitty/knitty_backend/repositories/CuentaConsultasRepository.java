package com.knitty.knitty_backend.repositories;

import com.knitty.knitty_backend.models.DetallePedido;
import com.knitty.knitty_backend.models.DetalleWishlist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;

/** Consultas de lectura compartidas por la web y la aplicación Android. */
public interface CuentaConsultasRepository extends Repository<DetallePedido, Integer> {
    @Query("select p.id, p.nombre, p.precio from DetalleWishlist d join d.wishlist w join d.patron p where w.usuario.id = :usuarioId")
    List<Object[]> favoritos(@Param("usuarioId") Integer usuarioId);

    @Query("select p.id, p.nombre, d.precioUnitario from DetallePedido d join d.pedido ped join d.patron p " +
           "where ped.usuario.id = :usuarioId and exists " +
           "(select pag.id from Pago pag where pag.pedido = ped and lower(pag.estadoPago) in ('pagado', 'completado', 'aprobado', 'paid', 'succeeded'))")
    List<Object[]> comprasPagadas(@Param("usuarioId") Integer usuarioId);

    @Query("select p.id, p.nombre, d.precioUnitario from DetallePedido d join d.pedido ped join d.patron p " +
           "where p.usuario.id = :usuarioId and exists " +
           "(select pag.id from Pago pag where pag.pedido = ped and lower(pag.estadoPago) in ('pagado', 'completado', 'aprobado', 'paid', 'succeeded'))")
    List<Object[]> ventasPagadas(@Param("usuarioId") Integer usuarioId);
}
