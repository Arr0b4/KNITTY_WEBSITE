// Ejecutar con Node 21+ tras iniciar Spring Boot: node pruebas_login_knitty.mjs
// Crea una cuenta de prueba con correo único cada vez.
const base = 'http://localhost:8080/api/auth';
const email = `entrega-${Date.now()}@knitty.test`;
const password = 'PruebaSegura123!';

async function solicitud(ruta, metodo = 'GET', cuerpo, token) {
  const respuesta = await fetch(`${base}${ruta}`, {
    method: metodo,
    headers: { ...(cuerpo ? { 'Content-Type': 'application/json' } : {}), ...(token ? { Authorization: `Bearer ${token}` } : {}) },
    ...(cuerpo ? { body: JSON.stringify(cuerpo) } : {}),
  });
  const datos = respuesta.status === 204 ? null : await respuesta.json().catch(() => null);
  return { status: respuesta.status, datos };
}

function verificar(condicion, mensaje) {
  if (!condicion) throw new Error(`FALLO: ${mensaje}`);
  console.log(`OK: ${mensaje}`);
}

try {
  const registro = await solicitud('/registro', 'POST', { nombre: 'Alumna KNITTY', email, password });
  verificar(registro.status === 201 && registro.datos?.token, 'Registro exitoso (201 y token)');
  verificar(!JSON.stringify(registro.datos).includes('contrasenaHash'), 'No se devuelve el hash de contraseña');

  const duplicado = await solicitud('/registro', 'POST', { nombre: 'Otra', email, password });
  verificar(duplicado.status === 409, 'Correo duplicado rechazado (409)');

  const mal = await solicitud('/login', 'POST', { email, password: 'NoEsLaClave' });
  verificar(mal.status === 401, 'Contraseña incorrecta rechazada (401)');

  const login = await solicitud('/login', 'POST', { email, password });
  verificar(login.status === 200 && login.datos?.token, 'Login correcto (200 y token)');

  const sinToken = await solicitud('/yo');
  verificar(sinToken.status === 400 || sinToken.status === 401, 'Ruta privada exige cabecera');

  const perfil = await solicitud('/yo', 'GET', null, login.datos.token);
  verificar(perfil.status === 200 && perfil.datos?.email === email, 'El token permite consultar solo el perfil propio');

  const resumenSinToken = await fetch('http://localhost:8080/api/cuenta/resumen');
  verificar(resumenSinToken.status === 400 || resumenSinToken.status === 401, 'Los datos de compras y ventas requieren sesión');

  const resumenRespuesta = await fetch('http://localhost:8080/api/cuenta/resumen', {
    headers: { Authorization: `Bearer ${login.datos.token}` },
  });
  const resumen = await resumenRespuesta.json();
  verificar(resumenRespuesta.status === 200 && resumen.usuario.email === email &&
    Array.isArray(resumen.wishlist) && Array.isArray(resumen.compras) && Array.isArray(resumen.ventas),
    'Resumen privado con wishlist, compras y ventas');

  const logout = await solicitud('/logout', 'POST', null, login.datos.token);
  verificar(logout.status === 204, 'Logout correcto (204)');

  const revocado = await solicitud('/yo', 'GET', null, login.datos.token);
  verificar(revocado.status === 401, 'El token deja de servir tras logout');

  console.log(`\nPRUEBAS SUPERADAS. Cuenta de prueba creada: ${email}`);
} catch (error) {
  console.error('\nERROR:', error.message);
  process.exitCode = 1;
}
