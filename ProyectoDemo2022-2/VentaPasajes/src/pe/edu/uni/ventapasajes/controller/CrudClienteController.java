package pe.edu.uni.ventapasajes.controller;

import java.util.List;
import pe.edu.uni.ventapasajes.model.ClienteModel;
import pe.edu.uni.ventapasajes.service.CrudClienteImpl;

/**
 *
 * @author Gustavo Coronel
 */
public class CrudClienteController {

   private CrudClienteImpl crudService;

   public CrudClienteController() {
      crudService = new CrudClienteImpl();
   }

   public ClienteModel findById(Integer id) {
      return crudService.findById(id);
   }

   public List<ClienteModel> findAll() {
      return crudService.findAll();
   }

   public List<ClienteModel> find(ClienteModel model) {
      return crudService.find(model);
   }

   public void insert(ClienteModel model) {
      crudService.insert(model);
   }

   public void update(ClienteModel model) {
      crudService.update(model);
   }

   public void delete(Integer id) {
      crudService.delete(id);
   }
}
