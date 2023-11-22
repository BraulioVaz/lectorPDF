package bvaz.os.lector_pdf.controladores;

import java.awt.event.*;
import java.util.*;
import bvaz.os.lector_pdf.modelos.*;
import bvaz.os.lector_pdf.modelos.entidades.*;
import bvaz.os.lector_pdf.vistas.VistaLibros;

public class ControladorLibros extends ControladorBase implements ObservadorDeCambiosEnBD{
	private DistribuidorLibros modelo;
	private DistribuidorAutores autores;
	private DistribuidorEditoriales editoriales;
	private VistaLibros vista;
	private ArrayList<ObservadorDeCambiosEnBD> oyentesDeModificacionesEnBD;
	
	public ControladorLibros(VistaLibros pVista) {
		super(pVista);
		
		modelo = new DistribuidorLibros();
		autores = new DistribuidorAutores();
		editoriales = new DistribuidorEditoriales();
		oyentesDeModificacionesEnBD = new ArrayList<ObservadorDeCambiosEnBD>();
		vista = pVista;
		
		actualizarEntidadesExternas();
		actualizarListadoLibros();
		
		definirEventos();
	}
	
	private void actualizarEntidadesExternas() {
		vista.definirAutores(autores.obtenerTodos());
		vista.definirEditoriales(editoriales.obtenerTodos());
	}
	
	private void actualizarListadoLibros() {
		vista.definirLibros(modelo.obtenerTodos());
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
		Libro nuevoLibro = vista.generarLibro();
		boolean insercionExitosa = modelo.insertar(nuevoLibro);
		
		if(insercionExitosa) {
			vista.limpiar();
			vista.mostrarMensaje("Operacion exitosa", "Se ha agregado el libro correctamente.");
			actualizarListadoLibros();
			notificarCambioEnBD();
		}
		else {
			vista.mostrarError("Hubo un problema", "No se pudo agregar el libro.");
		}
	}
	
	public void agregarOyente(ObservadorDeCambiosEnBD o) {
		oyentesDeModificacionesEnBD.add(o);
	}
	
	private void notificarCambioEnBD() {
		for(ObservadorDeCambiosEnBD o : oyentesDeModificacionesEnBD) {
			o.operacionDML();
		}
	}
	
	@Override
	public void operacionDML() {
		actualizarEntidadesExternas();
	}
}
