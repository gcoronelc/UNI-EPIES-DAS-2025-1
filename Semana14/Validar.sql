select * from ALUMNO;
go

-- Para el empleado
select emp_id id, 
CONCAT(emp_nombre,' ',emp_apellido) nombre,
emp_usuario usuario
from empleado
where emp_usuario='eaguero' and emp_clave='cazador';
go

-- Para el alumno
select alu_id id, alu_nombre nombre, alu_email usuario
from ALUMNO
where alu_email='yesenia@hotmail.com' and alu_telefono='986412345';
go



