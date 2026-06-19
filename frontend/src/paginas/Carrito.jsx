import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";

import api from "../api/api";
import { CarritoContext } from "../contexto/CarritoProvider";
import cameraImg from "../assets/camera.png";

import "../styles/Carrito.css";

function Carrito() {
  const {
    carritoItems,
    incrementarCantidad,
    decrementarCantidad,
    removeFromCarrito,
    clearCarrito,
  } = useContext(CarritoContext);

  const navigate = useNavigate();
  const [procesandoCompra, setProcesandoCompra] = useState(false);

  const cantidadItems = carritoItems.reduce(
    (acc, item) => acc + item.cantidad,
    0,
  );

  const subtotal = carritoItems.reduce(
    (acc, item) => acc + item.precio * item.cantidad,
    0,
  );

  const envio = carritoItems.length > 0 ? 50.0 : 0;
  const impuestos = carritoItems.length > 0 ? 10.0 : 0;
  const total = subtotal + envio + impuestos;

  const handleFinalizarCompra = async () => {
    if (carritoItems.length === 0) {
      alert("El carrito está vacío.");
      return;
    }

    const token = localStorage.getItem("token");

    if (!token) {
      alert("Debés iniciar sesión para finalizar la compra.");
      navigate("/login");
      return;
    }

    setProcesandoCompra(true);

    try {
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };

      const carritoResponse = await api.get("/carrito/actual", config);
      const carritoId = carritoResponse.data.id;

      await api.delete(`/carrito/${carritoId}/vaciar`, config);

      for (const item of carritoItems) {
        await api.post(`/carrito/${carritoId}/agregar`, null, {
          ...config,
          params: {
            productoId: item.id,
            cantidad: item.cantidad,
          },
        });
      }

      const pedidoResponse = await api.post(
        `/carrito/${carritoId}/checkout`,
        null,
        config,
      );

      clearCarrito();

      alert(`¡Compra finalizada con éxito! Pedido #${pedidoResponse.data.id}`);

      navigate("/");
    } catch (error) {
      console.error("Error al finalizar la compra:", error);

      const mensaje =
        error.response?.data?.message ||
        error.response?.data?.error ||
        "No se pudo finalizar la compra.";

      alert(mensaje);
    } finally {
      setProcesandoCompra(false);
    }
  };

  return (
    <div className="carrito-pagina-contenedor">
      <h1 className="carrito-titulo-principal">
        Mi Carrito <span className="items-count">({cantidadItems} items)</span>
      </h1>

      {carritoItems.length === 0 ? (
        <div className="carrito-vacio-box">
          <p>Tu carrito está vacío.</p>
        </div>
      ) : (
        <div className="carrito-layout">
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
                    <td className="celda-producto">
                      <div className="prod-img-box">
                        <img
                          src={item.imagen || cameraImg}
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
                          Stock: {item.stock ?? 0} unidades
                        </span>
                      </div>
                    </td>

                    <td className="celda-precio-u">
                      $
                      {item.precio?.toLocaleString("es-AR", {
                        minimumFractionDigits: 2,
                      })}
                    </td>

                    <td className="celda-cantidad">
                      <div className="counter-container">
                        <button onClick={() => decrementarCantidad(item.id)}>
                          -
                        </button>

                        <span className="counter-value">{item.cantidad}</span>

                        <button onClick={() => incrementarCantidad(item.id)}>
                          +
                        </button>
                      </div>
                    </td>

                    <td className="celda-subtotal">
                      $
                      {(item.precio * item.cantidad)?.toLocaleString("es-AR", {
                        minimumFractionDigits: 2,
                      })}
                    </td>

                    <td className="celda-eliminar">
                      <button
                        className="btn-trash"
                        onClick={() => removeFromCarrito(item.id)}
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
              <button className="btn-vaciar" onClick={clearCarrito}>
                Vaciar Carrito
              </button>
            </div>
          </div>

          <div className="resumen-compra-tarjeta">
            <h3>Resumen de Compra</h3>

            <div className="resumen-linea">
              <span>Subtotal</span>
              <strong>
                ${subtotal.toLocaleString("es-AR", { minimumFractionDigits: 2 })}
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
              onClick={handleFinalizarCompra}
              disabled={procesandoCompra}
            >
              {procesandoCompra ? "PROCESANDO..." : "FINALIZAR COMPRA"}
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

export default Carrito;