import React, { useEffect, useState } from 'react';
import '../styles/Perfil.css';

function Perfil() {
  const [userLogged, setUserLogged] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const emailLogueado = localStorage.getItem("userEmail");

    if (emailLogueado) {
      fetch("http://localhost:8080/api/usuarios")
        .then(res => {
          if (!res.ok) throw new Error("Error al obtener usuarios");
          return res.json();
        })
        .then(usuarios => {
          
          const usuarioEncontrado = usuarios.find(
            u => u.email?.toLowerCase() === emailLogueado.toLowerCase()
          );

          if (usuarioEncontrado) {
            setUserLogged(usuarioEncontrado.username); 
          } else {
            setUserLogged(emailLogueado.split('@')[0]);
          }
          setLoading(false);
        })
        .catch(err => {
          console.error("Error cargando perfil:", err);
          setUserLogged(emailLogueado.split('@')[0]);
          setLoading(false);
        });
    } else {
      setLoading(false);
    }
  }, []);

  const handleLogout = () => {
    localStorage.clear();
    alert("Sesión cerrada correctamente.");
    window.location.href = "/";
  };

  if (loading) return <div style={{ textAlign: 'center', padding: '50px' }}>Cargando perfil...</div>;

  return (
    <div className="perfil-page">
      <div className="perfil-card">
        <div className="perfil-avatar_wrapper">
          <span className="perfil-avatar_icon">👤</span>
        </div>

        <h2 className="perfil-title">¡Hola!</h2>
        <p className="perfil-subtitle">Bienvenido a tu panel de usuario de la tienda.</p>
        
        <div className="perfil-info_box">
            <strong>Conectado como {userLogged}</strong>
        </div>

        <button type="button" className="perfil-btn_logout" onClick={handleLogout}>
          Cerrar Sesión
        </button>
      </div>
    </div>
  );
}

export default Perfil;