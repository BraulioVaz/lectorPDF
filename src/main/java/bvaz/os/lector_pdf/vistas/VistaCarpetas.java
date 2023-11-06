package bvaz.os.lector_pdf.vistas;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import bvaz.os.lector_pdf.modelos.entidades.*;
import bvaz.os.lector_pdf.modelos.tda.Arbol;

public class VistaCarpetas extends VistaBase{
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JList<Carpeta> lstCarpetas;
	private ArrayList<Carpeta> carpetas;
	private Explorador explorador;
	private JButton btnCrearCarpeta;
	private JButton btnAsignar;
	private JButton btnDesasignar;
	
	public VistaCarpetas() {
		txtNombre = new JTextField(10);
		lstCarpetas = new JList<Carpeta>(new DefaultListModel<Carpeta>());
		carpetas = new ArrayList<Carpeta>();
		explorador = new Explorador();
		btnCrearCarpeta = new JButton("Agregar");
		btnAsignar = new JButton("=>");
		btnDesasignar = new JButton("<=");
		GridBagConstraints c = null;
		
		lstCarpetas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.setLayout(new GridBagLayout());
		
		//Titulo
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.1;
		this.add(this.crearTitulo("Carpetas"), c);
		
		//Seccion de datos
		Box contDatos = Box.createVerticalBox();
		Box contNombre = Box.createHorizontalBox();
		
		contNombre.add(this.crearEtiqueta("Nombre de la carpeta: "));
		contNombre.add(Box.createHorizontalStrut(15));
		contNombre.add(txtNombre);
		contDatos.add(contNombre);
		contDatos.add(Box.createVerticalStrut(15));
		contDatos.add(btnCrearCarpeta);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0.3;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 25, 0, 0);
		this.add(contDatos, c);
		
		//Explorador y listado de carpetas
		JScrollPane pnlExplorador = new JScrollPane(explorador);
		JScrollPane pnlListado = new JScrollPane(lstCarpetas);
		Box contBotones = Box.createVerticalBox();
		Box contGeneral = Box.createHorizontalBox();
		
		btnAsignar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnDesasignar.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		contBotones.add(btnAsignar);
		contBotones.add(Box.createVerticalStrut(15));
		contBotones.add(btnDesasignar);
		contGeneral.add(Box.createHorizontalGlue());
		contGeneral.add(pnlListado);
		contGeneral.add(Box.createHorizontalStrut(20));
		contGeneral.add(contBotones);
		contGeneral.add(Box.createHorizontalStrut(20));
		contGeneral.add(pnlExplorador);
		contGeneral.add(Box.createHorizontalGlue());
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 0.6;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(contGeneral, c);
	}
	
	private void actualizarListadoDeCarpetas() {
		DefaultListModel<Carpeta> modelo = (DefaultListModel<Carpeta>) lstCarpetas.getModel();
		modelo.clear();
		
		for(Carpeta c : carpetas) {
			modelo.addElement(c);
		}
	}
	
	/**
	 * Establece la lista de carpetas.
	 * @param pCarpetas
	 */
	public void definirCarpetas(List<Carpeta> pCarpetas) {
		carpetas.clear();
		carpetas.addAll(pCarpetas);
		actualizarListadoDeCarpetas();
	}
	
	/**
	 * Actualiza el explorador.
	 * @param estructura La nueva estructura del explorador
	 */
	public void actualizarExplorador(Arbol estructura) {
		explorador.definirEstructura(estructura);
		explorador.construir();
	}
	
	/**
	 * Establece el {@link ActionListener} asociado al buton para
	 * crear una nueva carpeta.
	 * @param a
	 */
	public void definirEventoCrearCarpeta(ActionListener a) {
		btnCrearCarpeta.addActionListener(a);
	}
	
	/**
	 * Establece el {@link ActionListener} asociado al buton para
	 * asignar una subcarpeta.
	 * @param a
	 */
	public void definirEventoAsignarCarpeta(ActionListener a) {
		btnAsignar.addActionListener(a);
	}
	
	/**
	 * Establece el {@link ActionListener} asociado al buton para
	 * desasignar una subcarpeta.
	 * @param a
	 */
	public void definirEventoDesasignarCarpeta(ActionListener a) {
		btnDesasignar.addActionListener(a);
	}
	
	/**
	 * Recupera el nombre introducido por el usuario, pudiendo ser 
	 * para una nueva carpeta o una modificacion al nombre de una carpeta existente.
	 * @return Nombre de carpeta
	 */
	public String nombreIntroducido() {
		return txtNombre.getText();
	}
	
	/**
	 * Recupera el elemento activo del listado de carpetas.
	 * @return Carpeta seleccionada
	 */
	public Carpeta elementoActivoDelListado() {
		return lstCarpetas.getSelectedValue();
	}
	
	/**
	 * Recupera el elemento activo del explorador.
	 * @return Carpeta seleccionada
	 */
	public Carpeta elementoActivoDelExplorador() {
		Object elementoSeleccionado = explorador.elementoSeleccionado();
		
		if(elementoSeleccionado != null && elementoSeleccionado instanceof Carpeta) {
			return (Carpeta) elementoSeleccionado;
		}
		
		return null;
	}
}
