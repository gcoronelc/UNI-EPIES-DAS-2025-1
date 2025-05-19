package pe.edu.uni.solucioneseduca.prueba;

import java.sql.Connection;
import pe.edu.uni.solucioneseduca.db.AccesoDB;

/**
 *
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 * @cursos gcoronelc.github.io
 */
public class Prueba01Conexion {
	
	public static void main(String[] args) {
		
		try {
			Connection cn = AccesoDB.getConnection();
			System.out.println("Conexion ok.");
			cn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
	}

}
