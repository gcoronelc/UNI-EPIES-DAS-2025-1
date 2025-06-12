package pe.edu.uni.ventaflores.model;

public class DetalleVenta {

    private int iddetalle;
    private int idventa;
    private int idproducto;
    private String nombre;
    private double preventa;
    private int cantidad;
    private double subtotal;

    public DetalleVenta() {
        this.iddetalle = 0;
        this.idventa = 0;
        this.idproducto = 0;
        this.nombre = "POR DEFINIR";
        this.preventa = 0;
        this.cantidad = 0;
        this.subtotal = 0;
    }
    
    public DetalleVenta(int idproducto, int cantidad){
        this.idproducto = idproducto;
        this.cantidad = cantidad;
    }

    public int getIddetalle() {
        return iddetalle;
    }

    public void setIddetalle(int iddetalle) {
        this.iddetalle = iddetalle;
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPreventa() {
        return preventa;
    }

    public void setPreventa(double preventa) {
        this.preventa = preventa;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

}
