select * from curso;
go

select * from ALUMNO;
go

select * from MATRICULA;
go


declare @curso int, @alumno int;
set @curso = 1;
set @alumno = 2;
select * from CURSO where cur_id = @curso;
select * from MATRICULA where cur_id = @curso and alu_id = @alumno;
select * from PAGO where cur_id = @curso and alu_id = @alumno;
GO


select * from matricula where cur_id=1;
go

/*
COLUMNAS:
- IDCURSO
- IDALUMNO
- NOMBRE DEL ALUMNO
- MATRICULA
- FECHA
- PRECIO
- CUOTAS
- CANCELADO
- DEUDA
- NOTA
*/



declare @curso int;
set @curso = 1;
with
-- Parte 1: Lista de matriculados
Parte1 as (
	select 
		M.cur_id, M.alu_id, A.alu_nombre, M.mat_tipo, 
		M.mat_fecha, M.mat_precio, M.mat_cuotas, M.mat_nota
	from MATRICULA M
	join ALUMNO A on M.alu_id = A.alu_id
	where cur_id = @curso
),
-- Parte 2: Suma de pagos realizados por alumno
Parte2 as (
	select alu_id, sum(pag_importe) cancelado
	from PAGO 
	where cur_id=@curso
	group by alu_id
)
select 
	P1.cur_id, P1.alu_id, P1.alu_nombre,
	CONVERT(varchar(20),P1.mat_fecha,103) fecha,
	P1.mat_precio, P1.mat_cuotas, 
	ISNULL(P2.cancelado,0) Cancelado,
	P1.mat_precio - ISNULL(P2.cancelado,0) Deuda,
	P1.mat_nota
from Parte1 P1
left join Parte2 P2 on P1.alu_id = P2.alu_id;
go

select count(1) cont
from MATRICULA
where cur_id = 1 and alu_id=3
go


select cur_vacantes - cur_matriculados cont
from CURSO where cur_id = 7;
go