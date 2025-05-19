select * from curso;
go

select * from ALUMNO;
go

select * from MATRICULA;
go


declare @curso int, @alumno int;
set @curso = 4;
set @alumno = 9;
select * from MATRICULA where cur_id = @curso and alu_id = @alumno;
select * from PAGO where cur_id = @curso and alu_id = @alumno;
GO


select * from matricula;
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
select 
	M.cur_id, M.alu_id, A.alu_nombre, M.mat_tipo, 
	M.mat_fecha, M.mat_precio, M.mat_cuotas
from MATRICULA M
join ALUMNO A on M.alu_id = A.alu_id
where cur_id = @curso;
go




