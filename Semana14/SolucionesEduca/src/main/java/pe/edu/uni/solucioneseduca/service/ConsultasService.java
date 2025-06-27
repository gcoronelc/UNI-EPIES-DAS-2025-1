package pe.edu.uni.solucioneseduca.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.uni.solucioneseduca.db.AccesoDB;
import pe.edu.uni.solucioneseduca.dto.MatriculadoDto;

/**
 *
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 * @cursos gcoronelc.github.io
 */
public class ConsultasService {

    private final String SQL1 = """
                                with
                                v1 as (
                                	select alu_id, SUM(pag_importe) cancelado
                                	from PAGO
                                	where cur_id = ?
                                	group by alu_id
                                )
                                select m.cur_id idCurso, m.alu_id idAlumno,
                                a.alu_nombre nombre,
                                emp_id idEmpleado, m.mat_tipo tipoMatricula,
                                m.mat_precio precio, m.mat_cuotas cuotas,
                                ISNULL(v1.cancelado,0.0) cancelado,
                                m.mat_precio - ISNULL(v1.cancelado,0.0) deuda,
                                ISNULL(m.mat_nota,0) nota
                                from MATRICULA m
                                join ALUMNO a on m.alu_id = a.alu_id
                                left join v1 on m.alu_id = v1.alu_id
                                where cur_id = ?
                                """;
    public List<MatriculadoDto> consultarMatriculados(int idCurso) {
        // Variables
        List<MatriculadoDto> lista = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pstm;
        ResultSet rs;
        // Proceso
        try {
            cn = AccesoDB.getConnection();
            pstm = cn.prepareStatement(SQL1);
            pstm.setInt(1, idCurso);
            pstm.setInt(2, idCurso);
            rs = pstm.executeQuery();
            while(rs.next()){
                MatriculadoDto dto = new MatriculadoDto();
                dto.setIdCurso(rs.getInt("idCurso"));
                dto.setIdAlumno(rs.getInt("idAlumno"));
                dto.setNombre(rs.getString("nombre"));
                dto.setIdEmpleado(rs.getInt("idEmpleado"));
                dto.setTipoMatricula(rs.getString("tipoMatricula"));
                dto.setPrecio(rs.getDouble("precio"));
                dto.setCuotas(rs.getInt("cuotas"));
                dto.setCancelado(rs.getDouble("cancelado"));
                dto.setDeuda(rs.getDouble("deuda"));
                dto.setNota(rs.getInt("nota"));
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
