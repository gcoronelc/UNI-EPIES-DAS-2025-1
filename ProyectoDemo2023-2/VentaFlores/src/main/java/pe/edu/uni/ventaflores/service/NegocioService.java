package pe.edu.uni.ventaflores.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import pe.edu.uni.ventaflores.db.AccesoDB;
import pe.edu.uni.ventaflores.model.DetalleVenta;
import pe.edu.uni.ventaflores.model.Venta;

public class NegocioService {

    public Venta procesarVenta(Venta venta) {
        // Validar venta
        if (venta.getDetalle().isEmpty()) {
            throw new RuntimeException("Una venta debe tener por lo menos 1 item.");
        }
        // Validacion de cantidad
        validarCantidad(venta.getDetalle());
        // Variables
        Connection cn = null;
        // Proceso
        try {
            // Inicio de TX
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false); // Inicio de TX
            // 1: Validar cliente
            validarCliente(cn, venta.getIdcliente());
            // 2: Validar vendedor
            validarEmpleado(cn, venta.getVendedor(), "El vendedor no existe.");
            // 3: Actualizar stock de productos
            actualizarStock(cn, venta.getDetalle());
            // 4: Calculo de la venta
            calcularVenta(venta);
            // 5: Registrar la venta
            venta.setIdestado(1);
            registrarVenta(cn, venta);
            // Fin de TX
            cn.commit();
        } catch (SQLException e) {
            try {
                cn.rollback(); // Cancela la TX.
            } catch (Exception e1) {
            }
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            try {
                cn.rollback(); // Cancela la TX
            } catch (Exception e1) {
            }
            throw new RuntimeException("Error en el proceso, intentelo nuevamente.");
        } finally {
            try {
                cn.close();
            } catch (Exception e) {
            }
        }
        // Reporte
        return venta;
    }

    private void validarCantidad(List<DetalleVenta> detalle) {
        for (DetalleVenta item : detalle) {
            if (item.getCantidad() <= 0) {
                throw new RuntimeException("La cantidad debe ser mayor o igual a cero.");
            }
        }
    }

    private void validarCliente(Connection cn, int idcliente) throws SQLException {
        String sql = "SELECT COUNT(1) contador FROM CLIENTE WHERE IDCLIENTE=?";
        PreparedStatement pstm = cn.prepareStatement(sql);
        pstm.setInt(1, idcliente);
        ResultSet rs = pstm.executeQuery();
        rs.next();
        int contador = rs.getInt("contador");
        if (contador != 1) {
            throw new SQLException("El cliente no existe.");
        }
    }

    private void validarEmpleado(Connection cn, int idempleado, String msg_error) throws SQLException {
        String sql = "SELECT COUNT(1) contador FROM EMPLEADO WHERE IDEMPLEADO=?";
        PreparedStatement pstm = cn.prepareStatement(sql);
        pstm.setInt(1, idempleado);
        ResultSet rs = pstm.executeQuery();
        rs.next();
        int contador = rs.getInt("contador");
        if (contador != 1) {
            throw new SQLException(msg_error);
        }
    }

    private void actualizarStock(Connection cn, List<DetalleVenta> detalle) throws SQLException {
        // Variables
        PreparedStatement pstmSelect = null;
        PreparedStatement pstmUpdate = null;
        ResultSet rs = null;
        String sqlSelect = "SELECT stock, preventa FROM PRODUCTO WHERE IDPRODUCTO=?";
        String sqlUpdate = "UPDATE PRODUCTO SET STOCK=? WHERE IDPRODUCTO=?";
        int stock;
        double precio;
        // Preparando las sentencias
        pstmSelect = cn.prepareStatement(sqlSelect);
        pstmUpdate = cn.prepareStatement(sqlUpdate);
        // Proceso los items
        for (DetalleVenta item : detalle) {
            pstmSelect.setInt(1, item.getIdproducto());
            rs = pstmSelect.executeQuery();
            if (!rs.next()) {
                throw new SQLException("Producto no existe.");
            }
            stock = rs.getInt("stock");
            precio = rs.getDouble("preventa");
            if (stock < item.getCantidad()) {
                throw new SQLException("No existe stock suficiente del producto " + item.getIdproducto() + ".");
            }
            item.setPreventa(precio);
            stock = stock - item.getCantidad();
            rs.close();
            pstmUpdate.setInt(1, stock);
            pstmUpdate.setInt(2, item.getIdproducto());
            pstmUpdate.executeUpdate();
        }
        pstmUpdate.close();
        pstmSelect.close();
    }

    private void calcularVenta(Venta venta) {
        // Proceso
        double total = 0.0;
        for (DetalleVenta item : venta.getDetalle()) {
            item.setSubtotal(item.getPreventa() * item.getCantidad());
            total += item.getSubtotal();
        }
        double importe = total / 1.18;
        double impuesto = total - importe;
        // Actualizar venta
        venta.setSubtotal(importe);
        venta.setImpuesto(impuesto);
        venta.setTotal(total);
    }

    private void registrarVenta(Connection cn, Venta venta) throws SQLException {
        // Variables
        String sqlInsert1 = "INSERT INTO VENTA(IDESTADO,IDCLIENTE,VENDEDOR,FECHAVENTA";
        sqlInsert1 += ",SUBTOTAL,IMPUESTO,TOTAL) VALUES(?,?,?,GETDATE(), ?,?,?)";
        String sqlInsert2 = "INSERT INTO DETALLE_VENTA(IDVENTA,IDPRODUCTO,PREVENTA,";
        sqlInsert2 += "CANTIDAD,SUBTOTAL) VALUES(?,?,?,?,?)";
        String sqlSelectId = "select @@IDENTITY idventa";
        // Preparar las sentencias SQL
        PreparedStatement pstmInsert1 = cn.prepareStatement(sqlInsert1);
        PreparedStatement pstmInsert2 = cn.prepareStatement(sqlInsert2);
        PreparedStatement pstmSelectId = cn.prepareStatement(sqlSelectId);
        // Insertar la venta
        pstmInsert1.setInt(1, venta.getIdestado());
        pstmInsert1.setInt(2, venta.getIdcliente());
        pstmInsert1.setInt(3, venta.getVendedor());
        pstmInsert1.setDouble(4, venta.getSubtotal());
        pstmInsert1.setDouble(5, venta.getImpuesto());
        pstmInsert1.setDouble(6, venta.getTotal());
        pstmInsert1.executeUpdate();
        // Obtener el IDVenta
        ResultSet rs = pstmSelectId.executeQuery();
        if (!rs.next()) {
            throw new SQLException("Error en el proceso Registrar Venta.");
        }
        int idVenta = rs.getInt("idventa");
        venta.setIdventa(idVenta);
        System.out.println("IdVenta: " + idVenta);
        // Grabar los detalles
        for (DetalleVenta item : venta.getDetalle()) {
            pstmInsert2.setInt(1, idVenta);
            pstmInsert2.setInt(2, item.getIdproducto());
            pstmInsert2.setDouble(3, item.getPreventa());
            pstmInsert2.setInt(4, item.getCantidad());
            pstmInsert2.setDouble(5, item.getSubtotal());
            pstmInsert2.executeUpdate();
        }
    }

}
