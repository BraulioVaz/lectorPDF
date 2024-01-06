package bvaz.os.lector_pdf.vistas;

import java.awt.event.*;
import javax.swing.*;

class ComboBox<T> extends JPanel{
	private static final long serialVersionUID = 1L;
	private static final String SELECCION_NULA = "--Elige una opción--";
	private JComboBox<Object> selector;
	
	public ComboBox() {
		selector = new JComboBox<Object>();
		
		selector.addItem(SELECCION_NULA);
		selector.setEditable(false);
		
		this.add(selector);
	}
	
	@Override
	public void setEnabled(boolean estaHabilitado) {
		super.setEnabled(estaHabilitado);
		selector.setEnabled(estaHabilitado);
	}
	

	/**
	 * Agrega un ActionListener.
	 * @param a
	 */
	public void addActionListener(ActionListener a) {
		selector.addActionListener(a);
	}
	
	/**
	 * Define el objeto seleccionado, si el objeto no es parte del listado
	 * no hay cambios en la seleccion.
	 * @param objeto
	 */
	public void setObjetoSeleccionado(T objeto) {
		if(objeto == null) {
			selector.setSelectedItem(SELECCION_NULA);
			return;
		}
		
		selector.setSelectedItem(objeto);
	}
	
	/**
	 * Recupera el objeto seleccionado.
	 * @return Objeto seleccionado o nulo si no hay selección.
	 */
	@SuppressWarnings("unchecked")
	public T getObjetoSeleccionado() {
		Object objSeleccionado = selector.getSelectedItem();
		
		if(objSeleccionado == null || objSeleccionado.equals(SELECCION_NULA)) {
			return null;
		}
		
		return (T) selector.getSelectedItem();
	}
	
	/**
	 * Quita todos los objetos dejando el combo box solo con la 
	 * opcion nula.
	 */
	public void limpiarListado() {
		selector.removeAllItems();
		
		selector.addItem(SELECCION_NULA);
	}
	
	/**
	 * Agrega una opcion al combo box.
	 * @param obj
	 */
	public void agregarObjeto(T obj) {
		selector.addItem(obj);
	}
}
