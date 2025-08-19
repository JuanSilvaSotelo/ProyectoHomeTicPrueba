-- Inserción de datos de prueba en la tabla Producto
-- Inserción de datos de prueba en la tabla Producto
INSERT INTO Producto (Nombre, Descripcion, Precio, Stock) VALUES
('Laptop Dell XPS 15', 'Potente laptop para profesionales', 1500.00, 50),
('Monitor curvo Samsung', 'Monitor de 27 pulgadas con alta resolución', 300.00, 120),
('Teclado mecánico HyperX', 'Teclado gaming con switches Cherry MX', 120.00, 80),
('Mouse Logitech MX Master 3', 'Mouse ergonómico avanzado', 90.00, 150),
('Webcam Logitech C920', 'Cámara web Full HD para videollamadas', 60.00, 200);


-- Inserción de datos de prueba en la tabla Vendedor
INSERT INTO Vendedor (Nombre, Apellido, Email, Telefono) VALUES
('Juan', 'Perez', 'juan.perez@example.com', '555-1234'),
('Maria', 'Gomez', 'maria.gomez@example.com', '555-5678'),
('Carlos', 'Ruiz', 'carlos.ruiz@example.com', '555-8765'),
('Ana', 'Lopez', 'ana.lopez@example.com', '555-4321'),
('Pedro', 'Martinez', 'pedro.martinez@example.com', '555-9876');


-- Inserción de datos de prueba en la tabla Venta
-- Asegúrate de que los ProductoID y VendedorID existan en sus respectivas tablas
INSERT INTO Venta (FechaVenta, Total, VendedorID, ProductoID, Cantidad) VALUES
(NOW(), 1500.00, 1, 1, 1),
(NOW(), 600.00, 2, 2, 2),
(NOW(), 120.00, 3, 3, 1),
(NOW(), 180.00, 4, 4, 2),
(NOW(), 60.00, 5, 5, 1);