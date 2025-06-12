package pe.edu.uni.ventapasajes.pruebas;

import java.util.List;
import pe.edu.uni.ventapasajes.model.ClienteModel;
import pe.edu.uni.ventapasajes.service.CrudClienteImpl;
import pe.edu.uni.ventapasajes.service.CrudServiceSpec;

/**
 *
 * @author Gustavo Coronel
 */
public class Prueba02 {
    
    public static void main(String[] args) {
        try {
            CrudServiceSpec crudCliente = new CrudClienteImpl();
            List<ClienteModel> lista = crudCliente.findAll();
            for (ClienteModel model : lista) {
                System.out.println(model);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
}
