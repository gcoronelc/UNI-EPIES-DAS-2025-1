package pe.edu.uni.ventaflores.pruebas;

import pe.edu.uni.ventaflores.model.DetalleVenta;
import pe.edu.uni.ventaflores.model.Venta;
import pe.edu.uni.ventaflores.service.NegocioService;

/**
 * Registro de venta por el vendedor
 * @author LAB-A
 */
public class Prueba02 {
    
    public static void main(String[] args) {
        try {
            // Preparar los datos de la venta
            Venta venta = new Venta();
            venta.setIdestado(1);
            venta.setIdcliente(3);
            venta.setVendedor(4);
            venta.setCajero(null);
            venta.setFechaventa(null);
            venta.setFechapago(null);
            // Proceso
            NegocioService service = new NegocioService();
            venta = service.procesarVenta(venta);
            // Reporte
            System.out.println("Venta procesada correctamente.");
            System.out.println("IDVenta = " + venta.getIdventa());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    
}
