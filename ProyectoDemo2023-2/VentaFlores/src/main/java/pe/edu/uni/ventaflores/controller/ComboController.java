package pe.edu.uni.ventaflores.controller;

import java.util.List;
import pe.edu.uni.ventaflores.model.Combo;
import pe.edu.uni.ventaflores.service.ComboService;

public class ComboController {
    
    private final ComboService service;

    public ComboController() {
        service = new ComboService();
    }
    
    public List<Combo> getCategorias(){
        return service.getCategorias();
    }
    
}
