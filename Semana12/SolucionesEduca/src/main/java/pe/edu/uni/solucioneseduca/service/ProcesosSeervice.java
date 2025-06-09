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

	/**
	 *
	 * @param bean
	 * @return
	 */
	public MatriculadoDto procMatricula(MatriculadoDto bean) {
		// Variables
		Connection cn = null;

		// Proceso
		try {
			// Paso 1: Inicio del proceso
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false); // Inicio de TX
			// Paso 2: Validaciones
			validarEmpleado(cn, bean.getIdEmpleado());
			validarAlumno(cn, bean.getIdAlumno());
			validarCurso(cn, bean.getIdCurso());
			validarMatricula(cn, bean.getIdCurso(), bean.getIdAlumno());
			validarVacantesCurso(cn, bean.getIdCurso());
			validarTipoMatricula(bean.getTipoMatricula());
			// Paso 3: 

			// Paso 4: Registrar la matricula
			registrarMatricula(cn,bean);
			
			// Paso final
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

	/**
	 * Este metodo varifica si el empleado existe en la base de datos.
	 * Basicamente verifica que exista el idEmpleado en la tabla EMPLEADO. Si no
	 * existe, genera un excepción de tipo SQLException.
	 *
	 * @param cn Representa la conexión con la base de datos.
	 * @param idEmpleado Representa el ID del empleado que se verificara en la
	 * BD.
	 * @throws SQLException Tipo de excepción que genera si no existe el ID del
	 * empleado.
	 */
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
		if (cont == 0) {
			throw new SQLException("Empleado no existe.");
		}
	}

	/**
	 *
	 * @param cn
	 * @param idAlumno
	 * @throws SQLException
	 */
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
		if (cont == 0) {
			throw new SQLException("Alumno no existe.");
		}
	}

	/**
	 *
	 * @param cn
	 * @param idCurso
	 * @throws SQLException
	 */
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
		if (cont == 0) {
			throw new SQLException("Curso no existe.");
		}
	}

	/**
	 *
	 * @param cn
	 * @param idCurso
	 * @param idAlumno
	 */
	private void validarMatricula(Connection cn, int idCurso, int idAlumno) throws SQLException {
		// Datos
		String sql = """
               select count(1) cont
               from MATRICULA
               where cur_id=? and alu_id=?
               """;
		// Proceso
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setInt(1, idCurso);
		pstm.setInt(2, idAlumno);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int cont = rs.getInt("cont");
		rs.close();
		pstm.close();
		if (cont == 1) {
			throw new SQLException("La matricula ya existe.");
		}
	}

	/**
	 * 
	 * @param cn
	 * @param idCurso
	 * @throws SQLException 
	 */
	private void validarVacantesCurso(Connection cn, int idCurso) throws SQLException {
		// Datos
		String sql = """
               select cur_vacantes - cur_matriculados cont
               from CURSO where cur_id = ?               
               """;
		// Proceso
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setInt(1, idCurso);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int cont = rs.getInt("cont");
		rs.close();
		pstm.close();
		if (cont <= 0) {
			throw new SQLException("Curso no tiene vacantes.");
		}
	}

	/**
	 * 
	 * @param tipoMatricula
	 * @throws SQLException 
	 */
	private void validarTipoMatricula(String tipoMatricula) throws SQLException {
		// Datos
		String[] tipos = {"REGULAR", "MEDIABECA", "BECA"};
		// Proceso
		int cont = 0;
		for (String tipo : tipos) {
			cont += tipo.equals(tipoMatricula)?1:0;
		}
		if (cont == 0) {
			throw new SQLException("Tipo de matricula incorrecto.");
		}
	}

	private void registrarMatricula(Connection cn, MatriculadoDto bean) throws SQLException {
		// Insertar matricula
		String sql = """
               insert into MATRICULA(cur_id,alu_id,emp_id,mat_tipo,mat_fecha,mat_precio,mat_cuotas)
               values(?,?,?,?,GETDATE(),?,?)
               """;
		PreparedStatement pstm = cn.prepareCall(sql);
		pstm.setInt(1, bean.getIdCurso());
		pstm.setInt(2, bean.getIdAlumno());
		pstm.setInt(3, bean.getIdEmpleado());
		pstm.setString(4, bean.getTipoMatricula());
		pstm.setDouble(5, bean.getPrecio());
		pstm.setInt(6, bean.getCuotas());
		pstm.executeUpdate();
		pstm.close();
		// Actualizar curso
		sql = """
        update CURSO
        set cur_matriculados = cur_matriculados + 1
        where cur_id = ?
        """;
		pstm = cn.prepareStatement(sql);
		pstm.setInt(1, bean.getIdCurso());
		pstm.executeUpdate();
		pstm.close();
	}

}
