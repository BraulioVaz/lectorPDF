package bvaz.os.lector_pdf.vistas;

import java.awt.*;
import javax.swing.*;
import bvaz.os.lector_pdf.modelos.entidades.*;

public class VistaCarpetas extends VistaBase{
	private static final long serialVersionUID = 1L;
	private JComboBox<Carpeta> cboCarpetaRaiz;
	private JTextField txtNombre;
	private Explorador explorador;
	private JButton btnAgregar;
	
	public VistaCarpetas() {
		cboCarpetaRaiz = new JComboBox<Carpeta>();
		txtNombre = new JTextField(10);
		explorador = new Explorador();
		btnAgregar = new JButton("Agregar");
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
		Box contRaiz = Box.createHorizontalBox();
		Box contNombre = Box.createHorizontalBox();
		
		contRaiz.add(this.crearEtiqueta("Carpeta raiz: "));
		contRaiz.add(Box.createHorizontalStrut(15));
		contRaiz.add(cboCarpetaRaiz);
		contNombre.add(this.crearEtiqueta("Nombre de la carpeta: "));
		contNombre.add(Box.createHorizontalStrut(15));
		contNombre.add(txtNombre);
		contDatos.add(contRaiz);
		contDatos.add(Box.createVerticalStrut(15));
		contDatos.add(contNombre);
		contDatos.add(Box.createVerticalStrut(15));
		contDatos.add(btnAgregar);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0.3;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 25, 0, 0);
		this.add(contDatos, c);
		
		//Explorador
		JScrollPane pnlExplorador = new JScrollPane(explorador);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0.4;
		c.weighty = 0.6;
		this.add(pnlExplorador, c);
	}
}
