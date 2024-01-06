package bvaz.os.lector_pdf.controladores;

import java.awt.event.*;
import java.util.*;
import bvaz.os.lector_pdf.modelos.*;
import bvaz.os.lector_pdf.modelos.entidades.*;
import bvaz.os.lector_pdf.modelos.vistas.*;
import bvaz.os.lector_pdf.vistas.VistaLibros;

public class ControladorLibros extends ControladorBase implements ObservadorBD{
	private DistribuidorLibros disLibros;
	private DistribuidorAutores disAutores;
	private DistribuidorEditoriales disEditoriales;
	private VistaLibros vista;
	private ArrayList<ObservadorBD> oyentesDeModificacionesEnBD;
	
	public ControladorLibros(VistaLibros pVista) {
		super(pVista);
		
		disLibros = new DistribuidorLibros();
		disAutores = new DistribuidorAutores();
		disEditoriales = new DistribuidorEditoriales();
		oyentesDeModificacionesEnBD = new ArrayList<ObservadorBD>();
		vista = pVista;
		
		actualizarEntidadesExternas();
		actualizarListadoLibros();
		
		definirEventos();
	}
	
	private LibroCualificado cualificarLibro(Libro pLibro) {
		Editorial editorial = null;
		ArrayList<Autor> autores = new ArrayList<Autor>();
		
		editorial = disEditoriales.buscarPorID(pLibro.editorial);
		
		for(Integer i : disLibros.autores(pLibro)) {
			Autor autor = disAutores.buscarPorID(i);
			
			if(autor != null) {
				autores.add(autor);
			}
		}
		
		return new LibroCualificado(pLibro, editorial, autores);
	}
	
	private void actualizarEntidadesExternas() {
		vista.definirAutores(disAutores.obtenerTodos());
		vista.definirEditoriales(disEditoriales.obtenerTodos());
	}
	
	private void actualizarListadoLibros() {
		ArrayList<LibroCualificado> libros = new ArrayList<LibroCualificado>();
		
		for(Libro l : disLibros.obtenerTodos()) {
			libros.add(this.cualificarLibro(l));
		}
		
		vista.definirLibros(libros);
	}
	
	private void definirEventos() {
		vista.definirEventoAgregar(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarLibro();
			}
		});
	}
	
	private void agregarLibro() {
		//TODO evisa la duplicacion de libros
		Libro nuevoLibro = new Libro();
		boolean insercionExitosa = false;
		
		nuevoLibro.titulo = vista.getTitulo();
		nuevoLibro.editorial = vista.getEditorial().id_editorial;
		nuevoLibro.fecha_publicacion = vista.getFechaDePublicacion();
		nuevoLibro.archivo = vista.getUbicacionDeArchivo();
		
		//TODO es necesario validar que los auotres son registros reales
		insercionExitosa = disLibros.insertar(nuevoLibro);
		
		if(insercionExitosa) {
			disLibros.registrarAutores(nuevoLibro, vista.getAutores());
			vista.limpiar();
			vista.mostrarMensaje("Operacion exitosa", "Se ha agregado el libro correctamente.");
			actualizarListadoLibros();
			notificarCambioEnBD();
		}
		else {
			vista.mostrarError("Hubo un problema", "No se pudo agregar el libro.");
		}
	}
	
	public void agregarOyente(ObservadorBD o) {
		oyentesDeModificacionesEnBD.add(o);
	}
	
	private void notificarCambioEnBD() {
		for(ObservadorBD o : oyentesDeModificacionesEnBD) {
			o.operacionDML();
		}
	}
	
	@Override
	public void operacionDML() {
		actualizarEntidadesExternas();
	}
}
