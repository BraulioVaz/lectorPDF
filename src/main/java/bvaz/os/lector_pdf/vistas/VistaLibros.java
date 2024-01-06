package bvaz.os.lector_pdf.vistas;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.*;
import bvaz.os.lector_pdf.modelos.entidades.Autor;
import bvaz.os.lector_pdf.modelos.entidades.Editorial;
import bvaz.os.lector_pdf.modelos.vistas.LibroCualificado;

public class VistaLibros extends VistaBase{
	private static final long serialVersionUID = 1L;
	private static final int MODO_INSERCION = 5;
	private static final int MODO_EDICION = 10;
	
	private JRadioButton optAgregar;
	private JRadioButton optEditar;
	private ComboBox<LibroCualificado> cboLibros;
	private JTextField txtTitulo;
	private JButton btnAgregarAutor;
	private JPanel pnlAutores;
	private ArrayList<Autor> autores;
	private ComboBox<Editorial> cboEditorial;
	private JTextField txtFecha;
	private JButton btnExplorador;
	private JFileChooser explorador;
	private JLabel lblArchivo;
	private JButton btnAgregarLibro;
	private JButton btnModificarLibro;
	private JButton btnLimpiar;
	private ArrayList<RegistroOpcional<Autor>> listadoAutores;
	
	/**
	 * Cambia la configuración a modo de inserción.
	 */
	private void modoInsercion() {
		cboLibros.setEnabled(false);
		
		limpiar();
	}
	
	/**
	 * Cambia la configuración a modo de edición
	 */
	private void modoEdicion() {
		cboLibros.setEnabled(true);
		
		mostrarLibroActivo();
	}
	
	/**
	 * Recupera el modo actual.
	 * @return {@link #MODO_EDICION} o {@link #MODO_INSERCION}
	 */
	private int modoActivo() {
		if(optAgregar.isSelected()) {
			return MODO_INSERCION;
		}
		
		return MODO_EDICION;
	}
	
