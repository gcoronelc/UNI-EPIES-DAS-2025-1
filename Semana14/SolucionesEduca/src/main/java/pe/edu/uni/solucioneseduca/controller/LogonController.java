
package pe.edu.uni.solucioneseduca.controller;

import pe.edu.uni.solucioneseduca.dto.UsuarioDto;
import pe.edu.uni.solucioneseduca.service.LogonService;


public class LogonController {
	
	private LogonService logonService;

	public LogonController() {
		logonService = new LogonService();
	}
	
	public void validar(String tipo, String usuario, String clave) {
		UsuarioDto bean = logonService.validar(tipo, usuario, clave);
	}
	
}
