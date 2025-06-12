package pe.edu.uni.solucioneseduca.controller;

import pe.edu.uni.solucioneseduca.dto.MatriculadoDto;
import pe.edu.uni.solucioneseduca.service.ProcesosSeervice;

public class ProcesosController {
	
	private ProcesosSeervice procesosSeervice;

	public ProcesosController() {
		procesosSeervice = new ProcesosSeervice();
	}
		
	public MatriculadoDto procMatricula(MatriculadoDto bean){
		return procesosSeervice.procMatricula(bean);
	}
	
}
