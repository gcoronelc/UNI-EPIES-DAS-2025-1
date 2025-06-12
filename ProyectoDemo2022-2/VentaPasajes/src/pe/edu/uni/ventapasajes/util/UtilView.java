package pe.edu.uni.autoventas.util;

import java.util.List;
import javax.swing.JComboBox;
import pe.edu.uni.ventapasajes.model.ComboModel;

/**
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware
 * @cursos gcoronelc.github.io
 */
public class UtilView {

	private UtilView() {
	}
	
	// Constantes para el CRUD
	public static final String CRUD_NUEVO = "NUEVO";
	public static final String CRUD_EDITAR = "EDITAR";
	public static final String CRUD_ELIMINAR = "ELIMINAR";
	
   
   public static void llenarCombo(JComboBox<ComboModel> combo, List<ComboModel> lista){
      combo.removeAllItems();
      for (ComboModel item : lista) {
         combo.addItem(item);
      }
      combo.setSelectedIndex(-1);
   }
   

}
