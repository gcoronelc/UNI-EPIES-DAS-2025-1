package pe.edu.uni.solucioneseduca.prueba;

import pe.edu.uni.solucioneseduca.dto.UsuarioDto;
import pe.edu.uni.solucioneseduca.service.LogonService;

public class PruebaValidar1 {

	public static void main(String[] args) {
		
		try {
			// Datos: Empleado
			String tipo = "empleado";
			String usuario = "eaguero";
			String clave = "cazador";
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
