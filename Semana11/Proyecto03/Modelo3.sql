-- =============================================
-- SISTEMA DE GESTIÓN DE CALIFICACIONES ESCOLARES
-- Diseñado para el contexto educativo peruano
-- Cumple con normativas MINEDU y estructura académica local
-- =============================================

-- Crear la base de datos
CREATE DATABASE GestorEscolar_Peru;
GO

USE GestorEscolar_Peru;
GO

-- =============================================
-- TABLAS MAESTRAS
-- =============================================

-- Tabla Niveles Educativos (Inicial, Primaria, Secundaria)
CREATE TABLE NivelEducativo (
    nivel_id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(20) NOT NULL CHECK (nombre IN ('Inicial', 'Primaria', 'Secundaria')),
    descripcion VARCHAR(100)
);
go

-- Tabla Grados (1°-6° Primaria, 1°-5° Secundaria)
CREATE TABLE Grado (
    grado_id INT PRIMARY KEY IDENTITY(1,1),
    nivel_id INT NOT NULL REFERENCES NivelEducativo(nivel_id),
    numero INT NOT NULL ,
    descripcion VARCHAR(100)
);
go

-- Tabla Secciones (A, B, C...)
CREATE TABLE Seccion (
    seccion_id INT PRIMARY KEY IDENTITY(1,1),
    letra CHAR(1) NOT NULL CHECK (letra BETWEEN 'A' AND 'Z'),
    descripcion VARCHAR(100)
);
go

-- Tabla Áreas Curriculares (Según diseño curricular peruano)
CREATE TABLE AreaCurricular (
    area_id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(50) NOT NULL, -- Ej: "Matemática", "Comunicación"
    descripcion VARCHAR(100)
);
go

-- =============================================
-- TABLAS DE PERSONAS
-- =============================================

-- Tabla Estudiantes
CREATE TABLE Estudiante (
    estudiante_id INT PRIMARY KEY IDENTITY(1,1),
    codigo VARCHAR(10) UNIQUE NOT NULL, -- Ej: "EST-2023-001"
    dni CHAR(8) UNIQUE NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    apellido_materno VARCHAR(50) NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    genero CHAR(1) CHECK (genero IN ('M', 'F')),
    direccion VARCHAR(100),
    telefono VARCHAR(15),
    estado BIT DEFAULT 1 -- 1=Activo, 0=Inactivo/Retirado
);
go

-- Tabla Profesores
CREATE TABLE Profesor (
    profesor_id INT PRIMARY KEY IDENTITY(1,1),
    codigo VARCHAR(10) UNIQUE NOT NULL, -- Ej: "PROF-2023-001"
    dni CHAR(8) UNIQUE NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    apellido_materno VARCHAR(50) NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    especialidad VARCHAR(50),
    telefono VARCHAR(15),
    email VARCHAR(100),
    estado BIT DEFAULT 1 -- 1=Activo, 0=Inactivo
);
go

-- =============================================
-- TABLAS ACADÉMICAS
-- =============================================

-- Tabla Año Académico
CREATE TABLE AnioAcademico (
    anio_id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(20) NOT NULL, -- Ej: "2023"
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado BIT DEFAULT 1 -- 1=Activo, 0=Cerrado
);
go

-- Tabla Periodos Académicos (Trimestres/Bimestres)
CREATE TABLE PeriodoAcademico (
    periodo_id INT PRIMARY KEY IDENTITY(1,1),
    anio_id INT NOT NULL REFERENCES AnioAcademico(anio_id),
    nombre VARCHAR(20) NOT NULL, -- Ej: "Trimestre I"
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    orden INT NOT NULL CHECK (orden BETWEEN 1 AND 4),
    estado BIT DEFAULT 1 -- 1=Activo, 0=Cerrado
);
go

-- Tabla Matrícula (Relación Estudiante-Grado-Sección-Año)
CREATE TABLE Matricula (
    matricula_id INT PRIMARY KEY IDENTITY(1,1),
    estudiante_id INT NOT NULL REFERENCES Estudiante(estudiante_id),
    grado_id INT NOT NULL REFERENCES Grado(grado_id),
    seccion_id INT NOT NULL REFERENCES Seccion(seccion_id),
    anio_id INT NOT NULL REFERENCES AnioAcademico(anio_id),
    fecha_matricula DATE DEFAULT GETDATE(),
    estado BIT DEFAULT 1, -- 1=Activo, 0=Retirado
    CONSTRAINT UQ_Matricula UNIQUE (estudiante_id, anio_id) -- Un estudiante solo una matrícula por año
);
go

-- Tabla Asignaturas
CREATE TABLE Asignatura (
    asignatura_id INT PRIMARY KEY IDENTITY(1,1),
    area_id INT NOT NULL REFERENCES AreaCurricular(area_id),
    nombre VARCHAR(50) NOT NULL, -- Ej: "Álgebra", "Literatura"
    horas_semanales INT,
    descripcion VARCHAR(100)
);
go

