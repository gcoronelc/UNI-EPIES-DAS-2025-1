-- =============================================
-- SISTEMA DE GESTIÓN DE CALIFICACIONES - VERSIÓN SIMPLIFICADA
-- Optimizado según recomendaciones: 
-- 1. Eliminada tabla AnioAcademico (usando solo PeriodoAcademico)
-- 2. Eliminada tabla AreaCurricular (asignaturas independientes)
-- =============================================

CREATE DATABASE ColegioABC_Simplificado;
GO

USE ColegioABC_Simplificado;
GO

-- =============================================
-- TABLAS MAESTRAS SIMPLIFICADAS
-- =============================================

-- Tabla Niveles Educativos
CREATE TABLE NivelEducativo (
    nivel_id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(20) NOT NULL CHECK (nombre IN ('Inicial', 'Primaria', 'Secundaria'))
);

-- Tabla Grados (1°-6° Primaria, 1°-5° Secundaria)
CREATE TABLE Grado (
    grado_id INT PRIMARY KEY IDENTITY(1,1),
    nivel_id INT NOT NULL REFERENCES NivelEducativo(nivel_id),
    numero INT NOT NULL
);

-- Tabla Secciones
CREATE TABLE Seccion (
    seccion_id INT PRIMARY KEY IDENTITY(1,1),
    letra CHAR(1) NOT NULL CHECK (letra BETWEEN 'A' AND 'Z')
);

-- =============================================
-- TABLAS DE PERSONAS
-- =============================================

CREATE TABLE Estudiante (
    estudiante_id INT PRIMARY KEY IDENTITY(1,1),
    codigo VARCHAR(10) UNIQUE NOT NULL,
    dni CHAR(8) UNIQUE NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    apellido_materno VARCHAR(50) NOT NULL,
    nombres VARCHAR(100) NOT NULL
);

CREATE TABLE Profesor (
    profesor_id INT PRIMARY KEY IDENTITY(1,1),
    codigo VARCHAR(10) UNIQUE NOT NULL,
    dni CHAR(8) UNIQUE NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    apellido_materno VARCHAR(50) NOT NULL,
    nombres VARCHAR(100) NOT NULL
);

-- =============================================
-- TABLAS ACADÉMICAS SIMPLIFICADAS
-- =============================================

-- Tabla Periodos Académicos (ahora incluye año)
CREATE TABLE PeriodoAcademico (
    periodo_id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(20) NOT NULL, -- Ej: "Trimestre I - 2023"
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    anio INT NOT NULL, -- Ej: 2023
    es_activo BIT DEFAULT 1
);

-- Tabla Asignaturas (sin área curricular)
CREATE TABLE Asignatura (
    asignatura_id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(50) NOT NULL UNIQUE -- Ej: "Matemática", "Comunicación"
);

-- Tabla Matrícula (vincula estudiante con grado/sección/periodo)
CREATE TABLE Matricula (
    matricula_id INT PRIMARY KEY IDENTITY(1,1),
    estudiante_id INT NOT NULL REFERENCES Estudiante(estudiante_id),
    grado_id INT NOT NULL REFERENCES Grado(grado_id),
    seccion_id INT NOT NULL REFERENCES Seccion(seccion_id),
    periodo_id INT NOT NULL REFERENCES PeriodoAcademico(periodo_id),
    CONSTRAINT UQ_Matricula UNIQUE (estudiante_id, periodo_id) -- 1 matrícula por periodo
);

-- Tabla Asignación de Profesores
CREATE TABLE AsignacionProfesor (
    asignacion_id INT PRIMARY KEY IDENTITY(1,1),
    profesor_id INT NOT NULL REFERENCES Profesor(profesor_id),
    asignatura_id INT NOT NULL REFERENCES Asignatura(asignatura_id),
    grado_id INT NOT NULL REFERENCES Grado(grado_id),
    seccion_id INT NOT NULL REFERENCES Seccion(seccion_id),
    periodo_id INT NOT NULL REFERENCES PeriodoAcademico(periodo_id)
);

-- =============================================
-- TABLAS DE EVALUACIÓN (MANTENIENDO ESCALA 0-20)
-- =============================================

CREATE TABLE TipoEvaluacion (
    tipo_id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(50) NOT NULL, -- Ej: "Examen Parcial"
    peso DECIMAL(5,2) NOT NULL CHECK (peso > 0 AND peso <= 100) -- % en el promedio
);

CREATE TABLE Evaluacion (
    evaluacion_id INT PRIMARY KEY IDENTITY(1,1),
    asignacion_id INT NOT NULL REFERENCES AsignacionProfesor(asignacion_id),
    tipo_id INT NOT NULL REFERENCES TipoEvaluacion(tipo_id),
    nombre VARCHAR(100) NOT NULL,
    fecha DATE NOT NULL
);

CREATE TABLE Calificacion (
    calificacion_id INT PRIMARY KEY IDENTITY(1,1),
    evaluacion_id INT NOT NULL REFERENCES Evaluacion(evaluacion_id),
    estudiante_id INT NOT NULL REFERENCES Estudiante(estudiante_id),
    valor DECIMAL(4,2) NOT NULL CHECK (valor >= 0 AND valor <= 20), -- Escala peruana
    CONSTRAINT UQ_Calificacion UNIQUE (evaluacion_id, estudiante_id) -- 1 nota por evaluación/estudiante
);


