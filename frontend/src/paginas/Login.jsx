import { useState } from "react";
import "../styles/Login.css";

import userIcon from "../assets/user.svg";
import mailIcon from "../assets/mail.svg";
import lockIcon from "../assets/lock.svg";

function Login() {
  const [isLogin, setIsLogin] = useState(true);

  // 1. Creamos un estado único para manejar todos los campos del formulario
  const [formData, setFormData] = useState({
    username: "",
    mail: "",
    password: "",
    name: "",
    lastname: "",
  });

  // Función para actualizar el estado a medida que el usuario escribe
  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData({
      ...formData,
      [id]: value, // Vincula el "id" del input con la propiedad del estado
    });
  };

  // 2. Función que se ejecuta al darle al botón de enviar (Submit)
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (isLogin) {
      // Lógica de Login
      try {
        const datosParaEnviar = {
          email: formData.mail,
          password: formData.password,
        };

        const response = await fetch("http://localhost:8080/api/auth/login", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(datosParaEnviar),
        });

        if (response.ok) {
          const token = await response.text();

          // Guardamos la sesión local para el Navbar
          localStorage.setItem("isLoggedIn", "true");
          localStorage.setItem("token", token);

          alert("¡Inicio de sesión exitoso!");
          window.location.href = "/"; // Redirecciona al catálogo y actualiza el Navbar
        } else {
          alert("Usuario o contraseña incorrectos.");
        }
      } catch (error) {
        console.error("Error en el login:", error);
        alert("No se pudo conectar con el servidor.");
      }
    } else {
      // Lógica de Registro
      try {
        const response = await fetch(
          "http://localhost:8080/api/auth/register",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              username: formData.username,
              email: formData.mail, // "email" en minúscula (Java espera email)
              password: formData.password, // "password" tal cual lo pusiste
              nombre: formData.name, // Cambiado de firstName a nombre
              apellido: formData.lastname, // Cambiado de lastName a apellido
            }),
          },
        );

        if (response.ok) {
          alert("¡Usuario registrado con éxito!");
          setIsLogin(true); // Cambia a la pestaña de login automáticamente
        } else {
          // Si el backend responde con un error (ej: contraseña muy corta)
          const errorText = await response.text();
          alert(
            `Error al registrar: ${errorText || "Verifica las validaciones"}`,
          );
        }
      } catch (error) {
        console.error("Error en el registro:", error);
        alert("No se pudo conectar con el servidor.");
      }
    }
  };

  return (
    <div className="login-page">
      <div className="login-card">
        <div className="login-card__tabs">
          <button
            className={`login-card__tab ${isLogin ? "login-card__tab--active" : ""}`}
            onClick={() => setIsLogin(true)}
            type="button"
          >
            Iniciar sesión
          </button>
          <button
            className={`login-card__tab ${!isLogin ? "login-card__tab--active" : ""}`}
            onClick={() => setIsLogin(false)}
            type="button"
          >
            Registrarse
          </button>
        </div>

        {/* Agregamos el onSubmit aquí */}
        <form className="login-card__form" onSubmit={handleSubmit}>
          {!isLogin && (
            <div className="login-card__field">
              <label htmlFor="username">Nombre de usuario</label>
              <div className="login-card__input-wrapper">
                <img src={userIcon} alt="" className="login-card__icon" />
                <input
                  id="username"
                  placeholder="Nombre de usuario"
                  type="text"
                  value={formData.username} // Input controlado
                  onChange={handleChange} // Escucha los cambios
                  required
                />
              </div>
            </div>
          )}

          <div className="login-card__field">
            <label htmlFor="mail">Mail</label>
            <div className="login-card__input-wrapper">
              <img src={mailIcon} alt="" className="login-card__icon" />
              <input
                id="mail"
                placeholder="Mail"
                type="email"
                value={formData.mail}
                onChange={handleChange}
                required
              />
            </div>
          </div>

          <div className="login-card__field">
            <label htmlFor="password">Contraseña</label>
            <div className="login-card__input-wrapper">
              <img src={lockIcon} alt="" className="login-card__icon" />
              <input
                id="password"
                placeholder="Contraseña"
                type="password"
                value={formData.password}
                onChange={handleChange}
                required
              />
            </div>
          </div>

          {!isLogin && (
            <>
              <div className="login-card__field">
                <label htmlFor="name">Nombre</label>
                <div className="login-card__input-wrapper">
                  <img src={userIcon} alt="" className="login-card__icon" />
                  <input
                    id="name"
                    placeholder="Nombre"
                    type="text"
                    value={formData.name}
                    onChange={handleChange}
                    required
                  />
                </div>
              </div>

              <div className="login-card__field">
                <label htmlFor="lastname">Apellido</label>
                <div className="login-card__input-wrapper">
                  <img src={userIcon} alt="" className="login-card__icon" />
                  <input
                    id="lastname"
                    placeholder="Apellido"
                    type="text"
                    value={formData.lastname}
                    onChange={handleChange}
                    required
                  />
                </div>
              </div>
            </>
          )}

          <button
            type="submit"
            className="login-card__btn login-card__btn--primary"
          >
            {isLogin ? "INGRESAR" : "REGISTRARSE"}
          </button>

          <button
            type="button"
            className="login-card__btn login-card__btn--secondary"
            onClick={() =>
              setFormData({
                username: "",
                mail: "",
                password: "",
                name: "",
                lastname: "",
              })
            }
          >
            CANCELAR
          </button>
        </form>
      </div>
    </div>
  );
}

export default Login;
