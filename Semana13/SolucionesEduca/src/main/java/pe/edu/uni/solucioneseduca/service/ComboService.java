package pe.edu.uni.solucioneseduca.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.uni.solucioneseduca.db.AccesoDB;
import pe.edu.uni.solucioneseduca.dto.ComboDto;

public class ComboService {

    public List<ComboDto> getCursos() {
        // Variables
        Connection cn = null;
        List<ComboDto> lista = new ArrayList<>();
        PreparedStatement pstm;
        ResultSet rs;
        String sql = """
                     select
                     	cur_id id, cur_nombre  + ' ('
                     	+ cast(cur_vacantes - cur_matriculados as varchar(10)) 
                     	+ ')' nombre,
                        cast(cur_precio as varchar(20)) precio
                     from CURSO
                     where cur_vacantes - cur_matriculados > 0
                     order by 2
                     """;
        // Proceso
        try {
            cn = AccesoDB.getConnection();
            pstm = cn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ComboDto dto = new ComboDto();
                dto.setId(rs.getInt("id"));
                dto.setNombre(rs.getString("nombre"));
                dto.setBag(rs.getString("precio"));
                lista.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error en el proceso.");
        } finally {
            try {
                cn.close();
            } catch (Exception e) {
            }
        }
        // Reporte
        return lista;
    }

    public List<ComboDto> getAlumnos(int idCurso) {
        // Variables
        Connection cn = null;
        List<ComboDto> lista = new ArrayList<>();
        PreparedStatement pstm;
        ResultSet rs;
        String sql = """
                    select alu_id id, alu_nombre nombre
                    from ALUMNO
                    where alu_id not in 
                    (select alu_id from MATRICULA
                    where cur_id = ?)
                    order by 2
                     """;
        // Proceso
        try {
            cn = AccesoDB.getConnection();
            pstm = cn.prepareStatement(sql);
            pstm.setInt(1, idCurso);
            rs = pstm.executeQuery();
            while(rs.next()){
                ComboDto dto = new ComboDto();
                dto.setId(rs.getInt("id"));
                dto.setNombre(rs.getString("nombre"));
                lista.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error en el proceso.");
        } finally {
            try {
                cn.close();
            } catch (Exception e) {
            }
        }
        // Reporte
        return lista;
    }

}


