import React from 'react';
import { Link } from 'react-router-dom';
import { useSelector } from 'react-redux'; 

function Navbar() {
  const carritoItems = useSelector((state) => state.cart.items);

  return (
    <nav>
      <Link to="/">Inicio</Link>{" | "}

      <Link to="/carrito">
        Carrito ({carritoItems.length})
      </Link>{" | "}

      <Link to="/login">Login</Link>{" | "}

      <Link to="/productos">
        Gestion Productos
      </Link>
    </nav>
  );
}

export default Navbar;