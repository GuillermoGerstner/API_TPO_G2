import {useState} from 'react';

function Login() {
    const [isLogin, setIsLogin] = useState(true);

    return (
        <>
            <h1>{isLogin ? "Iniciar Sesión" : "Registrarse"}</h1>

            <form>
                <input placeholder="Email" />
                <input placeholder="Contraseña" type="password" />

                <button> {isLogin ? "Iniciar Sesión" : "Registrarse"} </button>
            </form>

            <button onClick={() => setIsLogin(!isLogin)}> Cambiar modo </button>
        </>
    );
}

export default Login;