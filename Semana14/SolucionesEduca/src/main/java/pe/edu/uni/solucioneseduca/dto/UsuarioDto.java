package pe.edu.uni.solucioneseduca.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {
	
	private int id;
	private String nombre;
	private String usuario;
	
}
