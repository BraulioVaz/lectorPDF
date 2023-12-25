package bvaz.os.lector_pdf.vistas;

import java.lang.reflect.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import bvaz.os.lector_pdf.modelos.entidades.*;
import bvaz.os.lector_pdf.modelos.tda.Tupla;

public class TabuladorEntidades<T extends Entidad> extends JTable{
	private static final long serialVersionUID = 1L;
	private Class<T> entidad;
	private ArrayList<Field> camposVisibles;
	private ArrayList<T> instancias;
	
	/**
	 * Registra que campos de la entidad seran visibles en la tabla.
	 * @param nombreCampo Nombre del campo a incluir en la tabla.
	 * @return true si el campo es parte de la entidad y es incluido con exito
	 *  a la lista de campos visibles, false en caso contrario.
	 */
	private boolean registrarCampo(String nombreCampo) {
		try {
			camposVisibles.add(entidad.getField(nombreCampo));
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * Agrega una nueva columna a la tabla.
	 * @param nombreColumna Nombre de la columna
	 */
	private void agregarColumna(String nombreColumna) {
		TableColumn columna = new TableColumn();
		columna.setHeaderValue(nombreColumna);
		
		this.addColumn(columna);
		DefaultTableModel modelo = (DefaultTableModel)this.dataModel;
		modelo.addColumn(nombreColumna);
	}
	
	/**
	 * Crea un nuevo tabulador.
	 * @param pTipo La clase de la entidad a tabular.
	 * @param pCampos Lista de tuplas del tipo &lt 'campo de la entidad', 'nombre de la columna' &gt
	 */
	public TabuladorEntidades(Class<T> pTipo, List<Tupla<String, String>> pCampos) {
		entidad = pTipo;
		camposVisibles = new ArrayList<Field>();
		instancias = new ArrayList<T>();
		
		for(Tupla<String, String> campo : pCampos) {
			if(registrarCampo(campo.valor)) {
				agregarColumna(campo.llave);
			}
		}
	}
	
	private Object leerCampo(Object obj, Field campo) {
		try {
			return campo.get(obj);
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public void agregar(T entidad) {
		DefaultTableModel modelo = (DefaultTableModel)this.dataModel;
		Object[] datos = new Object[camposVisibles.size()];
		
		instancias.add(entidad);
		
		for(int i = 0; i < camposVisibles.size(); i++) {
			datos[i] = leerCampo(entidad, camposVisibles.get(i));
		}
		
		modelo.addRow(datos);
	}
	
	private void limpiar() {
		DefaultTableModel modelo = (DefaultTableModel)this.dataModel;
		int registros = modelo.getRowCount();
		
		for(int i = 0; i < registros; i++) {
			modelo.removeRow(0);
		}
		
		instancias.clear();
	}
	
	public void setEntidades(List<T> pEntidades) {
		limpiar();
		
		for(T entidad : pEntidades) {
			agregar(entidad);
		}
	}
	
	public T entidadActiva() {
		int filaSeleccionada = this.getSelectedRow();
		
		if(filaSeleccionada == -1) {
			return null;
		}
		
		return instancias.get(filaSeleccionada);
	}
}
