package pe.edu.uni.solucioneseduca.prueba;

import java.util.List;
import pe.edu.uni.solucioneseduca.dto.ComboDto;
import pe.edu.uni.solucioneseduca.dto.MatriculadoDto;
import pe.edu.uni.solucioneseduca.service.ComboService;
import pe.edu.uni.solucioneseduca.service.ProcesosSeervice;

public class PruebaComboCursos {

    public static void main(String[] args) {
        try {
            // Proceso
            ComboService comboService;
            comboService = new ComboService();
            List<ComboDto> lista = comboService.getCursos();
            // Reporte
            for (ComboDto dto : lista) {
                System.out.println(dto);
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

}
