package pe.edu.uni.ventapasajes.model;

/**
 * Mapeao de la tabla: CLIENTE
 *
 * @author Gustavo Coronel
 */
public class ClienteModel {

   private Integer id;
   private String nombre;
   private String apellido;
   private String dni;
   private String distrito;
   private String correo;
   private String telefono;

   public ClienteModel() {
      this.id = 0;
      this.nombre = "";
      this.apellido = "";
      this.dni = "";
      this.distrito = "";
      this.correo = "";
      this.telefono = "";
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

   public String getDni() {
      return dni;
   }

   public void setDni(String dni) {
      this.dni = dni;
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

   @Override
   public String toString() {
      return "ClienteModel{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", distrito=" + distrito + ", correo=" + correo + ", telefono=" + telefono + '}';
   }

}
