package pe.edu.uni.ventapasajes.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.uni.ventapasajes.db.AccesoDB;
import pe.edu.uni.ventapasajes.model.ClienteModel;

/**
 *
 * @author Gustavo Coronel
 */
public class CrudClienteImpl implements CrudServiceSpec<ClienteModel>, RowMapper<ClienteModel> {

   private final String SELECT_BASE = "SELECT IDCLIENTE,NOMBRE,APELLIDO,DNI,DISTRITO,CORREO,TELEFONO FROM CLIENTE";
   private final String INSERT = "INSERT INTO CLIENTE(IDCLIENTE,NOMBRE,APELLIDO,DNI,DISTRITO,CORREO,TELEFONO) VALUES(?,?,?,?,?,?,?)";
   private final String UPDATE = "UPDATE CLIENTE SET NOMBRE=?,APELLIDO=?,DNI=?,DISTRITO=?,CORREO=?,TELEFONO=? WHERE IDCLIENTE=?";
   private final String DELETE = "DELETE FROM CLIENTE WHERE IDCLIENTE=?";
   
   @Override
   public ClienteModel findById(Integer id) {
      ClienteModel cliente = null;
      Connection cn = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;
      String sql = null;
      try {
         cn = AccesoDB.getConnection();
         sql = SELECT_BASE + " WHERE IDCLIENTE=?";
         pstm = cn.prepareStatement(sql);
         pstm.setInt(1, id);
         rs = pstm.executeQuery();
         if (rs.next()) {
            cliente = mapRow(rs);
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
      return cliente;
   }

   @Override
   public List<ClienteModel> findAll() {
      List<ClienteModel> lista = new ArrayList<>();
      Connection cn = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;
      ClienteModel model = null;
      try {
         cn = AccesoDB.getConnection();
         pstm = cn.prepareStatement(SELECT_BASE);
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
   public List<ClienteModel> find(ClienteModel model) {
      // Revisando los datos
      if (model.getId() == null) {
         model.setId(0);
      }
      if (model.getApellido() == null) {
         model.setApellido("");
      }
      model.setApellido("%" + model.getApellido() + "%");
      
      if (model.getNombre() == null) {
         model.setNombre("");
      }
      
      model.setNombre("%" + model.getNombre() + "%");
      // Programacion
      List<ClienteModel> lista = new ArrayList<>();
      Connection cn = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;
      ClienteModel cliente = null;
      String sql = null;
      try {
         cn = AccesoDB.getConnection();
         sql = SELECT_BASE + " WHERE IDCLIENTE = IIF(?=0,IDCLIENTE,?) AND NOMBRE LIKE ? AND APELLIDO LIKE ?";
         pstm = cn.prepareStatement(sql);
         pstm.setInt(1, model.getId());
         pstm.setInt(2, model.getId());
         pstm.setString(3, model.getNombre());
         pstm.setString(4, model.getApellido());
         rs = pstm.executeQuery();
         while (rs.next()) {
            cliente = mapRow(rs);
            lista.add(cliente);
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
   public void insert(ClienteModel model) {
      Connection cn = null;
      PreparedStatement pstm;
      ResultSet rs;
      String sql;
      int filas;
      int id;
      try {
         // Inicio de Tx
         cn = AccesoDB.getConnection();
         cn.setAutoCommit(false); // Inportante para controlar Tx
         // Falta validaciones
         
         // Leer contador
         sql = "UPDATE CONTADOR SET ULTIMO = ULTIMO + 1 WHERE IDCONTADOR=1";
         pstm = cn.prepareStatement(sql);
         filas = pstm.executeUpdate();
         pstm.close();
         if(filas != 1){
            throw new SQLException("Se tiene problemas con el contador.");
         }
         sql = "SELECT ULTIMO FROM CONTADOR WHERE IDCONTADOR=1";
         pstm = cn.prepareStatement(sql);
         rs = pstm.executeQuery();
         if(!rs.next()){
            throw new SQLException("No se encontro el contador.");
         }
         id = rs.getInt("ULTIMO");
         model.setId(id);
         pstm.close();
         // Insertar el cliente
         pstm = cn.prepareStatement(INSERT);
         pstm.setInt(1, id);
         pstm.setString(2, model.getNombre());
         pstm.setString(3, model.getApellido());
         pstm.setString(4, model.getDni());
         pstm.setString(5, model.getDistrito());
         pstm.setString(6, model.getCorreo());
         pstm.setString(7, model.getTelefono());
         pstm.executeUpdate();
         pstm.close();
         // Confirmar de Tx
         cn.commit();
      } catch (SQLException e) {
         try {
            cn.rollback();
         } catch (Exception e1) {
         }
         throw new RuntimeException(e.getMessage());
      } catch (Exception e) {
         try {
            cn.rollback();
         } catch (Exception e1) {
         }
         throw new RuntimeException("Error en la base de datos, intentelo en 5 minutos.");
      }finally{
         try {
            cn.close();
         } catch (Exception e) {
         }
      }
   }

   
   @Override
   public void update(ClienteModel model) {
      Connection cn = null;
      PreparedStatement pstm;
      int filas;
      try {
         // Inicio de Tx
         cn = AccesoDB.getConnection();
         cn.setAutoCommit(false); // Inportante para controlar Tx
         // Falta validaciones
         
         // Actualizar el cliente
         pstm = cn.prepareStatement(UPDATE);
         pstm.setString(1, model.getNombre());
         pstm.setString(2, model.getApellido());
         pstm.setString(3, model.getDni());
         pstm.setString(4, model.getDistrito());
         pstm.setString(5, model.getCorreo());
         pstm.setString(6, model.getTelefono());
         pstm.setInt(7, model.getId());
         filas = pstm.executeUpdate();
         if(filas != 1){
            throw new SQLException("No eexiste el cliente.");
         }
         pstm.close();
         // Confirmar de Tx
         cn.commit();
      } catch (SQLException e) {
         try {
            cn.rollback();
         } catch (Exception e1) {
         }
         throw new RuntimeException(e.getMessage());
      } catch (Exception e) {
         try {
            cn.rollback();
         } catch (Exception e1) {
         }
         throw new RuntimeException("Error en la base de datos, intentelo en 5 minutos.");
      }finally{
         try {
            cn.close();
         } catch (Exception e) {
         }
      }
   }

   @Override
   public void delete(Integer id) {
      Connection cn = null;
      PreparedStatement pstm;
      int filas;
      try {
         // Inicio de Tx
         cn = AccesoDB.getConnection();
         cn.setAutoCommit(false); // Inportante para controlar Tx
         // Actualizar el cliente
         pstm = cn.prepareStatement(DELETE);
         pstm.setInt(1, id);
         filas = pstm.executeUpdate();
         pstm.close();
         if(filas != 1){
            throw new SQLException("No eexiste el cliente.");
         }
         // Confirmar de Tx
         cn.commit();
      } catch (SQLException e) {
         try {
            cn.rollback();
         } catch (Exception e1) {
         }
         throw new RuntimeException(e.getMessage());
      } catch (Exception e) {
         try {
            cn.rollback();
         } catch (Exception e1) {
         }
         throw new RuntimeException("Error en la base de datos, intentelo en 5 minutos.");
      }finally{
         try {
            cn.close();
         } catch (Exception e) {
         }
      }
   }

   @Override
   public ClienteModel mapRow(ResultSet rs) throws SQLException {
      ClienteModel model = new ClienteModel();
      model.setId(rs.getInt("IDCLIENTE"));
      model.setNombre(rs.getString("NOMBRE"));
      model.setApellido(rs.getString("APELLIDO"));
      model.setDni(rs.getString("DNI"));
      model.setDistrito(rs.getString("DISTRITO"));
      model.setCorreo(rs.getString("CORREO"));
      model.setTelefono(rs.getString("TELEFONO"));
      return model;
   }

}
