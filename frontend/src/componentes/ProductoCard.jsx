import { Link } from "react-router-dom";
import "../styles/ProductoCard.css";
// Tip: Si no tienes la imagen real, puedes usar una URL de marcador de posición temporal:
import cameraImg from "../assets/camera.png";

function ProductoCard({ producto }) {
  return (
    <div className="product-card">
      <div className="product-card__img-container">
        {/* Usamos cameraImg local o producto.imagenUrl si el backend la trae */}
        <img
          src={cameraImg}
          alt={producto.nombre}
          className="product-card__img"
        />
      </div>
      <div className="product-card__info">
        <h3 className="product-card__title">{producto.nombre}</h3>
        <p className="product-card__price">${producto.precio?.toFixed(2)}</p>
        {/* Si tu backend no trae stock todavía, dejamos el valor del mockup (15) fijo por ahora */}
        <p className="product-card__stock">
          Stock: {producto.stock || 15} unidades
        </p>
      </div>
      {/* Opcional: Si quieren mantener el link de detalles abajo */}
      <Link to={`/productos/${producto.id}`} className="product-card__link">
        Ver detalles
      </Link>
    </div>
  );
}

export default ProductoCard;
