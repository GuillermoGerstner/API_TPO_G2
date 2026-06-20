# API TPO G2 - Ecommerce

Proyecto Full Stack desarrollado para la materia **Aplicaciones Interactivas**.
La aplicación corresponde a un ecommerce con backend en Spring Boot y frontend en React, integrando autenticación, gestión de productos, carrito de compras y checkout.

## Tecnologías utilizadas

### Backend

* Java 17
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* MySQL
* Maven
* Docker / Docker Compose
* JUnit + Mockito

### Frontend

* JavaScript
* React
* Vite
* React Router DOM
* useEffect
* useContext
* Axios
* CSS

## Funcionalidades principales

* Registro y login de usuarios.
* Autenticación con JWT.
* Gestión de productos.
* Gestión de categorías.
* Listado y búsqueda de productos.
* Filtrado por categoría y ordenamiento.
* Detalle de producto mediante rutas dinámicas.
* Carrito de compras con estado global usando `useContext`.
* Agregado, eliminación, modificación de cantidades y vaciado del carrito.
* Checkout conectado al backend.
* Generación de pedidos e ítems de pedido.
* Descuento de stock al finalizar la compra.
* Perfil del usuario autenticado.
* Manejo de estados de carga y error en el frontend.
* Manejo global de excepciones en backend.
* Validaciones con DTOs.
* Tests unitarios con JUnit y Mockito.

## Estructura general

* `ecommerce/`: backend desarrollado con Spring Boot.
* `frontend/`: frontend desarrollado con React + Vite.

## Ejecución del backend

Desde la carpeta del backend:

```bash
cd ecommerce
mvn spring-boot:run
```

El backend se ejecuta por defecto en:

```bash
http://localhost:8080
```

## Ejecución del frontend

Desde la carpeta del frontend:

```bash
cd frontend
npm install
npm run dev
```

El frontend se ejecuta por defecto en:

```bash
http://localhost:5173
```

## Datos iniciales

Al iniciar el backend se crea automáticamente un usuario administrador y categorías iniciales para facilitar la prueba de la aplicación.

Usuario administrador:

```txt
Email: admin@ecommerce.com
Password: Admin1234
```

Categorías iniciales:

```txt
Electrónica
Ropa
Hogar
Deportes
Libros
```

## Videos explicativos
🔗 [Ver videos](https://drive.google.com/drive/folders/1nw67WkvJYUpSgPgDgr9Dc6mZpjqjY1jE?usp=sharing)
