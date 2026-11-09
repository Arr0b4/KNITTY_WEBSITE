import { useEffect, useMemo, useState } from 'react';
import { Link, useSearchParams } from 'react-router-dom';
import {
  obtenerCategorias,
  obtenerPatrones,
  type CategoriaKnitty,
  type PatronKnitty,
} from '../services/catalogoService';
import '../../styles/CatalogoPatrones.css';

export default function CatalogoPatrones() {
  const [patrones, setPatrones] = useState<PatronKnitty[]>([]);
  const [categorias, setCategorias] = useState<CategoriaKnitty[]>([]);
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState('');
  const [params, setParams] = useSearchParams();
  const categoria = params.get('category')?.toLowerCase() ?? '';
  const busqueda = params.get('search')?.trim().toLowerCase() ?? '';

  useEffect(() => {
    const controller = new AbortController();
    Promise.all([obtenerPatrones(controller.signal), obtenerCategorias(controller.signal)])
      .then(([productos, grupos]) => {
        setPatrones(productos);
        setCategorias(grupos);
      })
      .catch((e: unknown) => {
        if (!controller.signal.aborted) setError(e instanceof Error ? e.message : 'Error de conexión');
      })
      .finally(() => { if (!controller.signal.aborted) setCargando(false); });
    return () => controller.abort();
  }, []);

  const visibles = useMemo(() => {
    const idCategoria = categorias.find((item) => item.nombre.toLowerCase() === categoria)?.id;
    return patrones.filter((p) => p.disponible !== false)
      .filter((p) => !categoria || (idCategoria !== undefined && p.idCategoria === idCategoria))
      .filter((p) => !busqueda || `${p.nombre} ${p.descripcion ?? ''}`.toLowerCase().includes(busqueda));
  }, [patrones, categorias, categoria, busqueda]);

  return (
    <section className="knitty-catalogo" aria-label="Catálogo de patrones de crochet">
      <h1>Patrones de crochet</h1>
      <p>Explora nuestros patrones digitales para tejer tus propias creaciones.</p>
      <nav className="knitty-categorias" aria-label="Filtrar por categoría">
        <Link to="/catalog" aria-current={!categoria ? 'page' : undefined}>Todos</Link>
        {categorias.map((item) => (
          <Link key={item.id} to={`/catalog?category=${encodeURIComponent(item.nombre.toLowerCase())}`}
            aria-current={categoria === item.nombre.toLowerCase() ? 'page' : undefined}>
            {item.nombre}
          </Link>
        ))}
      </nav>
      <form className="knitty-buscar" onSubmit={(e) => {
        e.preventDefault();
        const data = new FormData(e.currentTarget);
        const valor = String(data.get('search') ?? '').trim();
        setParams((actual) => { const nuevo = new URLSearchParams(actual); if (valor) nuevo.set('search', valor); else nuevo.delete('search'); return nuevo; });
      }}>
        <input name="search" type="search" key={busqueda} defaultValue={busqueda} placeholder="Buscar patrones..." aria-label="Buscar patrones" />
        <button type="submit">Buscar</button>
      </form>
      {cargando && <p role="status">Cargando el catálogo…</p>}
      {error && <p role="alert">No se pudo cargar el catálogo: {error}. Comprueba que Spring Boot esté iniciado.</p>}
      {!cargando && !error && visibles.length === 0 && <p>No hay patrones para esta selección.</p>}
      {!cargando && !error && (
        <div className="knitty-grid">
          {visibles.map((p) => (
            <article className="knitty-patron" key={p.id}>
              <div className="knitty-placeholder" aria-hidden="true">🧶</div>
              <div className="knitty-patron-body">
                <h2>{p.nombre}</h2>
                <p>{p.descripcion}</p>
                <p>Nivel: {p.nivelDificultad ?? 'sin especificar'}</p>
                <strong>{Number(p.precio).toLocaleString('es-ES', { style: 'currency', currency: 'EUR' })}</strong>
              </div>
            </article>
          ))}
        </div>
      )}
      <p className="knitty-aviso">El catálogo funciona en modo consulta. La compra y la descarga requieren implementar autenticación y pagos en el backend.</p>
    </section>
  );
}
