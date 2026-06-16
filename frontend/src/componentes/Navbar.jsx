function Navbar() {
    const { carritoItems } = useContext(CarritoContext);

    return (
        <nav>
            <Link to = "/">Inicio</Link>{" | "}

            <Link to = "/carrito">
                Carrito ({carritoItems.length})
            </Link>{" | "}

            <Link to = "/login">Login</Link>{" | "}

            <Link to = "/productos">
                Gestion Productos
            </Link>
        </nav>
    );
}

export default Navbar;