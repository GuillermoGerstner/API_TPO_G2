import { useSelector, useDispatch } from "react-redux";
import {
  removeFromCart,
  clearCart,
  incrementarCantidad,
  decrementarCantidad,
} from "../store/slices/cartSlices";
import "../styles/Carrito.css"; // Archivo de estilos que crearemos abajo

function Carrito() {
  const dispatch = useDispatch();
  const carritoItems = useSelector((state) => state.cart.items);

  // Cálculos del Resumen de Compra
  const subtotal = carritoItems.reduce(
    (acc, item) => acc + item.precio * item.cantidad,
    0,
  );
  const envio = carritoItems.length > 0 ? 50.0 : 0;
  const impuestos = carritoItems.length > 0 ? 10.0 : 0;
  const total = subtotal + envio + impuestos;

  return (
    <div className="carrito-pagina-contenedor">
      <h1 className="carrito-titulo-principal">
        Mi Carrito{" "}
        <span className="items-count">
          ({carritoItems.reduce((acc, item) => acc + item.cantidad, 0)} items)
        </span>
      </h1>

      {carritoItems.length === 0 ? (
        <div className="carrito-vacio-box">
          <p>Tu carrito está vacío.</p>
        </div>
      ) : (
        <div className="carrito-layout">
          {/* COLUMNA IZQUIERDA: TABLA DE PRODUCTOS */}
          <div className="tabla-productos-contenedor">
            <table className="carrito-tabla">
              <thead>
                <tr>
                  <th>Producto</th>
                  <th>Precio</th>
                  <th>Cantidad</th>
                  <th>Subtotal</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {carritoItems.map((item) => (
                  <tr key={item.id}>
                    {/* Celda Producto con imagen e info detallada */}
                    <td className="celda-producto">
                      <div className="prod-img-box">
                        <img
                          src={item.imagen || "https://via.placeholder.com/80"}
                          alt={item.nombre}
                        />
                      </div>
                      <div className="prod-info-text">
                        <h4>{item.nombre}</h4>
                        <span className="prod-precio-mini">
                          $
                          {item.precio?.toLocaleString("es-AR", {
                            minimumFractionDigits: 2,
                          })}
                        </span>
                        <span className="prod-stock-mini">
                          Stock: {item.stock || 15} unidades
                        </span>
                      </div>
                    </td>

                    {/* Celda Precio Unitario */}
                    <td className="celda-precio-u">
                      $
                      {item.precio?.toLocaleString("es-AR", {
                        minimumFractionDigits: 2,
                      })}
                    </td>

                    {/* Celda Selectores de Cantidad */}
                    <td className="celda-cantidad">
                      <div className="counter-container">
                        <button
                          onClick={() => dispatch(decrementarCantidad(item.id))}
                        >
                          -
                        </button>
                        <span className="counter-value">{item.cantidad}</span>
                        <button
                          onClick={() => dispatch(incrementarCantidad(item.id))}
                        >
                          +
                        </button>
                      </div>
                    </td>

                    {/* Celda Subtotal por Item */}
                    <td className="celda-subtotal">
                      $
                      {(item.precio * item.cantidad)?.toLocaleString("es-AR", {
                        minimumFractionDigits: 2,
                      })}
                    </td>

                    {/* Celda Acción Eliminar (Tachito de basura en Figma) */}
                    <td className="celda-eliminar">
                      <button
                        className="btn-trash"
                        onClick={() => dispatch(removeFromCart(item.id))}
                        title="Eliminar producto"
                      >
                        🗑️
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>

            <div className="tabla-footer-actions">
              <button
                className="btn-vaciar"
                onClick={() => dispatch(clearCart())}
              >
                Vaciar Carrito
              </button>
            </div>
          </div>

          {/* COLUMNA DERECHA: RESUMEN DE COMPRA */}
          <div className="resumen-compra-tarjeta">
            <h3>Resumen de Compra</h3>
            <div className="resumen-linea">
              <span>Subtotal</span>
              <strong>
                $
                {subtotal.toLocaleString("es-AR", { minimumFractionDigits: 2 })}
              </strong>
            </div>
            <div className="resumen-linea">
              <span>Envío</span>
              <strong>
                ${envio.toLocaleString("es-AR", { minimumFractionDigits: 2 })}
              </strong>
            </div>
            <div className="resumen-linea">
              <span>Impuestos</span>
              <strong>
                $
                {impuestos.toLocaleString("es-AR", {
                  minimumFractionDigits: 2,
                })}
              </strong>
            </div>

            <hr className="resumen-divider" />

            <div className="resumen-linea total-linea">
              <span>Total</span>
              <strong>
                ${total.toLocaleString("es-AR", { minimumFractionDigits: 2 })}
              </strong>
            </div>

            <button
              className="btn-finalizar-compra"
              onClick={() => alert("Procesando compra...")}
            >
              FINALIZAR COMPRA
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

export default Carrito;
