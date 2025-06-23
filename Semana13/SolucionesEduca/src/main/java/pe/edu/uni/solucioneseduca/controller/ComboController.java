package pe.edu.uni.solucioneseduca.controller;

import java.util.List;
import pe.edu.uni.solucioneseduca.dto.ComboDto;
import pe.edu.uni.solucioneseduca.service.ComboService;

public class ComboController {
    
    private ComboService comboService;

    public ComboController() {
        comboService = new ComboService();
    }
    
    public List<ComboDto> getCursos() {
        return comboService.getCursos();
    }
    
     public List<ComboDto> getAlumnos(int idCurso) {
         return comboService.getAlumnos(idCurso);
     }
}
