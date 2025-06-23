package pe.edu.uni.solucioneseduca.prueba;

import pe.edu.uni.solucioneseduca.dto.MatriculadoDto;
import pe.edu.uni.solucioneseduca.service.ProcesosSeervice;

public class PruebaMatricula10MatriculaOk {

	public static void main(String[] args) {
		try {
			// Datos
			MatriculadoDto bean = new MatriculadoDto();
			bean.setIdEmpleado(3); // Pasa
			bean.setIdAlumno(8); //  Pasa
			bean.setIdCurso(1); //  pasa
			bean.setTipoMatricula("REGULAR"); // pasa
			bean.setPrecio(10000.0);
			bean.setCuotas(20);
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
