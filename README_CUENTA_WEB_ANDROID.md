# KNITTY: web, Android y backend compartido

## Diseño de la arquitectura

```
React (navegador) ── HTTP JSON ──┐
                                ├── Spring Boot REST (/api) ── MariaDB db_knitty
Android (app) ────── HTTP JSON ──┘
```

La aplicación Android **no necesita otro backend**. Puede consumir exactamente los mismos endpoints y datos, siempre que la IP/URL de Spring Boot sea accesible desde el dispositivo.

## Endpoints disponibles

| Método | Endpoint | Datos enviados | Autenticación |
| --- | --- | --- | --- |
| POST | `/api/auth/registro` | `{ "nombre":"Ana", "email":"ana@example.test", "password":"EjemploSegura123" }` | No |
| POST | `/api/auth/login` | `{ "email":"ana@example.test", "password":"EjemploSegura123" }` | No |
| GET | `/api/auth/yo` | — | `Authorization: Bearer TOKEN` |
| GET | `/api/cuenta/resumen` | — | `Authorization: Bearer TOKEN` |
| POST | `/api/auth/logout` | — | `Authorization: Bearer TOKEN` |
| GET | `/api/patrones` | — | No |
| GET | `/api/categorias` | — | No |

Tras registrarse o iniciar sesión se recibe:

```json
{
  "token": "TOKEN_ALEATORIO_DEL_SERVIDOR",
  "usuario": { "id": 1, "nombre": "Ana", "email": "ana@example.test" }
}
```

El resumen devuelve exclusivamente los datos del usuario asociado al token:

```json
{
  "usuario": { "id": 1, "nombre": "Ana", "email": "ana@example.test" },
  "wishlist": [],
  "compras": [],
  "ventas": [],
  "cantidadComprada": 0,
  "cantidadVendida": 0,
  "ingresosBrutos": 0
}
```

Las listas y valores numéricos son ejemplos. Las consultas reales proceden de `wishlist`, `detalle_wishlist`, `pedido`, `detalle_pedido`, `pago` y `patron`. Solo se contabilizan pedidos con pagos cuyo `estado_pago` sea `pagado`, `completado`, `aprobado`, `paid` o `succeeded`. Si tu BD utiliza otros estados para indicar confirmación, debes ajustar las consultas de `CuentaConsultasRepository` con tus estados reales. Los ingresos son **brutos** por líneas vendidas, sin descontar comisiones ni reembolsos. No equivalen a saldo bancario.

## Android

- Para probar con el emulador Android, **`localhost` dentro del emulador no es el PC**. El emulador habitual de Android Studio alcanza el host con `http://10.0.2.2:8080/api` cuando Spring Boot escucha en una interfaz accesible.
- Para un teléfono real en la misma red, utiliza la IP LAN del ordenador, configura firewall y servidor correctamente y evita exponer la BD; jamás conectes Android directamente a MariaDB.
- El tráfico HTTP sin TLS solo es adecuado en entorno de pruebas local. En Android puede requerir configuración de tráfico en claro para el modo de desarrollo. Para publicar usa HTTPS y tokens guardados de manera segura.
- Puedes usar Retrofit u otro cliente HTTP con modelos DTO equivalentes al JSON anterior.

## Qué se ha respetado del frontend KNITTY

- Formulario `AccesoKnitty` usa los estilos existentes `Authorization.css`, `Register.css` y las imágenes `Login.webp`, `Register.webp`.
- `ZonaKnitty` reutiliza `Dashboard.css`, `Dashboard.webp`, los botones, la tipografía y las variables del proyecto.
- Las rutas `/zona-usuario` y `/dashboard` muestran el resumen nuevo. La pantalla antigua se conserva en `/dashboard-legacy`.
- El resumen privado no confía en un ID de usuario enviado por el navegador: lo determina el backend leyendo la sesión.

## Comprobaciones

1. Inicia XAMPP y Spring Boot: `bash mvnw spring-boot:run` desde `knitty-backend`.
2. Inicia React: `npm ci` y `npm run dev` desde `knitty_frontend`.
3. Registra un usuario desde `/register`, comprueba que se guarda su hash en MariaDB e inicia sesión.
4. Abre `/dashboard`: verás el resumen; los valores sin ventas ni compras son 0, como corresponde.
5. Ejecuta desde la raíz `node pruebas_login_knitty.mjs` para probar también que los datos privados no se devuelven sin token.

## Todavía pendiente para una tienda completa

No se han creado endpoints de inserción/modificación de wishlist, pedidos, pagos y descargas. Para demostrar cifras distintas de cero necesitas registros coherentes en esas tablas y un proceso de confirmación de pagos. No se deben generar compras ficticias como si fueran cobros reales. La sesión ahora vive en memoria y caduca a las dos horas; antes de desplegar ambas aplicaciones necesitarás autenticación duradera y permisos por usuario.
