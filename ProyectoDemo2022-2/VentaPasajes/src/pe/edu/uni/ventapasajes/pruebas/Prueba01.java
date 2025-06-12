package pe.edu.uni.ventapasajes.pruebas;

import java.sql.Connection;
import pe.edu.uni.ventapasajes.db.AccesoDB;

public class Prueba01 {
	
	public static void main(String[] args) {
		try {
			Connection cn = AccesoDB.getConnection();
			System.out.println("Acceso Ok.");
			cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
