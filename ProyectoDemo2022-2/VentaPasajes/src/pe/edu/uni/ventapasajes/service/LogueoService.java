package pe.edu.uni.ventapasajes.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.edu.uni.ventapasajes.db.AccesoDB;
import pe.edu.uni.ventapasajes.model.UsuarioModel;

public class LogueoService {

   public UsuarioModel validar(String usuario, String clave) {
      String sql = "SELECT IDEMPLEADO,USUARIO,'*****' CLAVE,IDROL,ACTIVO FROM USUARIO WHERE USUARIO=? AND CLAVE=?";
      UsuarioModel usuarioModel = null;
      Connection cn = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;
      try {
         cn = AccesoDB.getConnection();
         pstm = cn.prepareStatement(sql);
         pstm.setString(1, usuario);
         pstm.setString(2, clave);
         rs = pstm.executeQuery();
         if (rs.next()) {
            usuarioModel = new UsuarioModel();
            usuarioModel.setId(rs.getInt("IDEMPLEADO"));
            usuarioModel.setUsuario(rs.getString("USUARIO"));
            usuarioModel.setClave(rs.getString("CLAVE"));
            usuarioModel.setRol(rs.getInt("IDROL"));
            usuarioModel.setActivo(rs.getInt("ACTIVO"));
         }
         rs.close();
         pstm.close();
      } catch (SQLException e) {
         throw new RuntimeException(e.getMessage());
      } catch (Exception e) {
         throw new RuntimeException("Error en el proceso. ejecutelo nuevamente.");
      } finally {
         try {
            cn.close();
         } catch (Exception e) {
         }
      }
      return usuarioModel;
   }
}
