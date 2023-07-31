package bvaz.os.lector_pdf.vistas;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import bvaz.os.lector_pdf.modelos.entidades.Editorial;
import bvaz.os.lector_pdf.modelos.tda.Tupla;

public class VistaEditoriales extends VistaBase{
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JButton btnAgregar;
	private TabuladorEntidades<Editorial> editoriales;
	
	public VistaEditoriales() {
		txtNombre = new JTextField(10);
		btnAgregar = new JButton("Agregar");
		editoriales = null;
		GridBagConstraints c = null;
		JScrollPane contenedorTabla = null;
		
		this.setLayout(new GridBagLayout());
		
		//Titulo
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weighty = 0.1;
		this.add(this.crearTitulo("Agregar Editorial"), c);
		
		//Nombre de editorial
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.2;
		c.weighty = 0.15;
		this.add(this.crearEtiqueta("Nombre: "), c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		this.add(txtNombre, c);
		
		//Boton para agregar
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 0.15;
		this.add(btnAgregar, c);
		
		//Registros guardados
		ArrayList<Tupla<String, String>> campos = new ArrayList<Tupla<String, String>>();
		campos.add(new Tupla<String, String>("nombre","Nombre"));
		editoriales = new TabuladorEntidades<Editorial>(Editorial.class, campos);
		contenedorTabla = new JScrollPane(editoriales);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.weighty = 0.6;
		this.add(contenedorTabla, c);
	}
}
