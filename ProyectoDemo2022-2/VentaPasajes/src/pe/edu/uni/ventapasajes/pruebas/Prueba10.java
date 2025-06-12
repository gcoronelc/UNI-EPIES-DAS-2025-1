package pe.edu.uni.ventapasajes.pruebas;

import java.util.List;
import pe.edu.uni.ventapasajes.model.ComboModel;
import pe.edu.uni.ventapasajes.service.ConsultaService;

/**
 *
 * @author Gustavo Coronel
 */
public class Prueba10 {

   public static void main(String[] args) {
      try {
         // Datos
         List<ComboModel> lista;
         // Llamada al servicio
         ConsultaService consultaService = new ConsultaService();
         lista = consultaService.leerCiudades();
         for (ComboModel model : lista) {
            System.out.println(model);
         }
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

}
