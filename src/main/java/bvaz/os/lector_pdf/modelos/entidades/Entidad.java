package bvaz.os.lector_pdf.modelos.entidades;

import java.lang.reflect.*;

public abstract class Entidad {
	/**
	 * Valida si dos entidades tienen la misma llave primaria.
	 * @param <T> El tipo de entidad.
	 * @param entidadA
	 * @param entidadB
	 * @return true si ambas entidades son del mismo tipo y tienen igual llave primaria,
	 * false en caso contrario.
	 */
	public static <T extends Entidad> boolean mismaLlavePrimaria(T entidadA, T entidadB) {
		Class<?> claseA = entidadA.getClass();
		Field[] campos = claseA.getDeclaredFields();
		
		if(!claseA.isInstance(entidadB)) {
			return false;
		}
		
		try {
			for(Field c : campos) {
				if(c.isAnnotationPresent(LlavePrimaria.class)) {
					Object resultadoA = c.get(entidadA);
					Object resultadoB = c.get(entidadB);
					
					if(!resultadoA.equals(resultadoB)) {
						return false;
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
