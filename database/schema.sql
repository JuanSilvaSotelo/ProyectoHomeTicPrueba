-- Tabla Producto
-- Tabla Producto: Almacena información sobre los productos disponibles.
CREATE TABLE Producto (
    -- ProductoID: Identificador único del producto (clave primaria).
    ProductoID INT PRIMARY KEY AUTO_INCREMENT,
    -- Nombre: Nombre del producto.
    -- Nombre: Nombre del vendedor.
    Nombre VARCHAR(255) NOT NULL,
    -- Descripcion: Descripción detallada del producto.
    Descripcion VARCHAR(255),
    -- Precio: Precio unitario del producto.
    Precio DECIMAL(10, 2) NOT NULL,
    -- Stock: Cantidad de unidades del producto en inventario.
    Stock INT NOT NULL
);

-- Tabla Vendedor
-- Tabla Vendedor: Almacena información sobre los vendedores.
CREATE TABLE Vendedor (
    -- VendedorID: Identificador único del vendedor (clave primaria).
    VendedorID INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(255) NOT NULL,
    -- Apellido: Apellido del vendedor.
    Apellido VARCHAR(255) NOT NULL,
    -- Email: Correo electrónico del vendedor (debe ser único).
    Email VARCHAR(255) UNIQUE NOT NULL,
    -- Telefono: Número de teléfono del vendedor.
    Telefono VARCHAR(20)
);

-- Tabla Venta
-- Tabla Venta: Registra cada transacción de venta.
CREATE TABLE Venta (
    -- VentaID: Identificador único de la venta (clave primaria).
    VentaID INT PRIMARY KEY AUTO_INCREMENT,
    -- FechaVenta: Fecha y hora en que se realizó la venta (por defecto, la fecha y hora actuales).
    FechaVenta DATETIME NOT NULL DEFAULT NOW(),
    -- Total: Monto total de la venta.
    Total DECIMAL(10, 2) NOT NULL,
    -- VendedorID: Identificador del vendedor que realizó la venta (clave foránea a la tabla Vendedor).
    VendedorID INT,
    -- ProductoID: Identificador del producto vendido (clave foránea a la tabla Producto).
    ProductoID INT,
    -- Cantidad: Cantidad del producto vendido en esta transacción.
    Cantidad INT NOT NULL,
    -- FOREIGN KEY (VendedorID): Define la relación con la tabla Vendedor.
    FOREIGN KEY (VendedorID) REFERENCES Vendedor(VendedorID),
    -- FOREIGN KEY (ProductoID): Define la relación con la tabla Producto.
    FOREIGN KEY (ProductoID) REFERENCES Producto(ProductoID)
);