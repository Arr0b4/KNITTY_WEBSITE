# KNITTY — revisión del proyecto (9 de noviembre de 2026)

Esta copia contiene ajustes para conectar **el catálogo público de patrones** de React con los endpoints existentes de Spring Boot y MariaDB. No convierte automáticamente toda la tienda original en un ecommerce funcional.

## Cambios realizados

1. Recuperados `knitty-backend/src/main/java/com/knitty/knitty_backend/services/PatronService.java` y `CategoriaService.java`: en el ZIP faltaban estas dos clases Java y había tres archivos `.js` equivocados dentro del backend.
2. Añadido `CorsConfig.java` para permitir solicitudes GET desde Vite en `localhost:5173` y `127.0.0.1:5173`.
3. Añadido `idCategoria` al JSON de `/api/patrones`, para poder filtrar por categoría real sin depender de los IDs numéricos concretos.
4. Carga anticipada de la categoría de cada patrón mediante `@EntityGraph`, evitando acceso a una relación JPA cerrada al desactivar `open-in-view`.
5. Añadido `src/services/catalogoService.ts` en el **frontend TypeScript** (no en Java).
6. Rehecho `CatalogoPatrones.tsx` y su CSS para consultar patrones/categorías de Spring Boot, buscar por texto y filtrar por Home/Plushies/Complements/Clothes.
7. `/catalog` presenta el nuevo catálogo. La antigua página de ropa sigue disponible temporalmente en `/catalog-legacy` para no borrar trabajo.
8. Corregidos los enlaces de categorías del menú lateral.
9. Añadido `.env.example`, con la URL de la API local como ejemplo.

## Antes de arrancar

- Instala XAMPP, inicia **MySQL/MariaDB** y verifica en phpMyAdmin la base `db_knitty`, sus tablas, las 4 categorías y los 4 patrones de prueba.
- Usa **dos terminales** en IntelliJ.

Terminal A, dentro de `knitty-backend` (Git Bash):

```bash
bash mvnw clean compile
bash mvnw spring-boot:run
```

Terminal B, dentro de `knitty_frontend`:

```bash
npm ci
npm run build
npm run dev
```

Abre `http://localhost:5173/catalog` en el navegador. Para cada categoría abre:

- `http://localhost:5173/catalog?category=home`
- `http://localhost:5173/catalog?category=plushies`
- `http://localhost:5173/catalog?category=complements`
- `http://localhost:5173/catalog?category=clothes`

Prueba antes la API directamente:

```bash
curl -i http://localhost:8080/api/categorias
curl -i http://localhost:8080/api/patrones
```

**Copia únicamente los comandos**, no el resultado HTTP que te devuelve la consola.

Si el navegador muestra un error de conexión, revisa que Spring Boot siga en ejecución y que `http://localhost:8080/api/patrones` muestre la lista JSON.

## Funciones aún NO integradas (prioridad)

El frontend original se diseñó para otra API de **ropa con tallas, colecciones, stock, JWT y Stripe**. La base KNITTY actual vende **PDF de crochet**, sin tallas ni envíos. Las pantallas antiguas (`/product/:id`, `/cart`, `/wishlist`, `/login`, `/register`, `/order`, `/dashboard`, etc.) aún llaman a endpoints como `/api/products/`, `/api/accounts/`, `/api/cart/` y `/api/token/refresh/` que tu backend Java **no proporciona**. Pueden mostrar errores 404 o 401. No introducir pagos reales ni probar operaciones de compra con datos personales por ahora.

Próximos pasos recomendados, en este orden:

1. Definir los contratos API y entidades/DTO para usuario, carrito, wishlist, pedido y reseñas; adaptar las pantallas a **patrones digitales**.
2. Implementar registro/login en backend con hash de contraseña seguro, sesiones/tokens, roles y autorización. Nunca enviar `contrasena_hash` al navegador.
3. Implementar carrito y wishlist por usuario autenticado y comprobar cantidades/precios en el servidor.
4. Implementar flujo de pedidos y pago con proveedor y verificación del lado servidor; habilitar la descarga autorizada de PDF **solo tras confirmación de pago**.
5. Crear pruebas automatizadas para servicios/repositorios y endpoints; no basarse únicamente en que `clean compile` termina bien.
6. Sustituir rutas de imágenes y PDFs de ejemplo por archivos reales servidos con permisos adecuados.
7. Usar una cuenta MariaDB específica con contraseña en lugar de `root` sin contraseña; mantener las credenciales fuera de Git y evitar publicar MariaDB o phpMyAdmin directamente en Internet.

## Límites de la validación en esta revisión

La revisión está basada en el ZIP adjunto. No se conectó a tu MariaDB local de Windows. En el entorno de revisión no estaban disponibles en caché las dependencias completas de Maven ni npm y no fue posible ejecutar `mvn compile` o `npm run build` aquí. **Debes ejecutar los comandos anteriores en tu equipo** y comunicar cualquier error resultante para corregirlo con precisión.
