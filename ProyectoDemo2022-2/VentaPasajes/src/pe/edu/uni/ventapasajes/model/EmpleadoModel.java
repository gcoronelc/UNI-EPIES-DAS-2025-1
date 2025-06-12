package pe.edu.uni.ventapasajes.model;

/**
 *
 * @author Gustavo Coronel
 */
public class EmpleadoModel {

   private Integer id;
   private String nombre;
   private String apellido;
   private String distrito;
   private String correo;
   private String telefono;
   private Integer activo;
   private Integer cargo;

   public EmpleadoModel() {
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

   public String getApellido() {
      return apellido;
   }

   public void setApellido(String apellido) {
      this.apellido = apellido;
   }

   public String getDistrito() {
      return distrito;
   }

   public void setDistrito(String distrito) {
      this.distrito = distrito;
   }

   public String getCorreo() {
      return correo;
   }

   public void setCorreo(String correo) {
      this.correo = correo;
   }

   public String getTelefono() {
      return telefono;
   }

   public void setTelefono(String telefono) {
      this.telefono = telefono;
   }

   public Integer getActivo() {
      return activo;
   }

   public void setActivo(Integer activo) {
      this.activo = activo;
   }

   public Integer getCargo() {
      return cargo;
   }

   public void setCargo(Integer cargo) {
      this.cargo = cargo;
   }

}
