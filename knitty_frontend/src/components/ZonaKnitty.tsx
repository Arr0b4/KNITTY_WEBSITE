import { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { cerrarSesion, consultarResumen, type ResumenCuenta, type DisenoCuenta } from '../services/authKnitty';
import dashboardImage from '../assets/images/Dashboard.webp';
import '../../styles/Dashboard.css';

function ListaDisenos({ titulo, disenos }: { titulo: string; disenos: DisenoCuenta[] }) {
  return <div className="knitty-cuenta__coleccion">
    <h2 className="dashboard__section-title">{titulo}</h2>
    {disenos.length ? <ul>{disenos.map((d, index) => <li key={`${d.id}-${index}`}><span>{d.nombre}</span><span>{Number(d.precio).toLocaleString('es-ES', { style: 'currency', currency: 'EUR' })}</span></li>)}</ul>
      : <p className="knitty-cuenta__vacio">Todavía no tienes diseños en este apartado.</p>}
  </div>;
}

export default function ZonaKnitty() {
  const navigate = useNavigate();
  const location = useLocation();
  const [resumen, setResumen] = useState<ResumenCuenta | null>(null);
  const [mensaje, setMensaje] = useState('Comprobando tu sesión…');

  useEffect(() => {
    let activo = true;
    consultarResumen().then(datos => {
      if (activo) { setResumen(datos); setMensaje(''); }
    }).catch((e: unknown) => {
      if (activo) { setMensaje(e instanceof Error ? e.message : 'No se pudo cargar tu perfil.'); }
    });
    return () => { activo = false; };
  }, []);

  return (
    <section className="dashboard knitty-cuenta">
      <div className="knitty-cuenta__layout">
        <div className="knitty-cuenta__contenido">
          <p className="dashboard__section-title">KNITTY / MY ACCOUNT</p>
          <h1 className="dashboard__page-title">{resumen ? `Hello, ${resumen.usuario.nombre}` : 'My account'}</h1>
          {!resumen ? <><p role="status" className="dashboard__status-err">{mensaje}</p><Link to="/login">Log in</Link></> : <>
            <p role="status" className="knitty-cuenta__mensaje">{(location.state as { mensaje?: string } | null)?.mensaje ?? resumen.usuario.email}</p>
            <div className="knitty-cuenta__estadisticas">
              <div><span>Purchased patterns</span><strong>{resumen.cantidadComprada}</strong></div>
              <div><span>Sold patterns</span><strong>{resumen.cantidadVendida}</strong></div>
              <div><span>Gross income</span><strong>{Number(resumen.ingresosBrutos).toLocaleString('es-ES', { style: 'currency', currency: 'EUR' })}</strong></div>
              <div><span>Wishlist</span><strong>{resumen.wishlist.length}</strong></div>
            </div>
            <div className="knitty-cuenta__listas">
              <ListaDisenos titulo="Wishlist" disenos={resumen.wishlist} />
              <ListaDisenos titulo="Purchased designs" disenos={resumen.compras} />
              <ListaDisenos titulo="Sold designs" disenos={resumen.ventas} />
            </div>
            <p className="knitty-cuenta__nota">Los ingresos muestran el importe bruto de los patrones con pagos confirmados, antes de comisiones y devoluciones.</p>
            <div className="dashboard__actions">
              <Link className="dashboard__button" to="/catalog">Explore patterns</Link>
              <button className="dashboard__button dashboard__button--primary" type="button" onClick={() => { void cerrarSesion().finally(() => navigate('/login', { replace: true })); }}>Log out</button>
            </div>
          </>}
        </div>
        <div className="knitty-cuenta__imagen"><img src={dashboardImage} alt="" aria-hidden="true" /></div>
      </div>
    </section>
  );
}
