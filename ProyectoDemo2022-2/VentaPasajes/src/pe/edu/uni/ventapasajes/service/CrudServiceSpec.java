package pe.edu.uni.ventapasajes.service;

import java.util.List;

/**
 *
 * @author Gustavo Coronel
 */
public interface CrudServiceSpec<T> {
    
    T findById(Integer id);
    
    List<T> findAll();
    
    List<T> find(T model);
    
    void insert(T model);
    
    void update(T model);
    
    void delete(Integer id);
    
}
