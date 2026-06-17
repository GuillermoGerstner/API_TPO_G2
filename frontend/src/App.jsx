import {
  BrowserRouter,
  Routes,
  Route,
} from "react-router-dom";
import { Provider } from 'react-redux';
import { store } from './store';

import Navbar from "./componentes/Navbar";
import Inicio from "./paginas/Inicio";
import DetalleProducto from "./paginas/DetalleProducto";
import Carrito from "./paginas/Carrito";
import Login from "./paginas/Login";
import GestionProductos from "./paginas/GestionProductos";
import FormularioProducto from "./paginas/FormularioProducto";
//Redux
import DetalleProductoRedux from "./paginas/DetalleProductoRedux";
import CarritoRedux from "./paginas/CarritoRedux";

import {CarritoProvider} from "./contexto/CarritoProvider";

function App() {
  return (
    <Provider store={store}>  
      <CarritoProvider>
        <BrowserRouter>
          <Navbar />
          <Routes>
            <Route path="/" element={<Inicio />} />
            <Route path="/productos/:id" element={<DetalleProductoRedux />} /> {/*ahora con redux*/ }
            <Route path="/carrito" element={<CarritoRedux />} /> {/*ahora con redux*/ }
            <Route path="/login" element={<Login />} />
            <Route path="/gestion-productos" element={<GestionProductos />} />
            <Route path="/formulario-producto" element={<FormularioProducto />} />
          </Routes>
        </BrowserRouter>
      </CarritoProvider>
    </Provider>
  )
}

export default App;
