import {
  BrowserRouter,
  Routes,
  Route,
} from "react-router-dom";

import Navbar from "./componentes/Navbar";
import Inicio from "./paginas/Inicio";
import DetalleProducto from "./paginas/DetalleProducto";
import Carrito from "./paginas/Carrito";
import Login from "./paginas/Login";
import GestionProductos from "./paginas/GestionProductos";
import FormularioProducto from "./paginas/FormularioProducto";

import {CarritoProvider} from "./contexto/CarritoProvider";

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
