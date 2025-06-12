package pe.edu.uni.ventapasajes.pruebas;

import pe.edu.uni.ventapasajes.model.ClienteModel;
import pe.edu.uni.ventapasajes.service.CrudClienteImpl;

/**
 *
 * @author Gustavo Coronel
 */
public class Prueba03 {

   public static void main(String[] args) {
      try {
         CrudClienteImpl crudCliente = new CrudClienteImpl();
         ClienteModel cliente = crudCliente.findById(10);
         System.out.println(cliente);
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

}
