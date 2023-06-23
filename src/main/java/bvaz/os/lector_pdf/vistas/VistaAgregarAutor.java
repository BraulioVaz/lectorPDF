package bvaz.os.lector_pdf.vistas;

import java.awt.*;
import javax.swing.*;
import bvaz.os.lector_pdf.modelos.entidades.Autor;

public class VistaAgregarAutor extends VistaBase{
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JButton btnAgregar;
	
	public VistaAgregarAutor() {
		txtNombre = new JTextField(20);
		txtApellidos = new JTextField(20);
		btnAgregar = new JButton("Agregar");
		GridBagConstraints c;
		
		this.setLayout(new GridBagLayout());
		
		//Titulo
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weighty = 0.3;
		this.add(crearTitulo("Agregar autor"), c);
		
		//Nombre
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.2;
		c.weighty = 0.2;
		this.add(crearEtiqueta("Nombre: "), c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0.8;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		this.add(txtNombre, c);
		
		//Apellidos
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0.2;
		c.weighty = 0.2;
		this.add(crearEtiqueta("Apellidos: "), c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 0.8;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		this.add(txtApellidos, c);
		
		//Boton agregar
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.weighty = 0.3;
		this.add(btnAgregar, c);
	}
	
	public Autor autor() {
		Autor nuevoAutor = new Autor();
		
		nuevoAutor.nombre = txtNombre.getText();
		nuevoAutor.apellidos = txtApellidos.getText();
		
		return nuevoAutor;
	}
}
