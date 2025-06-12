package pe.edu.uni.ventaflores.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.uni.ventaflores.db.AccesoDB;
import pe.edu.uni.ventaflores.model.Combo;

public class ComboService {

    public List<Combo> getCategorias(){
        List<Combo> lista = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pstm;
        ResultSet rs;
        String sql = "select idcategoria id, nombcorto nombre from categoria";
        Combo bean;
        try {
            cn = AccesoDB.getConnection();
            pstm = cn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                bean = new Combo();
                bean.setId(rs.getInt("id"));
                bean.setNombre(rs.getString("nombre"));
                lista.add(bean);
            }
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch(Exception e){
            throw new RuntimeException("Error al leer las categorias.");
        } finally{
            try {
                cn.close();
            } catch (Exception e) {
            }
        }
        return lista;
    }

    
}
