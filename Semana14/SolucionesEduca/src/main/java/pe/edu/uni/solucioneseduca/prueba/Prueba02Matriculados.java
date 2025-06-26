package pe.edu.uni.solucioneseduca.prueba;

import java.util.List;
import pe.edu.uni.solucioneseduca.dto.MatriculadoDto;
import pe.edu.uni.solucioneseduca.service.ConsultasService;

/**
 *
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 * @cursos gcoronelc.github.io
 */
public class Prueba02Matriculados {
	
	public static void main(String[] args) {
		
		try {
			// Datos
			int idCurso = 1;
			// Proceso
			ConsultasService  service = new ConsultasService();
			List<MatriculadoDto> lista = service.consultarMatriculados(idCurso);
			// Reporte
			if(lista != null)
			for (MatriculadoDto dto : lista) {
				System.out.println(dto);
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		
	}

}