	/**
	 * Agrega un nuevo registro de autor.
	 * @param seleccionado El autor seleccionado, este parametro puede ser
	 * nulo para un registro sin seleccion.
	 */
	private void agregarRegistroDeAutor(Autor seleccionado) {
		RegistroOpcional<Autor> nuevoAutor = new RegistroOpcional<Autor>();
		
		nuevoAutor.actualizarListadoDeObjetos(autores);
		nuevoAutor.setEventoEliminar(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listadoAutores.remove(nuevoAutor);
				pnlAutores.remove(nuevoAutor);
			}
		});
		
		if(seleccionado != null) {
			nuevoAutor.setObjectoSeleccionado(seleccionado);
		}
		
		listadoAutores.add(nuevoAutor);
		pnlAutores.add(nuevoAutor);
		
		pnlAutores.revalidate();
		pnlAutores.repaint();
	}
	
	/**
	 * Elimina todos los registros de autores.
	 */
	private void limpiarSeleccionDeAutores() {
		listadoAutores.clear();
		pnlAutores.removeAll();
		
		pnlAutores.revalidate();
		pnlAutores.repaint();
	}
	
	/**
	 * Muestra el libro seleccionado si la vista esta en modo edicion.
	 */
	private void mostrarLibroActivo() {
		LibroCualificado libroSeleccionado = cboLibros.getObjetoSeleccionado();
		
		if(modoActivo() != MODO_EDICION || libroSeleccionado == null) {
			return;
		}
		
		txtTitulo.setText(libroSeleccionado.getTitulo());
		cboEditorial.setObjetoSeleccionado(libroSeleccionado.getEditorial());
		txtFecha.setText(libroSeleccionado.getFechaDePublicacion());
		lblArchivo.setText(libroSeleccionado.getUbicacionDeArchivo());
		
		limpiarSeleccionDeAutores();
		
		for(Autor autor : libroSeleccionado.getAutores()) {
			agregarRegistroDeAutor(autor);
		}
	}
	
	/**
	 * Elimina los datos ingresados dejando todos los controles en su estado inicial.
	 */
	public void limpiar() {
		cboLibros.setObjetoSeleccionado(null);
		txtTitulo.setText("");
		cboEditorial.setObjetoSeleccionado(null);
		txtFecha.setText("");
		lblArchivo.setText("");
		
		limpiarSeleccionDeAutores();
	}
	
	public VistaLibros() {
		JPanel viewport = new JPanel();
		JScrollPane pnlBase = null;
		GridBagConstraints c = null;
		
		optAgregar = new JRadioButton("Agregar un nuevo libro");
		optEditar = new JRadioButton("Editar un registro existente");
		cboLibros = new ComboBox<LibroCualificado>();
		txtTitulo = new JTextField(20);
		btnAgregarAutor = new JButton("+");
		pnlAutores = new JPanel();
		autores = new ArrayList<Autor>();
		cboEditorial = new ComboBox<Editorial>();
		txtFecha = new JTextField(10);
		btnExplorador = new JButton("Buscar");
		explorador = new JFileChooser();
		lblArchivo = new JLabel();
		btnAgregarLibro = new JButton("Agregar");
		btnModificarLibro = new JButton("Modificar");
		btnLimpiar = new JButton("Limpiar");
		listadoAutores = new ArrayList<RegistroOpcional<Autor>>();
		
		//Configuracion de radio buttons
		ButtonGroup bg = new ButtonGroup();
		bg.add(optAgregar);
		bg.add(optEditar);
		
		optAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modoInsercion();
			}
		});
		
		optEditar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modoEdicion();
			}
		});
		
		optAgregar.doClick();
		
		//Configuracion del combobox
		cboLibros.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cboLibros.getObjetoSeleccionado() != null) {
					mostrarLibroActivo();
				}
			}
		});
		
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
				agregarRegistroDeAutor(null);
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
	public void definirLibros(List<LibroCualificado> pLibros) {
		cboLibros.limpiarListado();
		
		for(LibroCualificado l : pLibros) {
			cboLibros.agregarObjeto(l);
		}
	}
	
	/**
	 * Establece los autores.
	 * @param pAutores Lista de autores.
	 */
	public void definirAutores(List<Autor> pAutores) {
		autores.clear();
		autores.addAll(pAutores);
		
		for(RegistroOpcional<Autor> r : listadoAutores) {
			r.actualizarListadoDeObjetos(autores);;
		}
	}
	
	/**
	 * Establece las editoriales.
	 * @param pEditoriales Lista de editoriales.
	 */
	public void definirEditoriales(List<Editorial> pEditoriales) {
		cboEditorial.limpiarListado();
		
		for(Editorial e : pEditoriales) {
			cboEditorial.agregarObjeto(e);
		}
	}
	
	/**
	 * Recupera el libro que se busca modificar.
	 * @return El libro seleccionado o nulo si esta en modo de insercion.
	 */
	public LibroCualificado libroActivo() {
		if(modoActivo() != MODO_EDICION) {
			return null;
		}
		
		return cboLibros.getObjetoSeleccionado();
	}
	
	/**
	 * Recupera el titulo ingresado por el usuario.
	 * @return El titulo del libro.
	 */
	public String getTitulo() {
		return txtTitulo.getText();
	}
	
	/**
	 * Recupera la fecha de publicacion ingresada por el usuario.
	 * @return La fecha de publicacion.
	 */
	public String getFechaDePublicacion() {
		return txtFecha.getText();
	}
	
	/**
	 * Recupera la ubicacion absoluta del archivo PDF seleccionado por el usuario.
	 * @return Ubicacion absoluta.
	 */
	public String getUbicacionDeArchivo() {
		return lblArchivo.getText();
	}
	
	/**
	 * Recupera la editorial seleccionada por el usuario.
	 * @return La editorial seleccionada.
	 */
	public Editorial getEditorial() {
		return (Editorial) cboEditorial.getObjetoSeleccionado();
	}
	
	/**
	 * Recupera los autores seleccionados.
	 * @return Lista de autores seleccionados, la lista esta vacia 
	 * si no hay algun autor seleccionado.
	 */
	public List<Autor> getAutores(){
		ArrayList<Autor> autoresSeleccionados = new ArrayList<Autor>();
		
		for(RegistroOpcional<Autor> r : listadoAutores) {
			autoresSeleccionados.add(r.getObjetoSeleccionado());
		}
		
		return autoresSeleccionados;
	}
}
