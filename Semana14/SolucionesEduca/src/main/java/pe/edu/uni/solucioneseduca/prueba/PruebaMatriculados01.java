package pe.edu.uni.solucioneseduca.prueba;

import java.util.List;
import pe.edu.uni.solucioneseduca.dto.MatriculadoDto;
import pe.edu.uni.solucioneseduca.service.ConsultasService;

public class PruebaMatriculados01 {

    public static void main(String[] args) {
        try {
            // Datos
            int idCurso = 1;
            // Proceso
            ConsultasService service = new ConsultasService();
            List<MatriculadoDto> lista =service.consultarMatriculados(idCurso);
            // Reporte
            for (MatriculadoDto dto : lista) {
                System.out.println(dto);
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

}
