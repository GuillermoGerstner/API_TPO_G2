import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import api from "../api/api";
import { CarritoContext } from "../contexto/CarritoProvider";

import "../styles/Perfil.css";

function Perfil() {
  const navigate = useNavigate();
  const { clearCarrito } = useContext(CarritoContext);

  const [usuario, setUsuario] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/login");
      return;
    }

    api
      .get("/usuarios/actual", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        setUsuario(res.data);
      })
      .catch((err) => {
        console.error("Error cargando perfil:", err);
        setError("No se pudo cargar la información del perfil.");
      })
      .finally(() => {
        setLoading(false);
      });
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("token");
    localStorage.removeItem("userEmail");

    clearCarrito();

    alert("Sesión cerrada correctamente.");
    navigate("/");
    window.location.reload();
  };

  if (loading) {
    return (
      <div style={{ textAlign: "center", padding: "50px" }}>
        Cargando perfil...
      </div>
    );
  }

  if (error) {
    return (
      <div style={{ textAlign: "center", padding: "50px" }}>
        {error}
      </div>
    );
  }

  return (
    <div className="perfil-page">
      <div className="perfil-card">
        <div className="perfil-avatar_wrapper">
          <span className="perfil-avatar_icon">👤</span>
        </div>

        <h2 className="perfil-title">¡Hola, {usuario?.username}!</h2>

        <p className="perfil-subtitle">
          Bienvenido a tu panel de usuario de la tienda.
        </p>

        <div className="perfil-info_box">
          <strong>Nombre:</strong> {usuario?.nombre} {usuario?.apellido}
        </div>

        <div className="perfil-info_box">
          <strong>Email:</strong> {usuario?.email}
        </div>

        <div className="perfil-info_box">
          <strong>Rol:</strong> {usuario?.role}
        </div>

        <button
          type="button"
          className="perfil-btn_logout"
          onClick={handleLogout}
        >
          Cerrar Sesión
        </button>
      </div>
    </div>
  );
}

export default Perfil;