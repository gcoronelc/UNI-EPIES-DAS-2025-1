package pe.edu.uni.ventapasajes.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.edu.uni.ventapasajes.db.AccesoDB;
import pe.edu.uni.ventapasajes.model.RutaModel;

/**
 *
 * @author Gustavo Coronel
 */
public class SistemaService {

   public Integer creacionRuta(RutaModel model) {
      Connection cn = null;
      PreparedStatement pstm;
      ResultSet rs;
      String sql;
      int filas;
      int id;
      try {
         // Inicio de Tx
         cn = AccesoDB.getConnection();
         cn.setAutoCommit(false);
         // Validacion
         sql = "SELECT COUNT(1) FILAS FROM RUTA WHERE ORIGEN=? AND DESTINO=?";
         pstm = cn.prepareStatement(sql);
         pstm.setInt(1, model.getOrigen());
         pstm.setInt(2, model.getDestino());
         rs = pstm.executeQuery();
         rs.next();
         filas = rs.getInt("FILAS");
         rs.close();
         pstm.close();
         if(filas!=0){
            throw new SQLException("La ruta ya existe.");
         }
         // Actualizar contador
         sql = "UPDATE CONTADOR SET ULTIMO = ULTIMO + 1 WHERE IDCONTADOR = 3";
         pstm = cn.prepareStatement(sql);
         filas = pstm.executeUpdate();
         pstm.close();
         if (filas != 1) {
            throw new SQLException("Problemas con el contador de rutas.");
         }
         // Leer el contador
         sql = "SELECT ULTIMO FROM CONTADOR WHERE IDCONTADOR = 3";
         pstm = cn.prepareStatement(sql);
         rs = pstm.executeQuery();
         rs.next();
         id = rs.getInt("ULTIMO");
         rs.close();
         pstm.close();
         // Insertar ruta
         sql = "INSERT INTO RUTA(IDRUTA,NOMBRE,ORIGEN,DESTINO,DURACION,PRECIO1,PRECIO2,PRECIO3,IDEMPLEADO) VALUES(?,?,?,?,?,?,?,?,?)";
         pstm = cn.prepareStatement(sql);
         pstm.setInt(1, id);
         pstm.setString(2, model.getNombre());
         pstm.setInt(3, model.getOrigen());
         pstm.setInt(4, model.getDestino());
         pstm.setString(5, model.getDuracion());
         pstm.setDouble(6, model.getPrecio1());
         pstm.setDouble(7, model.getPrecio2());
         pstm.setDouble(8, model.getPrecio3());
         pstm.setInt(9, model.getEmpleado());
         pstm.executeUpdate();
         // Confirmar de Tx
         cn.commit();
         model.setId(id); // Asigna el id al modelo.
      } catch (SQLException e) {
         try {
            cn.rollback();
         } catch (SQLException e1) {
         }
         throw new RuntimeException(e.getMessage());
      } catch (Exception e) {
         try {
            cn.rollback();
         } catch (Exception e1) {
         }
         throw new RuntimeException("Error en el proceso, intentelo nuevamente.");
      } finally {
         try {
            cn.close();
         } catch (Exception e) {
         }
      }
      return id;
   }

}
