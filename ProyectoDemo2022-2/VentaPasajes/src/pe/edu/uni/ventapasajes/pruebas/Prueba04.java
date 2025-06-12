package pe.edu.uni.ventapasajes.pruebas;

import java.util.List;
import pe.edu.uni.ventapasajes.model.ClienteModel;
import pe.edu.uni.ventapasajes.service.CrudClienteImpl;

/**
 *
 * @author Gustavo Coronel
 */
public class Prueba04 {

   public static void main(String[] args) {
      try {
         ClienteModel model = new ClienteModel();
         model.setId(10);
         model.setApellido("ta");
         CrudClienteImpl crudCliente = new CrudClienteImpl();
         List<ClienteModel> lista = crudCliente.find(model);
         for (ClienteModel cliente : lista) {
            System.out.println(cliente);
         }
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

}
