import { useEffect, useState } from "react";
import api from "../api/api";
import ProductoCard from "../componentes/ProductoCard";
import Loader from "../componentes/Loader";
import ErrorMessage from "../componentes/ErrorMessage";
import "../styles/Inicio.css";

function Inicio() {
  const [productos, setProductos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  // 1. Estado para manejar la categoría activa (empieza con "Electrónica")
  const [categoriaSeleccionada, setCategoriaSeleccionada] =
    useState("Electrónica");

  // Estado para el ordenamiento
  const [orden, setOrden] = useState("A-Z (Alfabético)");

  const categorias = ["Electrónica", "Ropa", "Hogar", "Deportes", "Libros"];

  useEffect(() => {
    api
      .get("/productos")
      .then((res) => setProductos(res.data))
      .catch(() => setError("Error al cargar los productos"))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <Loader />;
  if (error) return <ErrorMessage message={error} />;

  return (
    <div className="home-layout">
      {/* Sidebar Izquierdo: Categorías */}
      <aside className="sidebar">
        <h2 className="sidebar__title">CATEGORÍAS</h2>
        <ul className="sidebar__list">
          {categorias.map((cat, index) => (
            <li
              key={index}
              // 2. Compara dinámicamente si es la categoría seleccionada para ponerle la clase activa
              className={`sidebar__item ${cat === categoriaSeleccionada ? "sidebar__item--active" : ""}`}
              // 3. Al hacer click, actualiza el estado con la categoría clickeada
              onClick={() => setCategoriaSeleccionada(cat)}
            >
              <span className="sidebar__icon">★</span> {cat}
            </li>
          ))}
        </ul>
      </aside>

      {/* Contenido Principal */}
      <main className="main-content">
        <div className="main-content__filter-bar">
          <label htmlFor="sort">Ordenar por:</label>
          <select
            id="sort"
            className="main-content__select"
            value={orden}
            onChange={(e) => setOrden(e.target.value)}
          >
            <option value="A-Z (Alfabético)">A-Z (Alfabético)</option>
            <option value="Precio: Menor a Mayor">Precio: Menor a Mayor</option>
            <option value="Precio: Mayor a Menor">Precio: Mayor a Menor</option>
          </select>
        </div>

        {/* Grilla de Productos */}
        <div className="products-grid">
          {productos.map((producto) => (
            <ProductoCard key={producto.id} producto={producto} />
          ))}
        </div>
      </main>
    </div>
  );
}

export default Inicio;
