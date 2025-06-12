package pe.edu.uni.ventaflores.pruebas;

import java.sql.Connection;
import pe.edu.uni.ventaflores.db.AccesoDB;

public class Prueba01 {

    public static void main(String[] args) {
        try {
            Connection cn = AccesoDB.getConnection();
            System.out.println("Conexion ok.");
            cn.close(); // Cierra la conexion
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
