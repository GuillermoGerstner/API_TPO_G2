import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../api/api"; // Usamos la configuración de axios de tus compañeros
import "../styles/GestionProductos.css";

function GestionProductos() {
  const [misProductos, setMisProductos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    // 1. Recuperamos el token guardado en el localStorage al loguearnos
    const token = localStorage.getItem("token");

    // 2. Hacemos la consulta pasando el encabezado de autorización requerido por Spring Security
    api
      .get("/productos/mis-productos", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        setMisProductos(res.data);
      })
      .catch((err) => {
        console.error(err);
        setError("No se pudieron cargar tus productos de gestión.");
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading)
    return <div className="gestion-status">Cargando tus productos...</div>;
  if (error) return <div className="gestion-status error">{error}</div>;

  return (
    <div className="gestion-container">
      <div className="gestion-header">
        <h1>Mis Productos Publicados</h1>
        <Link to="/formulario-producto" className="btn-agregar">
          + Agregar Producto
        </Link>
      </div>

      {misProductos.length === 0 ? (
        <div className="no-products">
          <p>Aún no has publicado ningún producto con este usuario.</p>
        </div>
      ) : (
        <table className="gestion-tabla">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Precio</th>
              <th>Stock</th>
              <th>Descripción</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {misProductos.map((prod) => (
              <tr key={prod.id}>
                <td>{prod.id}</td>
                <td>
                  <strong>{prod.nombre}</strong>
                </td>
                <td>${prod.precio?.toFixed(2)}</td>
                <td>{prod.stock} u.</td>
                <td>{prod.descripcion || "Sin descripción"}</td>
                <td>
                  <Link
                    to={`/formulario-producto/${prod.id}`}
                    className="btn-accion btn-editar"
                    title="Editar"
                  >
                    ✏️
                  </Link>
                  <button className="btn-accion btn-eliminar" title="Eliminar">
                    🗑️
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default GestionProductos;
