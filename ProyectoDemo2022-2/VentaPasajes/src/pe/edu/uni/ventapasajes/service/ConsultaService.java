package pe.edu.uni.ventapasajes.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.uni.ventapasajes.db.AccesoDB;
import pe.edu.uni.ventapasajes.model.ComboModel;

/**
 *
 * @author Gustavo Coronel
 */
public class ConsultaService implements RowMapper<ComboModel> {

   public List<ComboModel> leerCiudades() {
      List<ComboModel> lista = new ArrayList<>();
      Connection cn = null;
      PreparedStatement pstm;
      ResultSet rs;
      ComboModel model;
      String sql;
      try {
         cn = AccesoDB.getConnection();
         sql = "select idciudad id, nombre etiqueta from ciudad";
         pstm = cn.prepareStatement(sql);
         rs = pstm.executeQuery();
         while (rs.next()) {
            model = mapRow(rs);
            lista.add(model);
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
      return lista;
   }

   public List<ComboModel> leerCiudades(int origen) {
      List<ComboModel> lista = new ArrayList<>();
      Connection cn = null;
      PreparedStatement pstm;
      ResultSet rs;
      ComboModel model;
      String sql;
      try {
         cn = AccesoDB.getConnection();
         sql = "select idciudad id, nombre etiqueta from ciudad "
                 + "where idciudad <> ? "
                 + "and idciudad not in (select destino from ruta where origen=?)";
         pstm = cn.prepareStatement(sql);
         pstm.setInt(1, origen);
         pstm.setInt(2, origen);
         rs = pstm.executeQuery();
         while (rs.next()) {
            model = mapRow(rs);
            lista.add(model);
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
      return lista;
   }

   @Override
   public ComboModel mapRow(ResultSet rs) throws SQLException {
      ComboModel model = new ComboModel();
      model.setId(rs.getInt("id"));
      model.setEtiqueta(rs.getString("etiqueta"));
      return model;
   }

}
