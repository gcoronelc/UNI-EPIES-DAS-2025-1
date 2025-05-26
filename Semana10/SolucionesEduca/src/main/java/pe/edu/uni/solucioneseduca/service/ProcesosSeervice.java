package pe.edu.uni.solucioneseduca.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class ProcesosSeervice {
	
	
	public MatriculadoDto procMatricula(MatriculadoDto bean){
		// Variables
		Connection cn = null;
		
		// Proceso
		try {
			// Paso 1: Inicio del proceso
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false); // Inicio de TX
			// Paso 2: Validaciones
			validarEmpleado(cn,bean.getIdEmpleado());
			validarAlumno(cn,bean.getIdAlumno());
			validarCurso(cn,bean.getIdCurso());
			//validarMatricula(cn,bean.getIdCurso(), bean.getIdAlumno());
			
			// Paso 3: 
			
			// Paso 4: Registrar la matricula
			
			// Paso 5: Actualizar datos del curso
			
			// Paso final
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (Exception e1) {
			}
			throw new RuntimeException(e.getMessage());
		} catch (Exception e){
			try {
				cn.rollback();
			} catch (Exception e1) {
			}
			throw new RuntimeException("Error en el proceso.");
		} finally{
			try {
				cn.close();
			} catch (Exception e) {
			}
		}
		// Reporte
		return bean;
	}

	private void validarEmpleado(Connection cn, int idEmpleado) throws SQLException {
		// Datos
		String sql = "select count(1) cont from EMPLEADO where emp_id=?";
		// Proceso
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setInt(1, idEmpleado);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int cont = rs.getInt("cont");
		rs.close();
		pstm.close();
		if(cont==0){
			throw new SQLException("Empleado no existe.");
		}		
	}

	private void validarAlumno(Connection cn, int idAlumno) throws SQLException {
		// Datos
		String sql = "select count(1) cont from ALUMNO where alu_id=?";
		// Proceso
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setInt(1, idAlumno);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int cont = rs.getInt("cont");
		rs.close();
		pstm.close();
		if(cont==0){
			throw new SQLException("Alumno no existe.");
		}	
	}

	private void validarCurso(Connection cn, int idCurso) throws SQLException {
		// Datos
		String sql = "select count(1) cont from CURSO where cur_id=?";
		// Proceso
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setInt(1, idCurso);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int cont = rs.getInt("cont");
		rs.close();
		pstm.close();
		if(cont==0){
			throw new SQLException("Curso no existe.");
		}
	}
	

}
