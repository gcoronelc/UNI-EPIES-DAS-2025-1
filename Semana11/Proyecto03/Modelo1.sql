CREATE TABLE Rol (
    RolID INT PRIMARY KEY,
    NombreRol VARCHAR(50) NOT NULL
);

CREATE TABLE Usuario (
    UsuarioID INT PRIMARY KEY,
    Username VARCHAR(50) NOT NULL UNIQUE,
    PasswordHash VARCHAR(256) NOT NULL,
    RolID INT NOT NULL,
    FOREIGN KEY (RolID) REFERENCES Rol(RolID)
);

CREATE TABLE Estudiante (
    EstudianteID INT PRIMARY KEY,
    Nombre VARCHAR(100),
    Apellido VARCHAR(100),
    DNI VARCHAR(20),
    FechaNacimiento DATE,
    Genero CHAR(1),
    Email VARCHAR(100)
);

CREATE TABLE Profesor (
    ProfesorID INT PRIMARY KEY,
    Nombre VARCHAR(100),
    Apellido VARCHAR(100),
    Email VARCHAR(100),
    Especialidad VARCHAR(100)
);

CREATE TABLE Curso (
    CursoID INT PRIMARY KEY,
    Nombre VARCHAR(100),
    Grado VARCHAR(50),
    Seccion CHAR(1),
    PeriodoAcademico VARCHAR(20)
);

CREATE TABLE Asignatura (
    AsignaturaID INT PRIMARY KEY,
    Nombre VARCHAR(100),
    Descripcion TEXT
);

CREATE TABLE Clase (
    ClaseID INT PRIMARY KEY,
    CursoID INT,
    AsignaturaID INT,
    ProfesorID INT,
    FOREIGN KEY (CursoID) REFERENCES Curso(CursoID),
    FOREIGN KEY (AsignaturaID) REFERENCES Asignatura(AsignaturaID),
    FOREIGN KEY (ProfesorID) REFERENCES Profesor(ProfesorID)
);

CREATE TABLE EstudianteCurso (
    EstudianteID INT,
    CursoID INT,
    AñoLectivo VARCHAR(20),
    PRIMARY KEY (EstudianteID, CursoID, AñoLectivo),
    FOREIGN KEY (EstudianteID) REFERENCES Estudiante(EstudianteID),
    FOREIGN KEY (CursoID) REFERENCES Curso(CursoID)
);

CREATE TABLE Evaluacion (
    EvaluacionID INT PRIMARY KEY,
    ClaseID INT,
    Nombre VARCHAR(100),
    Tipo VARCHAR(50),
    Fecha DATE,
    Peso DECIMAL(5,2),
    FOREIGN KEY (ClaseID) REFERENCES Clase(ClaseID)
);

CREATE TABLE Calificacion (
    CalificacionID INT PRIMARY KEY,
    EvaluacionID INT,
    EstudianteID INT,
    Nota DECIMAL(5,2),
    FOREIGN KEY (EvaluacionID) REFERENCES Evaluacion(EvaluacionID),
    FOREIGN KEY (EstudianteID) REFERENCES Estudiante(EstudianteID)
);

CREATE TABLE BitacoraCambios (
    CambioID INT PRIMARY KEY,
    CalificacionID INT,
    UsuarioID INT,
    FechaHora DATETIME,
    NotaAnterior DECIMAL(5,2),
    NotaNueva DECIMAL(5,2),
    Motivo TEXT,
    FOREIGN KEY (CalificacionID) REFERENCES Calificacion(CalificacionID),
    FOREIGN KEY (UsuarioID) REFERENCES Usuario(UsuarioID)
);
