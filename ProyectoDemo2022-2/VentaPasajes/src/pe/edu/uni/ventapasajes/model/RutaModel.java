package pe.edu.uni.ventapasajes.model;

/**
 *
 * @author Gustavo Coronel
 */
public class RutaModel {

   private Integer id;
   private String nombre;
   private Integer origen;
   private Integer destino;
   private String duracion;
   private Double precio1;
   private Double precio2;
   private Double precio3;
   private Integer empleado;

   public RutaModel() {
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getNombre() {
      return nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public Integer getOrigen() {
      return origen;
   }

   public void setOrigen(Integer origen) {
      this.origen = origen;
   }

   public Integer getDestino() {
      return destino;
   }

   public void setDestino(Integer destino) {
      this.destino = destino;
   }

   public String getDuracion() {
      return duracion;
   }

   public void setDuracion(String duracion) {
      this.duracion = duracion;
   }

   public Double getPrecio1() {
      return precio1;
   }

   public void setPrecio1(Double precio1) {
      this.precio1 = precio1;
   }

   public Double getPrecio2() {
      return precio2;
   }

   public void setPrecio2(Double precio2) {
      this.precio2 = precio2;
   }

   public Double getPrecio3() {
      return precio3;
   }

   public void setPrecio3(Double precio3) {
      this.precio3 = precio3;
   }

   public Integer getEmpleado() {
      return empleado;
   }

   public void setEmpleado(Integer empleado) {
      this.empleado = empleado;
   }

}
