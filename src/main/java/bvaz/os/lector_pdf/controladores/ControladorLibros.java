package bvaz.os.lector_pdf.controladores;

import java.awt.event.*;
import bvaz.os.lector_pdf.modelos.*;
import bvaz.os.lector_pdf.modelos.entidades.*;
import bvaz.os.lector_pdf.vistas.VistaLibros;

public class ControladorLibros extends ControladorBase{
	private DistribuidorLibros modelo;
	private DistribuidorAutores autores;
	private DistribuidorEditoriales editoriales;
	private VistaLibros vista;
	
	public ControladorLibros(VistaLibros pVista) {
		super(pVista);
		
		modelo = new DistribuidorLibros();
		autores = new DistribuidorAutores();
		editoriales = new DistribuidorEditoriales();
		vista = pVista;
		
		vista.definirAutores(autores.obtenerTodos());
		vista.definirEditoriales(editoriales.obtenerTodos());
		
		definirEventos();
	}
	
	private void definirEventos() {
		vista.definirEventoAgregar(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarLibro();
			}
		});
	}
	
	public void agregarLibro() {
		//TODO Actualiza la vista y revisa la duplicacion de libros
		Libro nuevoLibro = vista.generarLibro();
		modelo.insertar(nuevoLibro);
	}
}
