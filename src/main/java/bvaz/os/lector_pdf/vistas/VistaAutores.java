package bvaz.os.lector_pdf.vistas;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import bvaz.os.lector_pdf.modelos.entidades.Autor;
import bvaz.os.lector_pdf.modelos.tda.*;

public class VistaAutores extends VistaBase{
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JButton btnAgregar;
	private JButton btnEliminar;
	private TabuladorEntidades<Autor> tblAutores;
	
	public VistaAutores() {
		txtNombre = new JTextField(20);
		txtApellidos = new JTextField(20);
		btnAgregar = new JButton("Agregar");
		btnEliminar = new JButton("Eliminar");
		GridBagConstraints c;
		
		this.setLayout(new GridBagLayout());
		
		//Titulo
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weighty = 0.1;
		this.add(crearTitulo("Agregar autor"), c);
		
		//Nombre
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.2;
		c.weighty = 0.1;
		this.add(crearEtiqueta("Nombre: "), c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0.8;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		this.add(txtNombre, c);
		
		//Apellidos
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0.2;
		c.weighty = 0.1;
		this.add(crearEtiqueta("Apellidos: "), c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 0.8;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		this.add(txtApellidos, c);
		
		//Botones
		Box contenedorDeBotones = Box.createHorizontalBox();
		contenedorDeBotones.add(btnAgregar);
		contenedorDeBotones.add(Box.createHorizontalStrut(50));
		contenedorDeBotones.add(btnEliminar);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.weighty = 0.2;
		this.add(contenedorDeBotones, c);
		
		//CRUD
		JScrollPane contenedor = null;
		ArrayList<Tupla<String, String>> campos = new ArrayList<Tupla<String, String>>();
		campos.add(new Tupla<String, String>("nombre","Nombre"));
		campos.add(new Tupla<String, String>("apellidos","Apellidos"));
		
		tblAutores = new TabuladorEntidades<Autor>(Autor.class, campos);
		contenedor = new JScrollPane(tblAutores);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		c.weighty = 0.5;
		c.weightx = 1;
		this.add(contenedor, c);
	}
	
	public void llenarTabla(java.util.List<Autor> autores) {
		tblAutores.setEntidades(autores);
	}
	
	public Autor nuevoAutor() {
		Autor nuevoAutor = new Autor();
		
		nuevoAutor.nombre = txtNombre.getText();
		nuevoAutor.apellidos = txtApellidos.getText();
		
		return nuevoAutor;
	}
	
	public Autor autorSeleccionado() {
		return tblAutores.entidadActiva();
	}
	
	public void definirEventoAgregar(ActionListener a) {
		btnAgregar.addActionListener(a);
	}
	
	public void definirEventoEliminar(ActionListener a) {
		btnEliminar.addActionListener(a);
	}
}
