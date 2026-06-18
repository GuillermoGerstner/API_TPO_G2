import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../api/api";
import "../styles/FormularioProducto.css";

function FormularioProducto() {
  const { id } = useParams(); // Detecta si hay un ID en la URL
  const navigate = useNavigate();
  const isEditMode = !!id; // Flag booleano para saber el modo

  const [formData, setFormData] = useState({
    nombre: "",
    precio: "",
    stock: "",
    idCategoria: "1", // Categoría por defecto (por ej. Electrónica)
    descripcion: "",
  });

  const [loading, setLoading] = useState(isEditMode);
  const [error, setError] = useState("");

  // Si estamos en modo edición, traemos los datos actuales del producto
  useEffect(() => {
    if (isEditMode) {
      const token = localStorage.getItem("token");
      api
        .get(`/productos/${id}`, {
          headers: { Authorization: `Bearer ${token}` },
        })
        .then((res) => {
          const prod = res.data;
          setFormData({
            nombre: prod.nombre,
            precio: prod.precio,
            stock: prod.stock || 0,
            idCategoria: prod.categoria ? prod.categoria.id.toString() : "1",
            descripcion: prod.descripcion || "",
          });
        })
        .catch(() =>
          setError("No se pudieron recuperar los datos del producto."),
        )
        .finally(() => setLoading(false));
    }
  }, [id, isEditMode]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");

    // Estructuramos el DTO exigido por Java
    const payload = {
      nombre: formData.nombre,
      descripcion: formData.descripcion,
      precio: parseFloat(formData.precio),
      stock: parseInt(formData.stock),
      idCategoria: parseInt(formData.idCategoria),
      idUsuario: 0, // Lo maneja el backend automáticamente con el Token ahora
    };

    // Definimos dinámicamente el método y la ruta
    const request = isEditMode
      ? api.put(`/productos/${id}`, payload, {
          headers: { Authorization: `Bearer ${token}` },
        })
      : api.post("/productos", payload, {
          headers: { Authorization: `Bearer ${token}` },
        });

    request
      .then(() => {
        alert(
          isEditMode
            ? "¡Producto actualizado con éxito!"
            : "¡Producto publicado con éxito!",
        );
        navigate("/gestion-productos"); // Volvemos para atrás
      })
      .catch((err) => {
        console.error(err);
        alert("Ocurrió un error al procesar el producto. Verifica los campos.");
      });
  };

  if (loading)
    return <div className="form-status">Cargando datos del producto...</div>;
  if (error) return <div className="form-status error">{error}</div>;

  return (
    <div className="form-page-container">
      <div className="form-card">
        <h2 className="form-card__title">
          {isEditMode ? "MODIFICAR PRODUCTO" : "AGREGAR NUEVO PRODUCTO"}
        </h2>

        <form onSubmit={handleSubmit} className="form-card__body">
          <div className="form-group">
            <label>Nombre del Producto</label>
            <input
              type="text"
              name="nombre"
              value={formData.nombre}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-grid-inputs">
            <div className="form-group">
              <label>Precio ($)</label>
              <input
                type="number"
                step="0.01"
                name="precio"
                value={formData.precio}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label>Stock Disponible</label>
              <input
                type="number"
                name="stock"
                value={formData.stock}
                onChange={handleChange}
                required
              />
            </div>
          </div>

          <div className="form-group">
            <label>Categoría</label>
            <select
              name="idCategoria"
              value={formData.idCategoria}
              onChange={handleChange}
            >
              <option value="1">Electrónica</option>
              <option value="2">Ropa</option>
              <option value="3">Hogar</option>
              <option value="4">Deportes</option>
              <option value="5">Libros</option>
            </select>
          </div>

          <div className="form-group">
            <label>Descripción</label>
            <textarea
              name="descripcion"
              rows="4"
              value={formData.descripcion}
              onChange={handleChange}
            />
          </div>

          <div className="form-actions-buttons">
            <button type="submit" className="btn-form-submit">
              {isEditMode ? "GUARDAR CAMBIOS" : "PUBLICAR PRODUCTO"}
            </button>
            <button
              type="button"
              className="btn-form-cancel"
              onClick={() => navigate("/gestion-productos")}
            >
              CANCELAR
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default FormularioProducto;
