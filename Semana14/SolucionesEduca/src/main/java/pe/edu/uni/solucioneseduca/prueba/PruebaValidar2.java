package pe.edu.uni.solucioneseduca.prueba;

import pe.edu.uni.solucioneseduca.dto.UsuarioDto;
import pe.edu.uni.solucioneseduca.service.LogonService;

public class PruebaValidar2 {

	public static void main(String[] args) {
		
		try {
			// Datos: Alumno
			String tipo = "alumno";
			String usuario = "yesenia@hotmail.com";
			String clave = "986412345";
			// Proceso
			LogonService service = new LogonService();
			UsuarioDto bean = service.validar(tipo, usuario, clave);
			// Reporte
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		
	}
	
}
