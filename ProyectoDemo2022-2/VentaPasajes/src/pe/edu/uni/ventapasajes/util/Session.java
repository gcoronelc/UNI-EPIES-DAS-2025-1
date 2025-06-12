package pe.edu.uni.ventapasajes.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware
 * @cursos gcoronelc.github.io
 */
public class Session {

	private static Map<String, Object> datos;

	static {
		datos = new HashMap<>();
	}

	private Session() {
	}

	public static void put(String key, Object object) {
		datos.put(key, object);
	}

	public static Object get(String key) {
		return datos.get(key);
	}

}
