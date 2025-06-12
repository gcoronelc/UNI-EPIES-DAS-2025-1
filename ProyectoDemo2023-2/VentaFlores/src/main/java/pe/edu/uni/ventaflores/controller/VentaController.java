/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.ventaflores.controller;

import pe.edu.uni.ventaflores.model.Venta;
import pe.edu.uni.ventaflores.service.NegocioService;

/**
 *
 * @author LAB-A
 */
public class VentaController {

    public Venta procesarVenta(Venta venta) {
        NegocioService service = new NegocioService();
        venta = service.procesarVenta(venta);
        return venta;
    }

}
