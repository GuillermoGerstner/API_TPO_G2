import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";
import "../styles/Navbar.css";

function Navbar() {
  const carritoItems = useSelector((state) => state.cart.items);

  // 1. Estado local para rastrear si el usuario está conectado
  const [isLogged, setIsLogged] = useState(false);

  useEffect(() => {
    // 2. Al cargar el componente, revisamos si el navegador guardó la sesión
    const session = localStorage.getItem("isLoggedIn");
    if (session === "true") {
      setIsLogged(true);
    }
  }, []);

  // 3. Función opcional por si quieren agregar un botón de cerrar sesión más adelante
  const handleLogout = () => {
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("token");
    setIsLogged(false);
    window.location.href = "/";
  };

  return (
    <header className="navbar-container">
      {/* Sector Izquierdo: LOGO */}
      <div className="navbar__logo">
        <Link to="/" className="navbar__logo-link">
          <span className="navbar__logo-bold">TPO</span>
          <span className="navbar__logo-sub">E-Commerce</span>
        </Link>
      </div>

      {/* Sector Central: BUSCADOR */}
      <div className="navbar__search-wrapper">
        <span className="navbar__search-icon">🔍</span>
        <input
          type="text"
          placeholder="Buscar..."
          className="navbar__search-input"
        />
      </div>

      {/* Sector Derecho: ACCIONES Y RUTAS */}
      <div className="navbar__actions">
        {/* 🔒 RENDERIZADO CONDICIONAL: Solo aparece si isLogged es true */}
        {isLogged && (
          <Link to="/gestion-productos" className="navbar__link-text">
            Gestión
          </Link>
        )}

        {/* Icono de Mi Perfil / Con condicional de ruta */}
        <Link 
          to={isLogged ? "/perfil" : "/login"} // 👈 ¡EL TRUCO ESTÁ ACÁ!
          className="navbar_action-icon" 
          title="Mi Cuenta"
        >
          👤
        </Link>

        {/* Icono del Carrito */}
        <Link
          to="/carrito"
          className="navbar__action-icon navbar__cart-btn"
          title="Carrito de compras"
        >
          🛒
          {carritoItems.length > 0 && (
            <span className="navbar__cart-badge">{carritoItems.length}</span>
          )}
        </Link>

        {/* Pequeño botón de salir si está logueado (puedes quitarlo si no te gusta visualmente) */}
        {isLogged && (
          <button
            onClick={handleLogout}
            className="navbar__logout-btn"
            title="Cerrar Sesión"
          >
            🚪
          </button>
        )}
      </div>
    </header>
  );
}

export default Navbar;