-- Tabla Asignación de Profesores
CREATE TABLE AsignacionProfesor (
    asignacion_id INT PRIMARY KEY IDENTITY(1,1),
    profesor_id INT NOT NULL REFERENCES Profesor(profesor_id),
    asignatura_id INT NOT NULL REFERENCES Asignatura(asignatura_id),
    grado_id INT NOT NULL REFERENCES Grado(grado_id),
    seccion_id INT NOT NULL REFERENCES Seccion(seccion_id),
    anio_id INT NOT NULL REFERENCES AnioAcademico(anio_id),
    fecha_asignacion DATE DEFAULT GETDATE(),
    CONSTRAINT UQ_Asignacion UNIQUE (asignatura_id, grado_id, seccion_id, anio_id) -- Evitar duplicados
);
go

-- =============================================
-- TABLAS DE EVALUACIÓN
-- =============================================

-- Tabla Tipo de Evaluación
CREATE TABLE TipoEvaluacion (
    tipo_id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(50) NOT NULL, -- Ej: "Examen Parcial", "Tarea"
    descripcion VARCHAR(100),
    peso DECIMAL(5,2) NOT NULL CHECK (peso > 0 AND peso <= 100) -- % en el promedio
);
go

-- Tabla Evaluaciones
CREATE TABLE Evaluacion (
    evaluacion_id INT PRIMARY KEY IDENTITY(1,1),
    asignacion_id INT NOT NULL REFERENCES AsignacionProfesor(asignacion_id),
    tipo_id INT NOT NULL REFERENCES TipoEvaluacion(tipo_id),
    nombre VARCHAR(100) NOT NULL, -- Ej: "Examen Parcial de Matemáticas - Trimestre I"
    fecha DATE NOT NULL,
    descripcion VARCHAR(200),
    es_oficial BIT DEFAULT 1 -- 1=Va al acta oficial
);
go

-- Tabla Calificaciones (Escala 0-20)
CREATE TABLE Calificacion (
    calificacion_id INT PRIMARY KEY IDENTITY(1,1),
    evaluacion_id INT NOT NULL REFERENCES Evaluacion(evaluacion_id),
    estudiante_id INT NOT NULL REFERENCES Estudiante(estudiante_id),
    valor DECIMAL(4,2) NOT NULL CHECK (valor >= 0 AND valor <= 20),
    fecha_registro DATETIME DEFAULT GETDATE(),
    observaciones VARCHAR(200),
    CONSTRAINT UQ_Calificacion UNIQUE (evaluacion_id, estudiante_id) -- Un estudiante una nota por evaluación
);
go

-- Tabla Actas Oficiales (MINEDU)
CREATE TABLE ActaEvaluacion (
    acta_id INT PRIMARY KEY IDENTITY(1,1),
    asignacion_id INT NOT NULL REFERENCES AsignacionProfesor(asignacion_id),
    periodo_id INT NOT NULL REFERENCES PeriodoAcademico(periodo_id),
    fecha_creacion DATE DEFAULT GETDATE(),
    estado VARCHAR(20) DEFAULT 'Pendiente' CHECK (estado IN ('Pendiente', 'Aprobada', 'Rechazada')),
    observaciones VARCHAR(200)
);
go

-- =============================================
-- TABLAS DE USUARIOS Y SEGURIDAD
-- =============================================

-- Tabla Roles
CREATE TABLE Rol (
    rol_id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(30) NOT NULL, -- Ej: "Administrador", "Director", "Profesor"
    descripcion VARCHAR(100)
);
go

-- Tabla Usuarios
CREATE TABLE Usuario (
    usuario_id INT PRIMARY KEY IDENTITY(1,1),
    profesor_id INT NULL REFERENCES Profesor(profesor_id),
    username VARCHAR(30) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    rol_id INT NOT NULL REFERENCES Rol(rol_id),
    estado BIT DEFAULT 1 -- 1=Activo, 0=Inactivo
);
go

-- Tabla Bitácora (Auditoría)
CREATE TABLE Bitacora (
    bitacora_id INT PRIMARY KEY IDENTITY(1,1),
    usuario_id INT NOT NULL REFERENCES Usuario(usuario_id),
    accion VARCHAR(50) NOT NULL, -- Ej: "Actualizar calificación"
    tabla_afectada VARCHAR(50) NOT NULL,
    fecha DATETIME DEFAULT GETDATE(),
    detalles VARCHAR(500) -- Cambios realizados
);
go

-- =============================================
-- VISTAS PARA REPORTES
-- =============================================

-- Vista: Boletín de Notas por Estudiante
CREATE VIEW VW_BoletinNotas AS
SELECT 
    e.estudiante_id,
    e.codigo,
    e.apellido_paterno + ' ' + e.apellido_materno + ', ' + e.nombres AS estudiante,
    g.numero AS grado_numero,
    n.nombre AS nivel_educativo,
    s.letra AS seccion,
    a.nombre AS asignatura,
    t.nombre AS tipo_evaluacion,
    c.valor AS nota,
    t.peso,
    p.nombre AS periodo,
    ay.nombre AS anio_academico
