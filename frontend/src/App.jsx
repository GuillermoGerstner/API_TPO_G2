import {
  BrowserRouter,
  Routes,
  Route,
} from "react-router-dom";

import Navbar from "./components/Navbar";
import Inicio from "./pages/Inicio";
import DetalleProducto from "./pages/DetalleProducto";
import Carrito from "./pages/Carrito";
import Login from "./pages/Login";
import GestionProductos from "./pages/GestionProductos";
import FormularioProducto from "./pages/FormularioProducto";

import {CarritoProvider} from "./context/CarritoProvider";

function App() {
  return (
    <CarritoProvider>
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route path="/" element={<Inicio />} />
          <Route path="/productos/:id" element={<DetalleProducto />} />
          <Route path="/carrito" element={<Carrito />} />
          <Route path="/login" element={<Login />} />
          <Route path="/gestion-productos" element={<GestionProductos />} />
          <Route path="/formulario-producto" element={<FormularioProducto />} />
        </Routes>
      </BrowserRouter>
    </CarritoProvider>
  )
}

export default App;