import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux"; 
import { addToCart } from "../store/slices/cartSlices"; 
import api from "../api/api";

function DetalleProducto() {
  const { id } = useParams();
  const navigate = useNavigate();
  const dispatch = useDispatch(); 
  const [producto, setProducto] = useState(null);

  useEffect(() => {
    api
      .get(`/productos/${id}`)
      .then((res) => setProducto(res.data))
      .catch((err) => console.error("Error al cargar producto:", err)); 
  }, [id]);

  if (!producto) {
    return <h2>Cargando...</h2>;
  }

  return (
    <>
      <h1>{producto.nombre}</h1>
      <p>{producto.descripcion}</p>
      <p>Precio: ${producto.precio}</p>

      
      <button onClick={() => dispatch(addToCart(producto))}>
        Agregar al carrito
      </button>

      
      <button
        onClick={() => {
          dispatch(addToCart(producto));
          navigate("/carrito");
        }}
      >
        Comprar ahora
      </button>
    </>
  );
}

export default DetalleProducto;