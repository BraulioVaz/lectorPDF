package bvaz.os.lector_pdf.vistas;

import java.lang.reflect.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import bvaz.os.lector_pdf.modelos.entidades.*;
import bvaz.os.lector_pdf.modelos.tda.Tupla;

public class TabuladorEntidades<T extends Entidad> extends JTable{
	private static final long serialVersionUID = 1L;
	private Class<T> tipo;
	private ArrayList<Field> campos;
	private ArrayList<T> entidades;
	
	private boolean registrarCampo(String nombreCampo) {
		try {
			campos.add(tipo.getField(nombreCampo));
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	private void agregarColumna(String nombreColumna) {
		TableColumn columna = new TableColumn();
		columna.setHeaderValue(nombreColumna);
		
		this.addColumn(columna);
		DefaultTableModel modelo = (DefaultTableModel)this.dataModel;
		modelo.addColumn(nombreColumna);
	}
	
	public TabuladorEntidades(Class<T> pTipo, List<Tupla<String, String>> pCampos) {
		tipo = pTipo;
		campos = new ArrayList<Field>();
		entidades = new ArrayList<T>();
		
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
		Object[] datos = new Object[campos.size()];
		
		entidades.add(entidad);
		
		for(int i = 0; i < campos.size(); i++) {
			datos[i] = leerCampo(entidad, campos.get(i));
		}
		
		modelo.addRow(datos);
	}
	
	private void limpiar() {
		DefaultTableModel modelo = (DefaultTableModel)this.dataModel;
		int registros = modelo.getRowCount();
		
		for(int i = 0; i < registros; i++) {
			modelo.removeRow(0);
		}
		
		entidades.clear();
	}
	
	public void setEntidades(List<T> pEntidades) {
		limpiar();
		
		for(T entidad : pEntidades) {
			agregar(entidad);
		}
	}
	
	public T entidadActiva() {
		return null;
	}
}
