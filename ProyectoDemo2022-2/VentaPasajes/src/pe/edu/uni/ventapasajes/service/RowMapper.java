package pe.edu.uni.ventapasajes.service;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Gustavo Coronel
 */
public interface RowMapper<T> {
    
    T mapRow(ResultSet rs) throws SQLException;
    
}
