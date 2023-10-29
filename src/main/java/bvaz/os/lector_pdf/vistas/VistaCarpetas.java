package bvaz.os.lector_pdf.vistas;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import bvaz.os.lector_pdf.modelos.entidades.*;

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
		lstCarpetas = new JList<Carpeta>();
		explorador = new Explorador();
		btnCrearCarpeta = new JButton("Agregar");
		btnAsignar = new JButton("=>");
		btnDesasignar = new JButton("<=");
		GridBagConstraints c = null;
		
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
}
