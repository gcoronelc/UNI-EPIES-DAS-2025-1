
CREATE TABLE Evaluacion
( 
	evaluacion_id        int IDENTITY ( 1,1 ) ,
	nombre               varchar(100)  NOT NULL ,
	fecha                DATE  NOT NULL ,
	CONSTRAINT XPKEvaluacion PRIMARY KEY (evaluacion_id ASC)
)
go



CREATE TABLE Estudiante
( 
	estudiante_id        int IDENTITY ( 1,1 ) ,
	codigo               varchar(10)  NOT NULL ,
	dni                  char(8)  NOT NULL ,
	apellido_paterno     varchar(50)  NOT NULL ,
	apellido_materno     varchar(50)  NOT NULL ,
	nombres              varchar(100)  NOT NULL ,
	CONSTRAINT XPKEstudiante PRIMARY KEY (estudiante_id ASC)
)
go



CREATE TABLE NivelEducativo
( 
	nivel_id             int IDENTITY ( 1,1 ) ,
	nombre               varchar(20)  NOT NULL 
	CONSTRAINT Validation_Rule_173_1647134968
		CHECK  ( nombre IN ('Inicial' , 'Primaria' , 'Secundaria') ),
	CONSTRAINT XPKNivelEducativo PRIMARY KEY (nivel_id ASC)
)
go



CREATE TABLE Grado
( 
	grado_id             int IDENTITY ( 1,1 ) ,
	nivel_id             int  NOT NULL ,
	numero               int  NOT NULL ,
	CONSTRAINT XPKGrado PRIMARY KEY (grado_id ASC),
	CONSTRAINT R_1 FOREIGN KEY (nivel_id) REFERENCES NivelEducativo(nivel_id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
)
go



CREATE TABLE Seccion
( 
	seccion_id           int IDENTITY ( 1,1 ) ,
	letra                char(1)  NOT NULL 
	CONSTRAINT Validation_Rule_198_1242357727
		CHECK  ( letra BETWEEN 'A' AND 'Z' ),
	CONSTRAINT XPKSeccion PRIMARY KEY (seccion_id ASC)
)
go



CREATE TABLE PeriodoAcademico
( 
	periodo_id           int IDENTITY ( 1,1 ) ,
	nombre               varchar(20)  NOT NULL ,
	fecha_inicio         DATE  NOT NULL ,
	fecha_fin            DATE  NOT NULL ,
	anio                 int  NOT NULL ,
	es_activo            bit  NULL 
	CONSTRAINT Default_Value_223_80818469
		 DEFAULT  1,
	CONSTRAINT XPKPeriodoAcademico PRIMARY KEY (periodo_id ASC)
)
go



CREATE TABLE Matricula
( 
	matricula_id         int IDENTITY ( 1,1 ) ,
	estudiante_id        int  NOT NULL ,
	grado_id             int  NOT NULL ,
	seccion_id           int  NOT NULL ,
	periodo_id           int  NOT NULL ,
	CONSTRAINT XPKMatricula PRIMARY KEY (matricula_id ASC),
	CONSTRAINT R_2 FOREIGN KEY (estudiante_id) REFERENCES Estudiante(estudiante_id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION,
CONSTRAINT R_3 FOREIGN KEY (grado_id) REFERENCES Grado(grado_id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION,
CONSTRAINT R_4 FOREIGN KEY (seccion_id) REFERENCES Seccion(seccion_id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION,
CONSTRAINT R_5 FOREIGN KEY (periodo_id) REFERENCES PeriodoAcademico(periodo_id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
)
go



CREATE TABLE Asignatura
( 
	asignatura_id        int IDENTITY ( 1,1 ) ,
	nombre               varchar(50)  NOT NULL ,
	grado_id             int  NULL ,
	CONSTRAINT XPKAsignatura PRIMARY KEY (asignatura_id ASC),
	CONSTRAINT R_15 FOREIGN KEY (grado_id) REFERENCES Grado(grado_id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
)
go



CREATE TABLE Profesor
( 
	profesor_id          int IDENTITY ( 1,1 ) ,
	codigo               varchar(10)  NOT NULL ,
	dni                  char(8)  NOT NULL ,
	apellido_paterno     varchar(50)  NOT NULL ,
	apellido_materno     varchar(50)  NOT NULL ,
	nombres              varchar(100)  NOT NULL ,
	CONSTRAINT XPKProfesor PRIMARY KEY (profesor_id ASC)
)
go



CREATE TABLE Bloque
( 
	bloque_id            integer IDENTITY ( 1,1 ) ,
	asignatura_id        int  NULL ,
	seccion_id           int  NULL ,
	profesor_id          int  NULL ,
	CONSTRAINT XPKBloque PRIMARY KEY (bloque_id ASC),
	CONSTRAINT R_16 FOREIGN KEY (asignatura_id) REFERENCES Asignatura(asignatura_id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION,
CONSTRAINT R_17 FOREIGN KEY (seccion_id) REFERENCES Seccion(seccion_id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION,
CONSTRAINT R_18 FOREIGN KEY (profesor_id) REFERENCES Profesor(profesor_id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
)
go



CREATE TABLE Calificacion
( 
	calificacion_id      int IDENTITY ( 1,1 ) ,
	bloque_id            integer  NULL ,
	matricula_id         int  NULL ,
	evaluacion_id        int  NOT NULL ,
	nota                 decimal(4,2)  NOT NULL 
	CONSTRAINT Validation_Rule_397_525236880
		CHECK  ( valor >=    0 AND valor <=    20 ),
	CONSTRAINT XPKCalificacion PRIMARY KEY (calificacion_id ASC),
	CONSTRAINT R_13 FOREIGN KEY (evaluacion_id) REFERENCES Evaluacion(evaluacion_id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION,
CONSTRAINT R_19 FOREIGN KEY (matricula_id) REFERENCES Matricula(matricula_id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION,
CONSTRAINT R_20 FOREIGN KEY (bloque_id) REFERENCES Bloque(bloque_id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
)
go
