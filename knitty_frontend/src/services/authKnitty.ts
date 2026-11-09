// Sesión demostrativa para la actividad de Login/Registro.
// No reutilizar tokens de la antigua API de ropa.
const BASE = String(import.meta.env.VITE_API_URL || 'http://localhost:8080/api').replace(/\/+$/, '');
const STORAGE = 'knitty_sesion_entrega';

export type PerfilKnitty = { id: number; nombre: string; email: string };
export type SesionKnitty = { token: string; usuario: PerfilKnitty };
export type DisenoCuenta = { id: number; nombre: string; precio: number };
export type ResumenCuenta = {
  usuario: PerfilKnitty;
  wishlist: DisenoCuenta[];
  compras: DisenoCuenta[];
  ventas: DisenoCuenta[];
  cantidadComprada: number;
  cantidadVendida: number;
  ingresosBrutos: number;
};

export function obtenerSesion(): SesionKnitty | null {
  try {
    const valor = sessionStorage.getItem(STORAGE);
    if (!valor) return null;
    const sesion: unknown = JSON.parse(valor);
    if (typeof sesion !== 'object' || !sesion || !('token' in sesion) || typeof sesion.token !== 'string') return null;
    return sesion as SesionKnitty;
  } catch { return null; }
}

async function peticion<T>(ruta: string, options: RequestInit = {}): Promise<T> {
  let respuesta: Response;
  try {
    respuesta = await fetch(`${BASE}/auth${ruta}`, {
      ...options,
      headers: { 'Content-Type': 'application/json', ...options.headers },
    });
  } catch { throw new Error('No se puede conectar con el servidor. Comprueba Spring Boot.'); }
  if (!respuesta.ok) {
    if (respuesta.status === 409) throw new Error('Este correo electrónico ya está registrado.');
    if (respuesta.status === 401) throw new Error('Correo o contraseña incorrectos, o sesión caducada.');
    if (respuesta.status === 400) throw new Error('Revisa los campos: correo válido y contraseña de al menos 8 caracteres.');
    throw new Error(`Error del servidor (${respuesta.status}).`);
  }
  return respuesta.status === 204 ? undefined as T : await respuesta.json() as T;
}

export async function registrar(nombre: string, email: string, password: string): Promise<SesionKnitty> {
  const sesion = await peticion<SesionKnitty>('/registro', { method: 'POST', body: JSON.stringify({ nombre, email, password }) });
  sessionStorage.setItem(STORAGE, JSON.stringify(sesion));
  return sesion;
}

export async function acceder(email: string, password: string): Promise<SesionKnitty> {
  const sesion = await peticion<SesionKnitty>('/login', { method: 'POST', body: JSON.stringify({ email, password }) });
  sessionStorage.setItem(STORAGE, JSON.stringify(sesion));
  return sesion;
}

export async function consultarPerfil(): Promise<PerfilKnitty> {
  const sesion = obtenerSesion();
  if (!sesion) throw new Error('Tienes que iniciar sesión para acceder.');
  return peticion<PerfilKnitty>('/yo', { headers: { Authorization: `Bearer ${sesion.token}` } });
}

export async function consultarResumen(): Promise<ResumenCuenta> {
  const sesion = obtenerSesion();
  if (!sesion) throw new Error('Debes iniciar sesión para ver tus datos.');
  let respuesta: Response;
  try {
    const base = String(import.meta.env.VITE_API_URL || 'http://localhost:8080/api').replace(/\/+$/, '');
    respuesta = await fetch(`${base}/cuenta/resumen`, { headers: { Authorization: `Bearer ${sesion.token}` } });
  } catch { throw new Error('No se puede conectar con el servidor.'); }
  if (respuesta.status === 401) throw new Error('La sesión ha caducado. Inicia sesión de nuevo.');
  if (!respuesta.ok) throw new Error(`No se pudo cargar la cuenta (${respuesta.status}).`);
  return await respuesta.json() as ResumenCuenta;
}

export async function cerrarSesion(): Promise<void> {
  const sesion = obtenerSesion();
  try {
    if (sesion) await peticion<void>('/logout', { method: 'POST', headers: { Authorization: `Bearer ${sesion.token}` } });
  } finally { sessionStorage.removeItem(STORAGE); }
}
