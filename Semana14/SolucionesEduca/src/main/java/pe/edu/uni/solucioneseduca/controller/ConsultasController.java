package pe.edu.uni.solucioneseduca.controller;

import java.util.List;
import pe.edu.uni.solucioneseduca.dto.MatriculadoDto;
import pe.edu.uni.solucioneseduca.service.ConsultasService;

public class ConsultasController {

    private ConsultasService consultasService;

    public ConsultasController() {
        consultasService = new ConsultasService();
    }

    public List<MatriculadoDto> consultarMatriculados(int idCurso) {
        return consultasService.consultarMatriculados(idCurso);
    }

}
