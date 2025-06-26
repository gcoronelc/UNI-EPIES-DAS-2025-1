	public MatriculadoDto procMatricula(MatriculadoDto bean){
		// Variables
		Connection cn = null;
		
		// Proceso
		try {
			// Paso 1: Inicio del proceso
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false); // Inicio de TX
			
			
			// Paso final
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (Exception e1) {
			}
			throw new RuntimeException(e.getMessage());
		} catch (Exception e){
			try {
				cn.rollback();
			} catch (Exception e1) {
			}
			throw new RuntimeException("Error en el proceso.");
		} finally{
			try {
				cn.close();
			} catch (Exception e) {
			}
		}
		// Reporte
		return bean;
	}