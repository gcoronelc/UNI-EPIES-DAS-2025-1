package pe.edu.uni.solucioneseduca.prueba;

import pe.edu.uni.solucioneseduca.dto.MatriculadoDto;
import pe.edu.uni.solucioneseduca.service.ProcesosSeervice;

public class PruebaMatricula01Empleado {

	public static void main(String[] args) {
		try {
			// Datos
			MatriculadoDto bean = new MatriculadoDto();
			bean.setIdEmpleado(333); // No pasa
			// Proceso
			ProcesosSeervice procesosSeervice;
			procesosSeervice = new ProcesosSeervice();
			procesosSeervice.procMatricula(bean);
			// Reporte
			System.out.println("Matricula ok.");
		} catch (Exception e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}
	
}
