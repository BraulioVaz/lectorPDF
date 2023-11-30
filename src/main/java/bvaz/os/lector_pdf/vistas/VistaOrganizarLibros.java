package bvaz.os.lector_pdf.vistas;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import bvaz.os.lector_pdf.modelos.entidades.Libro;
import bvaz.os.lector_pdf.modelos.tda.Arbol;

public class VistaOrganizarLibros extends VistaBase{
	private static final long serialVersionUID = 1L;
	private JList<Libro> lstLibros;
	private JButton btnAgregar;
	private JButton btnQuitar;
	private Explorador explorador;
	
	public VistaOrganizarLibros() {
		lstLibros = new JList<Libro>(new DefaultListModel<Libro>());
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
	
	public void actualizarExplorador(Arbol estructura) {
		explorador.definirEstructura(estructura);
		explorador.construir();
	}
	
	public void definirLibros(List<Libro> libros) {
		DefaultListModel<Libro> listadoLibros = (DefaultListModel<Libro>)lstLibros.getModel();
		
		listadoLibros.clear();
		for(Libro libroActual : libros) {
			listadoLibros.addElement(libroActual);
		}
	}
	
	/**
	 * Recupera el libro seleccionado en el listado global de libros.
	 * @return Libro seleccionado.
	 */
	public Libro seleccionDelListado() {
		return lstLibros.getSelectedValue();
	}
	
	/**
	 * Recupera el elemento seleccionado en el explorador, pudiendo ser un libro 
	 * o carpeta.
	 * @return Elemento seleccionado en el explorador.
	 */
	public Object seleccionDelExplorador() {
		return explorador.elementoSeleccionado();
	}
	
	/**
	 * Si el elemento seleccionado no se encuentra en la raiz
	 * regresa la carpeta que contiene al elemento seleccionado.
	 * @return La carpeta activa o nulo si no hay seleccion o
	 * el elemento seleccionado se encuentra en la raiz.
	 */
	public Object carpetaActivaDelExplorador() {
		return explorador.carpetaActiva();
	}
	
	public void eventoAsignarLibro(ActionListener a) {
		btnAgregar.addActionListener(a);
	}
	
	public void eventoDesasignarLibro(ActionListener a) {
		btnQuitar.addActionListener(a);
	}
}
