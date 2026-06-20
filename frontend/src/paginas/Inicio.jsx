import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";

import api from "../api/api";
import ProductoCard from "../componentes/ProductoCard";
import Loader from "../componentes/Loader";
import ErrorMessage from "../componentes/ErrorMessage";
import "../styles/Inicio.css";

function Inicio() {
  const [productos, setProductos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [categorias, setCategorias] = useState([]);

  const [categoriaSeleccionada, setCategoriaSeleccionada] = useState("Todas");
  const [orden, setOrden] = useState("alfabetico");

  const [searchParams] = useSearchParams();
  const busqueda = searchParams.get("q") || ""; 

  useEffect(() => {
    const fetchCatalogData = async () => {
      try {
        setLoading(true);
        const [productsResponse, categoriesResponse] = await Promise.all([
          api.get("/productos"),
          api.get("/categorias")
        ]);

        setProductos(productsResponse.data);
        setCategorias(categoriesResponse.data);
        setLoading(false);
      } catch (err) {
        console.error("Error en la carga de datos:", err);
        setError("Error al traer los datos del servidor");
        setLoading(false);
      }
    };

    fetchCatalogData();
  }, []);


  // Primero filtramos por categoría y búsqueda, luego ordenamos el resultado
  const textoBusqueda = busqueda.trim().toLowerCase();

  const productosFiltrados = productos.filter((producto) => {
    const coincideCategoria =
      categoriaSeleccionada === "Todas" ||
      producto.categoria?.nombre?.toLowerCase() ===
        categoriaSeleccionada.toLowerCase();

    const coincideBusqueda =
      textoBusqueda === "" ||
      producto.nombre?.toLowerCase().includes(textoBusqueda) ||
      producto.descripcion?.toLowerCase().includes(textoBusqueda) ||
      producto.categoria?.nombre?.toLowerCase().includes(textoBusqueda);

    return coincideCategoria && coincideBusqueda;
  });

  // Ordenamos la lista ya filtrada
  const productosOrdenados = [...productosFiltrados].sort((a, b) => {
    if (orden === 'alfabetico') {
      return (a.nombre || "").localeCompare(b.nombre || "");
    }
    if (orden === 'precioMenor') {
      return (a.precio || 0) - (b.precio || 0);
    }
    if (orden === 'precioMayor') {
      return (b.precio || 0) - (a.precio || 0);
    }
    return 0;
  });

  if (loading) return <Loader />;
  if (error) return <ErrorMessage message={error} />;

  return (
    <div className="home-layout">
      <aside className="sidebar">
        <h2 className="sidebar__title">CATEGORÍAS</h2>
        <ul className="sidebar__list">
          <li
            className={`sidebar__item ${categoriaSeleccionada === 'Todas' ? "sidebar__item--active" : ""}`}
            onClick={() => setCategoriaSeleccionada('Todas')}
          >
            <span className="sidebar__icon">★</span> Todas
          </li>

          {categorias.map((cat) => (
            <li
              key={cat.id}
              className={`sidebar__item ${categoriaSeleccionada === cat.nombre ? "sidebar__item--active" : ""}`}
              onClick={() => setCategoriaSeleccionada(cat.nombre)}
            >
              <span className="sidebar__icon">★</span> {cat.nombre}
            </li>
          ))}
        </ul>
      </aside>

      {/* Contenido Principal */}
      <main className="main-content">
        <div className="main-content__top-bar">
          <div className="main-content__results-info">
            {busqueda ? `Resultados para "${busqueda}"` : "Todos los productos"}
          </div>

          <div className="main-content__filter-bar">
            <label htmlFor="sort">Ordenar por:</label>
            <select
              id="sort"
              className="main-content__select"
              value={orden}
              onChange={(e) => setOrden(e.target.value)}
            >
              <option value="alfabetico">A-Z (Alfabético)</option>
              <option value="precioMenor">Precio: Menor a Mayor</option>
              <option value="precioMayor">Precio: Mayor a Menor</option>
            </select>
          </div>
        </div>

        {productosOrdenados.length === 0 ? (
          <div className="products-empty">
            No se encontraron productos para la búsqueda realizada.
          </div>
        ) : (
          <div className="products-grid">
            {productosOrdenados.map((producto) => (
              <ProductoCard key={producto.id} producto={producto} />
            ))}
          </div>
        )}
      </main>
    </div>
  );
}

export default Inicio;
