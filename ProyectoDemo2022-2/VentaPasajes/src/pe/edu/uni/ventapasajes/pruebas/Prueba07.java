package pe.edu.uni.ventapasajes.pruebas;

import pe.edu.uni.ventapasajes.service.CrudClienteImpl;

/**
 *
 * @author Gustavo Coronel
 */
public class Prueba07 {

   public static void main(String[] args) {
      try {
         CrudClienteImpl crudCliente = new CrudClienteImpl();
         crudCliente.delete(19);
         System.out.println("Registro eliminado.");
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

}
