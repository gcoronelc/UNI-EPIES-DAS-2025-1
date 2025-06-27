/*
	private int idCurso;
	private int idAlumno;
	private int idEmpleado;
	private String tipoMatricula;
	private String fechaMatricula;
	private double precio;
	private int cuotas;
	private double cancelado;
	private double deuda;
	private int nota;
*/

declare @idCurso int;
set @idCurso = 1;
with
v1 as (
	select alu_id, SUM(pag_importe) cancelado
	from PAGO
	where cur_id = @idCurso
	group by alu_id
)
select m.cur_id idCurso, m.alu_id idAlumno,
a.alu_nombre nombre,
emp_id idEmpleado, m.mat_tipo tipoMatricula,
m.mat_precio precio, m.mat_cuotas cuotas,
ISNULL(v1.cancelado,0.0) cancelado,
m.mat_precio - ISNULL(v1.cancelado,0.0) deuda,
ISNULL(m.mat_nota,0) nota
from MATRICULA m
join ALUMNO a on m.alu_id = a.alu_id
left join v1 on m.alu_id = v1.alu_id
where cur_id = @idCurso
go

select * from EMPLEADO;
go

