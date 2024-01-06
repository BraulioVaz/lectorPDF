package bvaz.os.lector_pdf.vistas;

import java.awt.event.*;
import java.util.List;
import javax.swing.*;

class RegistroOpcional<T> extends JPanel{
	private static final long serialVersionUID = 1L;
	private ComboBox<T> selector;
	private JButton btnEliminar;
	
	public RegistroOpcional() {
		Box controles = Box.createHorizontalBox();
		selector = new ComboBox<T>();
		btnEliminar = new JButton("X");
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		controles.add(selector);
		controles.add(Box.createHorizontalStrut(15));
		controles.add(btnEliminar);
		
		this.add(controles);
		this.add(Box.createVerticalStrut(15));
	}
	
	/**
	 * Establece el objeto seleccionado
	 * @param objeto El objeto a seleccionar o nulo si no hay selección
	 */
	public void setObjectoSeleccionado(T objeto) {
		selector.setObjetoSeleccionado(objeto);
	}
	
	/**
	 * Recupera el objeto seleccionado.
	 * @return El objeto seleccionado o nulo si no hay selección.
	 */
	public T getObjetoSeleccionado() {
		return selector.getObjetoSeleccionado();
	}
	
	/**
	 * Cambia el listado de objetos que se pueden seleccionar.
	 * @param objetos Objetos que pueden ser seleccionados.
	 */
	public void actualizarListadoDeObjetos(List<T> objetos) {
		selector.limpiarListado();
		
		for(T obj : objetos) {
			selector.agregarObjeto(obj);
		}
		
		this.revalidate();
	}
	
	/**
	 * Establece el {@link ActionListener} a ejecutar al presionar el boton "Eliminar".
	 * @param a
	 */
	public void setEventoEliminar(ActionListener a) {
		btnEliminar.addActionListener(a);
	}
}
