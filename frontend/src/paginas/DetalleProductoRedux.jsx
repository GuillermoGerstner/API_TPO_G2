import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { addToCart } from "../store/slices/cartSlices";
import api from "../api/api";
import "../styles/DetalleProducto.css"; // Ruta actualizada a tu carpeta styles

function DetalleProducto() {
  const { id } = useParams();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [producto, setProducto] = useState(null);

  // Estados para controlar cantidad y galería de imágenes
  const [cantidad, setCantidad] = useState(1);
  const [imagenSeleccionada, setImagenSeleccionada] = useState("");

  useEffect(() => {
    api
      .get(`/productos/${id}`)
      .then((res) => {
        setProducto(res.data);
        // Si tu backend maneja array de imágenes ponemos la primera, sino usamos 'imagen' único
        if (res.data.imagenes && res.data.imagenes.length > 0) {
          setImagenSeleccionada(res.data.imagenes[0]);
        } else {
          setImagenSeleccionada(res.data.imagen);
        }
      })
      .catch((err) => console.error("Error al cargar producto:", err));
  }, [id]);

  if (!producto) {
    return <h2 className="cargando">Cargando...</h2>;
  }

  // Si el objeto no trae array de imágenes secundarias, generamos un array simulado con su foto única para poblar la barra lateral como en figma
  const listaImagenes =
    producto.imagenes ||
    Array(5).fill(producto.imagen || "https://via.placeholder.com/150");

  const handleAgregarAlCarrito = () => {
    // Mandamos el objeto con el ID directo mapeado del backend y la cantidad seleccionada
    dispatch(addToCart({ ...producto, cantidadPedida: cantidad }));
  };

  const handleComprarAhora = () => {
    handleAgregarAlCarrito();
    navigate("/carrito");
  };

  return (
    <div className="detalle-container">
      {/* Sección Izquierda: Galería Vertical e Imagen Destacada */}
      <div className="galeria-seccion">
        <div className="miniaturas-lista">
          {listaImagenes.map((img, index) => (
            <button
              key={index}
              className={`miniatura-btn ${imagenSeleccionada === img ? "activa" : ""}`}
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

      {/* Sección Derecha: Datos del Producto y Acciones */}
      <div className="info-seccion">
        <h1 className="producto-titulo">{producto.nombre}</h1>

        <div className="categoria-badge">
          Categoría:{" "}
          <span className="categoria-nombre">
            {producto.categoria?.nombre || "Electrónica"}
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
          <strong>Disponibilidad:</strong> {producto.stock || 15} unidades en
          stock
        </p>

        {/* Fila del selector numérico y los CTAs */}
        <div className="acciones-contenedor">
          <div className="cantidad-selector">
            <input
              type="number"
              min="1"
              max={producto.stock || 15}
              value={cantidad}
              onChange={(e) =>
                setCantidad(Math.max(1, parseInt(e.target.value) || 1))
              }
            />
          </div>

          <button className="btn-agregar" onClick={handleAgregarAlCarrito}>
            AGREGAR AL CARRITO
          </button>

          <button className="btn-comprar" onClick={handleComprarAhora}>
            COMPRAR AHORA
          </button>
        </div>
      </div>
    </div>
  );
}

export default DetalleProducto;
