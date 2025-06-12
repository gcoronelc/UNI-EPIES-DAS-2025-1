package pe.edu.uni.ventapasajes.controller;

import pe.edu.uni.ventapasajes.model.RutaModel;
import pe.edu.uni.ventapasajes.service.SistemaService;

/**
 *
 * @author Gustavo Coronel
 */
public class SistemaController {

   private SistemaService sistemaService;

   public SistemaController() {
      sistemaService = new SistemaService();
   }

   public Integer creacionRuta(RutaModel model) {
      return sistemaService.creacionRuta(model);
   }
}
