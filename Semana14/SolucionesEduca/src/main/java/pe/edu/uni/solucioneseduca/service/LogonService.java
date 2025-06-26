package pe.edu.uni.solucioneseduca.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.edu.uni.solucioneseduca.db.AccesoDB;
import pe.edu.uni.solucioneseduca.dto.UsuarioDto;

public class LogonService {
	
	private final String SQL1 = """
                            select emp_id id, 
									CONCAT(emp_nombre,' ',emp_apellido) nombre,
									emp_usuario usuario
									from empleado
									where emp_usuario=? and emp_clave=?
                             """;
	
	private final String SQL2 = """
                            select alu_id id, alu_nombre nombre, alu_email usuario
                            from ALUMNO
                            where alu_email=? and alu_telefono=?
                             """;
	
	public UsuarioDto validar(String tipo, String usuario, String clave) {
		// Variables
		Connection cn = null;
		UsuarioDto bean = new UsuarioDto();
		PreparedStatement pstm;
		ResultSet rs;
		String sql;
		// Determinar el SQL
		tipo = tipo.toUpperCase();
		if(tipo.equals("EMPLEADO")){
			sql = SQL1;
		} else if(tipo.equals("ALUMNO")){
			sql = SQL2;
		} else {
			throw new RuntimeException("Tipo de usuario incorrecto.");
		}
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, usuario);
			pstm.setString(2, clave);
			rs = pstm.executeQuery();
			if (!rs.next()) {
				throw new SQLException("Datos incorrectos.");
			}
			bean.setId(rs.getInt("id"));
			bean.setNombre(rs.getString("nombre"));
			bean.setUsuario(rs.getString("usuario"));
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
		return bean;
	}

}
