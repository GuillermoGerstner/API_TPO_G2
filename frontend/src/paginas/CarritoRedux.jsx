import { useSelector, useDispatch } from "react-redux"; 
import { removeFromCart, clearCart } from "../store/slices/cartSlices"; 

function Carrito() {
  const dispatch = useDispatch(); 

  
  const carritoItems = useSelector((state) => state.cart.items);

  return (
    <>
      <h1>Carrito</h1>

      {carritoItems.length === 0 ? (
        <p>Carrito vacío</p>
      ) : (
        <>
          {carritoItems.map((item) => (
            <div key={item.id}>
              <h3>{item.nombre}</h3>
              
              <button onClick={() => dispatch(removeFromCart(item.id))}>
                Eliminar
              </button>
            </div>
          ))}

          <button onClick={() => dispatch(clearCart())}>
            Vaciar Carrito
          </button>
        </>
      )}
    </>
  );
}

export default Carrito;