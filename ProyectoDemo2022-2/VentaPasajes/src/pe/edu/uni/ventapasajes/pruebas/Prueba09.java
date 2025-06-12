package pe.edu.uni.ventapasajes.pruebas;

import pe.edu.uni.ventapasajes.model.RutaModel;
import pe.edu.uni.ventapasajes.service.SistemaService;

/**
 *
 * @author Gustavo Coronel
 */
public class Prueba09 {

   public static void main(String[] args) {
      try {
         // Datos
         RutaModel model = new RutaModel();
         model.setNombre("LIMA - CHICLAYO");
         model.setOrigen(1);
         model.setDestino(3);
         model.setDuracion("18:00 Horas");
         model.setPrecio1(150.0);
         model.setPrecio2(170.0);
         model.setPrecio3(190.0);
         model.setEmpleado(1);
         // Llamada al servicio
         SistemaService service = new SistemaService();
         service.creacionRuta(model);
         System.out.println("Id de nueva ruta: " + model.getId());
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

}
