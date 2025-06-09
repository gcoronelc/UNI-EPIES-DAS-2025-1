-- Tablas principales
CREATE TABLE Estudiante (
    estudiante_id INT PRIMARY KEY IDENTITY(1,1),
    nombre NVARCHAR(100) NOT NULL,
    apellido NVARCHAR(100) NOT NULL,
    fecha_nacimiento DATE,
    direccion NVARCHAR(200),
    telefono NVARCHAR(20)
);

CREATE TABLE Curso (
    curso_id INT PRIMARY KEY IDENTITY(1,1),
    nombre NVARCHAR(50) NOT NULL,
    descripcion NVARCHAR(200)
);

CREATE TABLE Asignatura (
    asignatura_id INT PRIMARY KEY IDENTITY(1,1),
    nombre NVARCHAR(100) NOT NULL,
    descripcion NVARCHAR(200)
);

CREATE TABLE Profesor (
    profesor_id INT PRIMARY KEY IDENTITY(1,1),
    nombre NVARCHAR(100) NOT NULL,
    apellido NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) UNIQUE,
    telefono NVARCHAR(20)
);

-- Tablas de relación muchos-a-muchos
CREATE TABLE Estudiante_Curso (
    estudiante_id INT REFERENCES Estudiante(estudiante_id),
    curso_id INT REFERENCES Curso(curso_id),
    PRIMARY KEY (estudiante_id, curso_id)
);

CREATE TABLE Profesor_Asignatura_Curso (
    profesor_id INT REFERENCES Profesor(profesor_id),
    asignatura_id INT REFERENCES Asignatura(asignatura_id),
    curso_id INT REFERENCES Curso(curso_id),
    PRIMARY KEY (profesor_id, asignatura_id, curso_id)
);

-- Evaluaciones y Calificaciones
CREATE TABLE Evaluacion (
    evaluacion_id INT PRIMARY KEY IDENTITY(1,1),
    asignatura_id INT REFERENCES Asignatura(asignatura_id),
    curso_id INT REFERENCES Curso(curso_id),
    profesor_id INT REFERENCES Profesor(profesor_id),
    tipo NVARCHAR(50) NOT NULL,
    fecha DATE NOT NULL,
    peso DECIMAL(5,2) CHECK (peso > 0 AND peso <= 100)
);

CREATE TABLE Calificacion (
    calificacion_id INT PRIMARY KEY IDENTITY(1,1),
    estudiante_id INT REFERENCES Estudiante(estudiante_id),
    evaluacion_id INT REFERENCES Evaluacion(evaluacion_id),
    valor DECIMAL(5,2) NOT NULL CHECK (valor >= 0 AND valor <= 100),
    fecha_registro DATETIME DEFAULT GETDATE()
);

-- Seguridad y Auditoría
CREATE TABLE Usuario (
    usuario_id INT PRIMARY KEY IDENTITY(1,1),
    profesor_id INT NULL REFERENCES Profesor(profesor_id),
    username NVARCHAR(50) UNIQUE NOT NULL,
    password_hash NVARCHAR(255) NOT NULL,
    rol NVARCHAR(20) NOT NULL CHECK (rol IN ('admin', 'profesor'))
);

CREATE TABLE Bitacora (
    bitacora_id INT PRIMARY KEY IDENTITY(1,1),
    usuario_id INT REFERENCES Usuario(usuario_id),
    accion NVARCHAR(100) NOT NULL,
    tabla_afectada NVARCHAR(50) NOT NULL,
    fecha DATETIME DEFAULT GETDATE(),
    detalles NVARCHAR(MAX) -- JSON con cambios anteriores/nuevos
);