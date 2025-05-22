package pe.edu.uni.solucioneseduca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 * @cursos gcoronelc.github.io
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MatriculadoDto {
	
	private int idCurso;
	private int idAlumno;
	private String tipoMatricula;
	private String fechaMatricula;
	private double precio;
	private int cuotas;
	private double cancelado;
	private double deuda;
	private int nota;
	
}
