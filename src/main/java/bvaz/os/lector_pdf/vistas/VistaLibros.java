package bvaz.os.lector_pdf.vistas;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.*;
import bvaz.os.lector_pdf.modelos.entidades.*;

public class VistaLibros extends VistaBase{
	private static final long serialVersionUID = 1L;
	private JRadioButton optAgregar;
	private JRadioButton optEditar;
	private JComboBox<Libro> cboLibros;
	private JTextField txtTitulo;
	private JButton btnAgregarAutor;
	private JPanel pnlAutores;
	private ArrayList<Autor> autores;
	private JComboBox<Editorial> cboEditorial;
	private JTextField txtFecha;
	private JButton btnExplorador;
	private JFileChooser explorador;
	private JLabel lblArchivo;
	private JButton btnAgregarLibro;
	private JButton btnModificarLibro;
	private JButton btnLimpiar;
	private ArrayList<RegistroAutores> listadoAutores;
	private boolean estaInicializado;
	
	private void inicializarAtributos() {
		if(estaInicializado) {
			return;
		}
		
		optAgregar = new JRadioButton("Agregar un nuevo libro");
		optEditar = new JRadioButton("Editar un registro existente");
		cboLibros = new JComboBox<Libro>();
		txtTitulo = new JTextField(20);
		btnAgregarAutor = new JButton("+");
		pnlAutores = new JPanel();
		autores = new ArrayList<Autor>();
		cboEditorial = new JComboBox<Editorial>();
		txtFecha = new JTextField(10);
		btnExplorador = new JButton("Buscar");
		explorador = new JFileChooser();
		lblArchivo = new JLabel();
		btnAgregarLibro = new JButton("Agregar");
		btnModificarLibro = new JButton("Modificar");
		btnLimpiar = new JButton("Limpiar");
		listadoAutores = new ArrayList<RegistroAutores>();
		estaInicializado = true;
	}
	
	private void agregarAutor() {
		RegistroAutores nuevoAutor = new RegistroAutores();
		
		listadoAutores.add(nuevoAutor);
		pnlAutores.add(nuevoAutor);
		pnlAutores.revalidate();
	}
	
	/**
	 * Elimina los datos ingresados dejando todos los controles en su estado inicial.
	 */
	public void limpiar() {
		cboLibros.setSelectedIndex(-1);
		txtTitulo.setText("");
		pnlAutores.removeAll();
		pnlAutores.revalidate();
		cboEditorial.setSelectedIndex(-1);
		txtFecha.setText("");
		lblArchivo.setText("");
	}
	