FROM Calificacion c
JOIN Evaluacion ev ON c.evaluacion_id = ev.evaluacion_id
JOIN Estudiante e ON c.estudiante_id = e.estudiante_id
JOIN AsignacionProfesor ap ON ev.asignacion_id = ap.asignacion_id
JOIN Asignatura a ON ap.asignatura_id = a.asignatura_id
JOIN Grado g ON ap.grado_id = g.grado_id
JOIN NivelEducativo n ON g.nivel_id = n.nivel_id
JOIN Seccion s ON ap.seccion_id = s.seccion_id
JOIN TipoEvaluacion t ON ev.tipo_id = t.tipo_id
JOIN PeriodoAcademico p ON ap.anio_id = p.anio_id
JOIN AnioAcademico ay ON ap.anio_id = ay.anio_id;
go

-- Vista: Promedios Finales por Asignatura
CREATE VIEW VW_PromediosFinales AS
SELECT 
    m.estudiante_id,
    m.anio_id,
    ap.asignatura_id,
    p.periodo_id,
    SUM(c.valor * t.peso / 100) AS promedio_ponderado
FROM Matricula m
JOIN AsignacionProfesor ap ON m.grado_id = ap.grado_id AND m.seccion_id = ap.seccion_id AND m.anio_id = ap.anio_id
JOIN Evaluacion ev ON ap.asignacion_id = ev.asignacion_id
JOIN Calificacion c ON ev.evaluacion_id = c.evaluacion_id AND c.estudiante_id = m.estudiante_id
JOIN TipoEvaluacion t ON ev.tipo_id = t.tipo_id
JOIN PeriodoAcademico p ON ap.anio_id = p.anio_id
GROUP BY m.estudiante_id, m.anio_id, ap.asignatura_id, p.periodo_id;
go

-- =============================================
-- PROCEDIMIENTOS ALMACENADOS
-- =============================================

-- SP: Registrar nueva matrícula
CREATE PROCEDURE SP_RegistrarMatricula
    @estudiante_id INT,
    @grado_id INT,
    @seccion_id INT,
    @anio_id INT
AS
BEGIN
    INSERT INTO Matricula (estudiante_id, grado_id, seccion_id, anio_id)
    VALUES (@estudiante_id, @grado_id, @seccion_id, @anio_id);
END;
GO

-- SP: Registrar calificación
CREATE PROCEDURE SP_RegistrarCalificacion
    @evaluacion_id INT,
    @estudiante_id INT,
    @valor DECIMAL(4,2),
    @observaciones VARCHAR(200) = NULL
AS
BEGIN
    IF @valor < 0 OR @valor > 20
        RAISERROR('La calificación debe estar entre 0 y 20', 16, 1);
    ELSE
        INSERT INTO Calificacion (evaluacion_id, estudiante_id, valor, observaciones)
        VALUES (@evaluacion_id, @estudiante_id, @valor, @observaciones);
END;
GO

-- =============================================
-- ÍNDICES PARA OPTIMIZACIÓN
-- =============================================

-- Índices para estudiantes
CREATE INDEX IX_Estudiante_Codigo ON Estudiante(codigo);
CREATE INDEX IX_Estudiante_DNI ON Estudiante(dni);
go

-- Índices para calificaciones
CREATE INDEX IX_Calificacion_Estudiante ON Calificacion(estudiante_id);
CREATE INDEX IX_Calificacion_Evaluacion ON Calificacion(evaluacion_id);
go

-- Índices para matrículas
CREATE INDEX IX_Matricula_Anio ON Matricula(anio_id);
CREATE INDEX IX_Matricula_GradoSeccion ON Matricula(grado_id, seccion_id);
go

-- =============================================
-- DATOS INICIALES (EJEMPLO)
-- =============================================

-- Insertar niveles educativos
INSERT INTO NivelEducativo (nombre, descripcion) VALUES 
('Inicial', 'Educación Inicial'),
('Primaria', 'Educación Primaria'),
('Secundaria', 'Educación Secundaria');
go

-- Insertar grados (1°-6° Primaria, 1°-5° Secundaria)
-- Primaria
INSERT INTO Grado (nivel_id, numero, descripcion) 
SELECT nivel_id, numero, CONCAT(numero, '° de Primaria') 
FROM (VALUES (1),(2),(3),(4),(5),(6)) AS nums(numero)
CROSS JOIN NivelEducativo WHERE nombre = 'Primaria';
go

-- Secundaria
INSERT INTO Grado (nivel_id, numero, descripcion) 
SELECT nivel_id, numero, CONCAT(numero, '° de Secundaria') 
FROM (VALUES (1),(2),(3),(4),(5)) AS nums(numero)
CROSS JOIN NivelEducativo WHERE nombre = 'Secundaria';
go

-- Insertar secciones
INSERT INTO Seccion (letra, descripcion) VALUES 
('A', 'Sección A'), ('B', 'Sección B'), ('C', 'Sección C');
go

-- Insertar roles
INSERT INTO Rol (nombre, descripcion) VALUES 
('Administrador', 'Acceso total al sistema'),
('Director', 'Puede ver reportes globales'),
('Profesor', 'Puede registrar y modificar calificaciones');
go

-- =============================================
-- FIN DEL SCRIPT
-- =============================================