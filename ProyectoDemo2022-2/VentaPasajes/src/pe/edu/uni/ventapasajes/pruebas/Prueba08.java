package pe.edu.uni.ventapasajes.pruebas;

import pe.edu.uni.ventapasajes.model.UsuarioModel;
import pe.edu.uni.ventapasajes.service.LogueoService;

/**
 *
 * @author Gustavo Coronel
 */
public class Prueba08 {

   public static void main(String[] args) {
      try {
         // Datos
         String usuario = "pepe";
         String clave = "nose";
         // Llamada al servicio
         LogueoService service = new LogueoService();
         UsuarioModel model = service.validar("pcastro", "cazador");
         if(model==null){
            System.err.println("Hay error, datos incorrectos.");
         } else {
            System.out.println("Todo ok.");
         }
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

}