	public VistaLibros() {
		JPanel viewport = new JPanel();
		JScrollPane pnlBase = null;
		GridBagConstraints c = null;
		
		inicializarAtributos();
		
		//Configuracion de radio buttons
		ButtonGroup bg = new ButtonGroup();
		bg.add(optAgregar);
		bg.add(optEditar);
		optAgregar.doClick();
		
		//Configuracion del explorador
		explorador.setFileFilter(new FileNameExtensionFilter("Libro (.pdf)", "pdf"));
		btnExplorador.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File archivo = null;
				int seleccion = explorador.showOpenDialog(VistaLibros.this);
				
				if(seleccion == JFileChooser.APPROVE_OPTION) {
					archivo = explorador.getSelectedFile();
					lblArchivo.setText(archivo.getAbsolutePath());
				}
			}
		});
		
		//Configuracion de seccion de autores
		pnlAutores.setLayout(new BoxLayout(pnlAutores, BoxLayout.Y_AXIS));
		btnAgregarAutor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agregarAutor();
			}
		});
		
		//Configuracion de boton "limpiar"
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar();
			}
		});
		
		/* Layout */
		viewport.setLayout(new GridBagLayout());
		
		//Titulo
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weighty = 0.1;
		viewport.add(this.crearTitulo("Libros"), c);
		
		//Opciones
		Box contenedor = Box.createVerticalBox();
		Box contenedorEdicion = Box.createHorizontalBox();
		optAgregar.setAlignmentX(SwingConstants.LEFT);
		contenedorEdicion.add(optEditar);
		contenedorEdicion.add(cboLibros);
		contenedorEdicion.add(Box.createHorizontalGlue());
		contenedor.add(optAgregar);
		contenedor.add(contenedorEdicion);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 30, 0, 0);
		viewport.add(contenedor, c);
		
		//Titulo del libro
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0.1;
		c.weighty = 0.1;
		viewport.add(this.crearEtiqueta("Titulo: "), c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 0.9;
		c.anchor = GridBagConstraints.WEST;
		viewport.add(txtTitulo, c);
		
		//Autores
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 0.1;
		viewport.add(this.crearEtiqueta("Autores: "), c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.WEST;
		viewport.add(btnAgregarAutor, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		viewport.add(pnlAutores, c);
		
		//Editorial
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 5;
		c.weighty = 0.1;
		viewport.add(this.crearEtiqueta("Editorial: "), c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 5;
		c.anchor = GridBagConstraints.WEST;
		viewport.add(cboEditorial, c);
		
		//Fecha de publicacion
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 6;
		c.weighty = 0.1;
		viewport.add(this.crearEtiqueta("Fecha de publicación: "), c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 6;
		c.anchor = GridBagConstraints.WEST;
		viewport.add(txtFecha, c);
		
		//Explorador de archivos
		Box componentesExplorador = Box.createHorizontalBox();
		componentesExplorador.add(btnExplorador);
		componentesExplorador.add(Box.createHorizontalStrut(15));
		componentesExplorador.add(lblArchivo);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 7;
		c.weighty = 0.1;
		viewport.add(this.crearEtiqueta("Archivo PDF: "), c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 7;
		c.anchor = GridBagConstraints.WEST;
		viewport.add(componentesExplorador, c);
		
		//Botones de edicion
		Box botones = Box.createHorizontalBox();
		botones.add(Box.createHorizontalGlue());
		botones.add(btnAgregarLibro);
		botones.add(Box.createHorizontalGlue());
		botones.add(btnModificarLibro);
		botones.add(Box.createHorizontalGlue());
		botones.add(btnLimpiar);
		botones.add(Box.createHorizontalGlue());
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 30, 0);
		viewport.add(botones, c);
		
		
		pnlBase = new JScrollPane(viewport);
		pnlBase.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		this.setLayout(new GridLayout(1,1));
		this.add(pnlBase);
	}
	
	/**
	 * Agrega un {@link ActionListener} al boton para agregar un libro.
	 * @param a
	 */
	public void definirEventoAgregar(ActionListener a) {
		btnAgregarLibro.addActionListener(a);
	}
	
	/**
	 * Establece los libros en el ComboBox de seleccion del libro a editar.
	 * @param pLibros Lista de libros.
	 */
	public void definirLibros(List<Libro> pLibros) {
		cboLibros.removeAllItems();
		
		for(Libro l : pLibros) {
			cboLibros.addItem(l);
		}
	}
	
	/**
	 * Establece los autores.
	 * @param pAutores Lista de autores.
	 */
	public void definirAutores(List<Autor> pAutores) {
		autores.clear();
		autores.addAll(pAutores);
		
		for(RegistroAutores r : listadoAutores) {
			r.actualizarAutores();
		}
	}
	
	/**
	 * Establece las editoriales.
	 * @param pEditoriales Lista de editoriales.
	 */
	public void definirEditoriales(List<Editorial> pEditoriales) {
		cboEditorial.removeAllItems();
		
		for(Editorial e : pEditoriales) {
			cboEditorial.addItem(e);
		}
	}
	
	/**
	 * Recupera el libro que se busca modificar.
	 * @return El libro seleccionado o nulo si esta en modo de insercion.
	 */
	public Libro libroActivo() {
		if(optAgregar.isSelected()) {
			return null;
		}
		
		return (Libro) cboLibros.getSelectedItem();
	}
	
	/**
	 * Genera un libro cuyos atributos son llenados con los controles de entrada,
	 * no se revisa la validez de los atributos.
	 * @return Nuevo objeto LIBRO
	 */
	public Libro generarLibro() {
		Libro libro = new Libro();
		Editorial editorial = (Editorial)cboEditorial.getSelectedItem();
		
		libro.titulo = txtTitulo.getText();
		libro.editorial = editorial.id_editorial;
		libro.fecha_publicacion = txtFecha.getText();
		libro.archivo = lblArchivo.getText();
		libro.marcador = "";
		
		return libro;
	}
	
	/**
	 * Recupera los autores seleccionados.
	 * @return Lista de autores seleccionados, la lista esta vacia 
	 * si no hay algun autor seleccionado.
	 */
	public List<Autor> getAutores(){
		ArrayList<Autor> autoresSeleccionados = new ArrayList<Autor>();
		
		for(RegistroAutores r : listadoAutores) {
			autoresSeleccionados.add(r.itemSeleccionado());
		}
		
		return autoresSeleccionados;
	}
	
	private class RegistroAutores extends JPanel{
		private static final long serialVersionUID = 1L;
		private JComboBox<Autor> selector;
		
		public RegistroAutores() {
			JButton btnEliminarAutor = new JButton("X");
			Box controles = Box.createHorizontalBox();
			selector = new JComboBox<Autor>();
			
			btnEliminarAutor.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pnlAutores.remove(RegistroAutores.this);
					pnlAutores.revalidate();
				}
			});
			
			actualizarAutores();
			
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			controles.add(selector);
			controles.add(Box.createHorizontalStrut(15));
			controles.add(btnEliminarAutor);
			
			this.add(controles);
			this.add(Box.createVerticalStrut(15));
		}
		
		public Autor itemSeleccionado() {
			return (Autor)selector.getSelectedItem();
		}
		
		public void actualizarAutores() {
			selector.removeAllItems();
			
			for(Autor a : autores) {
				selector.addItem(a);
			}
			
			this.revalidate();
		}
	}
}
