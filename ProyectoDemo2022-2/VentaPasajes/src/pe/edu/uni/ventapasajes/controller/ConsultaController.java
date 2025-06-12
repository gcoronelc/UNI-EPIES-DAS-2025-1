package pe.edu.uni.ventapasajes.controller;

import java.util.List;
import pe.edu.uni.ventapasajes.model.ComboModel;
import pe.edu.uni.ventapasajes.service.ConsultaService;

/**
 *
 * @author Gustavo Coronel
 */
public class ConsultaController {

   private ConsultaService consultaService;

   public ConsultaController() {
      consultaService = new ConsultaService();
   }

   public List<ComboModel> leerCiudades() {
      return consultaService.leerCiudades();
   }
   
   public List<ComboModel> leerCiudades(int origen) {
      return consultaService.leerCiudades(origen);
   }

}
