package bvaz.os.lector_pdf.vistas;

import java.awt.*;
import javax.swing.*;
import bvaz.os.lector_pdf.modelos.entidades.Libro;

public class VistaOrganizarLibros extends VistaBase{
	private static final long serialVersionUID = 1L;
	private JList<Libro> lstLibros;
	private JButton btnAgregar;
	private JButton btnQuitar;
	private Explorador explorador;
	
	public VistaOrganizarLibros() {
		lstLibros = new JList<Libro>();
		btnAgregar = new JButton("=>");
		btnQuitar = new JButton("<=");
		explorador = new Explorador();
		
		this.setLayout(new BorderLayout());
		
		/*Titulo del panel*/
		JLabel lblTitulo = crearTitulo("Organización de los libros");
		
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		
		this.add(lblTitulo, BorderLayout.NORTH);
		
		/*Componentes para manipular la estructura*/
		Box contenedorPrincipal = Box.createHorizontalBox();
		Box contenedorBotones = Box.createVerticalBox();
		JScrollPane pnlListado = new JScrollPane(lstLibros);
		JScrollPane pnlExplorador = new JScrollPane(explorador);
		
		contenedorBotones.add(btnAgregar);
		contenedorBotones.add(Box.createRigidArea(new Dimension(0, 25)));
		contenedorBotones.add(btnQuitar);
		
		contenedorPrincipal.add(Box.createHorizontalGlue());
		contenedorPrincipal.add(pnlListado);
		contenedorPrincipal.add(Box.createHorizontalGlue());
		contenedorPrincipal.add(contenedorBotones);
		contenedorPrincipal.add(Box.createHorizontalGlue());
		contenedorPrincipal.add(pnlExplorador);
		contenedorPrincipal.add(Box.createHorizontalGlue());
		
		contenedorPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		
		this.add(contenedorPrincipal, BorderLayout.CENTER);
	}
}
