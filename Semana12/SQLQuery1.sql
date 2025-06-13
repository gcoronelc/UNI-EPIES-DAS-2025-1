select
	cur_id id, cur_nombre  + ' ('
	+ cast(cur_vacantes - cur_matriculados as varchar(10)) 
	+ ')' nombre,
	cast(cur_precio as varchar(20)) precio
from CURSO
where cur_vacantes - cur_matriculados > 0
order by 2;
go

select alu_id id, alu_nombre nombre
from ALUMNO
where alu_id not in 
(select alu_id from MATRICULA
where cur_id = 0)
order by 2
go







