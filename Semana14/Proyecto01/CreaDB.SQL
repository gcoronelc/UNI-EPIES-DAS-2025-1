-- ========================================
-- Base de datos: Sistema de Inventario
-- ========================================

USE BDInventario;
go

-- Creación de tabla Unidad de Medida
CREATE TABLE UnidadMedida (
    UnidadID INT PRIMARY KEY IDENTITY(1,1),
    Nombre NVARCHAR(50) NOT NULL,
    Abreviatura NVARCHAR(10) NOT NULL
);

-- Creación de tabla Categoría
CREATE TABLE Categoria (
    CategoriaID INT PRIMARY KEY IDENTITY(1,1),
    Nombre NVARCHAR(100) NOT NULL UNIQUE
);

-- Creación de tabla Producto
CREATE TABLE Producto (
    ProductoID INT PRIMARY KEY IDENTITY(1,1),
    Codigo NVARCHAR(50) NOT NULL UNIQUE,
    Nombre NVARCHAR(100) NOT NULL,
    CategoriaID INT NOT NULL,
    Precio DECIMAL(10, 2) NOT NULL,
    Stock INT NOT NULL,
    StockMinimo INT NOT NULL,
    UnidadID INT NOT NULL,
    FOREIGN KEY (CategoriaID) REFERENCES Categoria(CategoriaID),
    FOREIGN KEY (UnidadID) REFERENCES UnidadMedida(UnidadID)
);

-- Creación de tabla Cliente
CREATE TABLE Cliente (
    ClienteID INT PRIMARY KEY IDENTITY(1,1),
    Nombre NVARCHAR(100) NOT NULL,
    Telefono NVARCHAR(20),
    Email NVARCHAR(100),
    Direccion NVARCHAR(200)
);

-- Creación de tabla Usuario
CREATE TABLE Usuario (
    UsuarioID INT PRIMARY KEY IDENTITY(1,1),
    Nombre NVARCHAR(100) NOT NULL,
    Usuario NVARCHAR(50) NOT NULL UNIQUE,
    ClaveHash NVARCHAR(256) NOT NULL,
    Rol NVARCHAR(20) NOT NULL
);

-- Creación de tabla Venta
CREATE TABLE Venta (
    VentaID INT PRIMARY KEY IDENTITY(1,1),
    Fecha DATETIME NOT NULL DEFAULT GETDATE(),
    Total DECIMAL(10, 2) NOT NULL,
    ClienteID INT NULL,
    UsuarioID INT NOT NULL,
    FOREIGN KEY (ClienteID) REFERENCES Cliente(ClienteID),
    FOREIGN KEY (UsuarioID) REFERENCES Usuario(UsuarioID)
);

-- Creación de tabla Detalle de Venta
CREATE TABLE DetalleVenta (
    DetalleID INT PRIMARY KEY IDENTITY(1,1),
    VentaID INT NOT NULL,
    ProductoID INT NOT NULL,
    Cantidad INT NOT NULL,
    PrecioVenta DECIMAL(10, 2) NOT NULL,
    Subtotal DECIMAL(10, 2) NOT NULL, -- Columna almacenada, no calculada
    FOREIGN KEY (VentaID) REFERENCES Venta(VentaID),
    FOREIGN KEY (ProductoID) REFERENCES Producto(ProductoID)
);
