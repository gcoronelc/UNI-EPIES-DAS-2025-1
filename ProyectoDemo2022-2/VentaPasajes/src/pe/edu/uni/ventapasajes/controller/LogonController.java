/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.ventapasajes.controller;

import pe.edu.uni.ventapasajes.model.UsuarioModel;
import pe.edu.uni.ventapasajes.service.LogueoService;
import pe.edu.uni.ventapasajes.util.Session;

/**
 *
 * @author Gustavo Coronel
 */
public class LogonController {
   
   public void validar(String usuario, String clave) {
      LogueoService service = new LogueoService();
      UsuarioModel usuarioModel = service.validar(usuario, clave);
      if(usuarioModel==null){
         throw  new RuntimeException("Datos incorrectos.");
      }
      Session.put("usuario", usuarioModel);
   }
   
}
