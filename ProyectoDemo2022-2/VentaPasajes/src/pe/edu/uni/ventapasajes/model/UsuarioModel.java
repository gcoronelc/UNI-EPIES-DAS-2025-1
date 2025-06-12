package pe.edu.uni.ventapasajes.model;

/**
 *
 * @author Gustavo Coronel
 */
public class UsuarioModel {

   private int id;
   private String usuario;
   private String clave;
   private int rol;
   private int activo;

   public UsuarioModel() {
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getUsuario() {
      return usuario;
   }

   public void setUsuario(String usuario) {
      this.usuario = usuario;
   }

   public String getClave() {
      return clave;
   }

   public void setClave(String clave) {
      this.clave = clave;
   }

   public int getRol() {
      return rol;
   }

   public void setRol(int rol) {
      this.rol = rol;
   }

   public int getActivo() {
      return activo;
   }

   public void setActivo(int activo) {
      this.activo = activo;
   }

}
