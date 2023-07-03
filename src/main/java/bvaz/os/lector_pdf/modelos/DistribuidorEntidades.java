package bvaz.os.lector_pdf.modelos;

import java.util.*;
import bvaz.os.lector_pdf.modelos.entidades.*;

public abstract class DistribuidorEntidades<T extends Entidad> {
	
	public DistribuidorEntidades() {
		
	}
	
	public abstract List<T> obtenerTodos();
	
	public abstract T buscarPorID(String id);
	
	public abstract boolean insertar(T entidad);
	
	public abstract boolean actualizar(T entidad);
	
	public abstract boolean eliminar(String id);
}
