// API pública del backend Spring Boot; las funciones privadas de compra y sesión
// se implementarán cuando existan endpoints seguros en Java.
export type CategoriaKnitty = { id: number; nombre: string; descripcion: string | null };
export type PatronKnitty = {
  id: number;
  nombre: string;
  descripcion: string | null;
  precio: number;
  nivelDificultad: string | null;
  imagenUrl: string | null;
  disponible: boolean;
  idCategoria: number | null;
};

const rawUrl = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';
const apiUrl = String(rawUrl).replace(/\/+$/, '');

async function consultar<T>(ruta: string, signal?: AbortSignal): Promise<T> {
  const respuesta = await fetch(`${apiUrl}${ruta}`, {
    signal,
    headers: { Accept: 'application/json' },
  });
  if (!respuesta.ok) throw new Error(`La API respondió con el código ${respuesta.status}`);
  return (await respuesta.json()) as T;
}

export const obtenerPatrones = (signal?: AbortSignal) => consultar<PatronKnitty[]>('/patrones', signal);
export const obtenerCategorias = (signal?: AbortSignal) => consultar<CategoriaKnitty[]>('/categorias', signal);
