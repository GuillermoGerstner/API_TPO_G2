import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import api from "../api/api";
import { CarritoContext } from "../contexto/CarritoProvider";
import cameraImg from "../assets/camera.png";

import "../styles/DetalleProducto.css";

function DetalleProducto() {
  const { id } = useParams();
  const navigate = useNavigate();

  const { addToCarrito } = useContext(CarritoContext);

  const [producto, setProducto] = useState(null);
  const [cantidad, setCantidad] = useState(1);
  const [imagenSeleccionada, setImagenSeleccionada] = useState(cameraImg);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    setLoading(true);
    setError("");

    api
      .get(`/productos/${id}`)
      .then((res) => {
        setProducto(res.data);

        const imagenPrincipal =
          res.data.imagenes?.[0] || res.data.imagen || cameraImg;

        setImagenSeleccionada(imagenPrincipal);
      })
      .catch((err) => {
        console.error("Error al cargar producto:", err);
        setError("No se pudo cargar el detalle del producto.");
      })
      .finally(() => {
        setLoading(false);
      });
  }, [id]);

  if (loading) {
    return <h2 className="cargando">Cargando...</h2>;
  }

  if (error) {
    return <h2 className="cargando">{error}</h2>;
  }

  if (!producto) {
    return <h2 className="cargando">Producto no encontrado.</h2>;
  }

  const stockDisponible = producto.stock ?? 0;

  const listaImagenes =
    producto.imagenes?.length > 0
      ? producto.imagenes
      : Array(5).fill(producto.imagen || cameraImg);

  const handleAgregarAlCarrito = () => {
    addToCarrito({ ...producto, cantidadPedida: cantidad });
  };

  const handleComprarAhora = () => {
    handleAgregarAlCarrito();
    navigate("/carrito");
  };

  const handleCantidadChange = (e) => {
    const nuevaCantidad = parseInt(e.target.value) || 1;
    const cantidadValidada = Math.min(
      Math.max(1, nuevaCantidad),
      stockDisponible || 1,
    );

    setCantidad(cantidadValidada);
  };

  return (
    <div className="detalle-container">
      <div className="galeria-seccion">
        <div className="miniaturas-lista">
          {listaImagenes.map((img, index) => (
            <button
              key={index}
              className={`miniatura-btn ${
                imagenSeleccionada === img ? "activa" : ""
              }`}
              onClick={() => setImagenSeleccionada(img)}
            >
              <img src={img} alt={`Miniatura ${index + 1}`} />
            </button>
          ))}
        </div>

        <div className="imagen-principal-contenedor">
          <img
            src={imagenSeleccionada}
            alt={producto.nombre}
            className="imagen-principal"
          />
        </div>
      </div>

      <div className="info-seccion">
        <h1 className="producto-titulo">{producto.nombre}</h1>

        <div className="categoria-badge">
          Categoría:{" "}
          <span className="categoria-nombre">
            {producto.categoria?.nombre || "Sin categoría"}
          </span>
        </div>

        <h2 className="producto-precio">
          $
          {producto.precio?.toLocaleString("es-AR", {
            minimumFractionDigits: 2,
          })}
        </h2>

        <p className="producto-descripcion">{producto.descripcion}</p>

        <p className="producto-stock">
          <strong>Disponibilidad:</strong> {stockDisponible} unidades en stock
        </p>

        <div className="acciones-contenedor">
          <div className="cantidad-selector">
            <input
              type="number"
              min="1"
              max={stockDisponible}
              value={cantidad}
              onChange={handleCantidadChange}
              disabled={stockDisponible <= 0}
            />
          </div>

          <button
            className="btn-agregar"
            onClick={handleAgregarAlCarrito}
            disabled={stockDisponible <= 0}
          >
            AGREGAR AL CARRITO
          </button>

          <button
            className="btn-comprar"
            onClick={handleComprarAhora}
            disabled={stockDisponible <= 0}
          >
            COMPRAR AHORA
          </button>
        </div>
      </div>
    </div>
  );
}

export default DetalleProducto;