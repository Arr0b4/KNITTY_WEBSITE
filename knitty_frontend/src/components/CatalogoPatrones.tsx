import { useEffect, useMemo, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
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
  const tituloCategoria = categorias.find((item) => item.nombre.toLowerCase() === categoria)?.nombre;

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
      <div className="knitty-catalogo__encabezado">
        <div>
          <p className="knitty-catalogo__etiqueta">KNITTY / PATTERNS</p>
          <h1>{tituloCategoria ?? 'All patterns'}</h1>
        </div>
      </div>
      <form className="knitty-buscar" onSubmit={(e) => {
        e.preventDefault();
        const data = new FormData(e.currentTarget);
        const valor = String(data.get('search') ?? '').trim();
        setParams((actual) => { const nuevo = new URLSearchParams(actual); if (valor) nuevo.set('search', valor); else nuevo.delete('search'); return nuevo; });
      }}>
        <input name="search" type="search" key={busqueda} defaultValue={busqueda} placeholder="Search patterns..." aria-label="Buscar patrones" />
        <button type="submit">SEARCH</button>
      </form>
      {cargando && <p role="status">Cargando el catálogo…</p>}
      {error && <p role="alert">No se pudo cargar el catálogo: {error}. Comprueba que Spring Boot esté iniciado.</p>}
      {!cargando && !error && visibles.length === 0 && <p>No hay patrones para esta selección.</p>}
      {!cargando && !error && (
        <>
        <p className="knitty-catalogo__contador">{visibles.length} {visibles.length === 1 ? 'pattern' : 'patterns'}</p>
        <div className="knitty-grid">
          {visibles.map((p) => (
            <article className="knitty-patron" key={p.id}>
              <div className="knitty-placeholder" aria-hidden="true" />
              <div className="knitty-patron-body">
                <h2>{p.nombre}</h2>
                <p>{p.descripcion}</p>
                <p className="knitty-patron__nivel">{p.nivelDificultad ?? 'Sin nivel especificado'}</p>
                <strong>{Number(p.precio).toLocaleString('es-ES', { style: 'currency', currency: 'EUR' })}</strong>
              </div>
            </article>
          ))}
        </div>
        </>
      )}
    </section>
  );
}
