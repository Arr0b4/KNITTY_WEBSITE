# KNITTY — Entrega UT2, Login y Registro

## Alcance

Esta versión se ha adaptado a la rúbrica de **Inicio y Registro con Logueo**.
No se ha implementado todavía el ecommerce completo; no se deben probar pagos reales.

## Funcionalidad implementada

- Rutas `/`, `/help`, `/login-choice`, `/login`, `/authorization`, `/register`, `/zona-usuario` y ruta de error `404`.
- Modo oscuro existente (`ThemeToggle`), estilos responsive específicos para login/registro.
- Formularios accesibles con etiquetas, tipo email, campos obligatorios, longitud mínima de 8 caracteres en contraseña, confirmación de contraseña y mensajes de errores.
- API Spring Boot: `POST /api/auth/registro`, `POST /api/auth/login`, `GET /api/auth/yo`, `POST /api/auth/logout`.
- Nuevo usuario persistido en la tabla `usuario` de MariaDB; correo duplicado devuelve `409`; contraseña guardada como hash BCrypt, nunca en texto plano.
- Token aleatorio generado con `SecureRandom` y conservado en `sessionStorage` en el navegador; el backend comprueba su validez para entrar a `/zona-usuario` y lo invalida con logout.
- La zona privada solo muestra los datos del usuario después de una respuesta válida del servidor.

**Importante:** La sesión demostrativa se guarda en memoria del servidor, caduca a las dos horas y se pierde cuando reinicias Spring Boot. Es suficiente para demostrar acceso con sesión en esta actividad local, pero no para producción. Para una entrega desplegada habría que usar una tabla de sesiones, Redis o un sistema de autenticación completo. Nunca uses el usuario `root` de MariaDB sin contraseña en un servidor remoto.

## Instrucciones para tu equipo

1. Haz copia de seguridad de tu proyecto actual.
2. Abre la carpeta `KNITTY_WEBSITE` del ZIP en IntelliJ.
3. Arranca el módulo MySQL/MariaDB de XAMPP; verifica que existe `db_knitty` con la tabla `usuario` y que `email` tiene una restricción UNIQUE.
4. Abre terminal **en `knitty-backend`**:

```bash
bash mvnw clean compile
bash mvnw spring-boot:run
```

5. En una segunda terminal, **en `knitty_frontend`**:

```bash
npm ci
npm run build
npm run dev
```

6. Abre `http://localhost:5173/register`, registra un usuario real de prueba y comprueba que accedes a `/zona-usuario`.
7. Comprueba en phpMyAdmin (tabla `usuario`) que el correo existe y que `contrasena_hash` comienza con `$2` (BCrypt), sin contener la contraseña original.
8. Cierra sesión; entra desde `http://localhost:5173/login` con las mismas credenciales; prueba también una clave incorrecta.
9. En una tercera terminal, **en la carpeta raíz `KNITTY_WEBSITE`**, ejecuta:

```bash
node pruebas_login_knitty.mjs
```

El script crea un usuario de prueba distinto en cada ejecución. Prueba registro, duplicado, contraseña incorrecta, login, acceso autenticado, logout e invalidación de token.

## Relación con la rúbrica

| Criterio | Qué enseñar en la defensa |
| --- | --- |
| Rutas y navegación | Inicio > login-choice > registro/login > zona privada; URL inexistente muestra 404 |
| Interfaz y estilos | Formularios KNITTY y diseño responsive (móvil + escritorio) |
| Modo oscuro | Botón de cambio de tema ya existente |
| Validación | Campos requeridos, email, longitud, contraseñas no coincidentes |
| Servicios y endpoints | Código `AuthController`, `AuthService`, `authKnitty.ts` |
| Registro real | Tabla `usuario` en phpMyAdmin + respuesta HTTP 201 |
| Correo ya registrado | HTTP 409 con aviso visible en React |
| Sesión | `sessionStorage` y consulta de `/api/auth/yo` con `Authorization: Bearer` |
| Acceso privado | `/zona-usuario` consulta servidor y rechaza sesión caducada |
| Usabilidad | Mensajes de carga/error/éxito, foco, botones, etiquetas y enlaces |

## Pendiente: tareas que requieren tus cuentas y acciones personales

- **GitHub:** subir este proyecto a tu repositorio, compartirlo con la profesora, crear commits y ramas visibles. No subas contraseñas ni secretos.
- **Sprints:** actualizar tu Trello/GitHub Projects/Notion con columnas `Esperando`, `En proceso`, `Finalizado`, `En revisión`; compartir el enlace con permisos y realizar una captura.
- **Documento de entrega:** crear PDF de **máximo 3 páginas**, llamado `Tunombre_02.PDF`, con capturas reales de la app en ejecución (inicio, registro, login, errores y zona privada), el enlace al repositorio, el enlace al tablero de sprints y una captura de ese tablero; comentar brevemente usabilidad y accesibilidad.
- **Defensa personal:** ensayar el recorrido completo en presencia de la profesora. La rúbrica indica que la defensa individual es obligatoria.

## Limitaciones que quedan fuera de esta entrega

- Pantallas del ecommerce antiguo (`/cart`, `/order`, `/wishlist`, `/dashboard` y `/product/:id`) aún usan otra API. No están conectadas al login nuevo y deberán adaptarse en una entrega posterior.
- Los tokens locales almacenados en memoria no permiten sesiones persistentes ni despliegue a producción.
- En el entorno de revisión no estaban en caché todas las dependencias Maven/npm; la compilación y el ensayo en tu Windows deben realizarse con los comandos anteriores.
