package bvaz.os.lector_pdf.controladores;

import bvaz.os.lector_pdf.modelos.DistribuidorAutores;
import bvaz.os.lector_pdf.modelos.DistribuidorEditoriales;
import bvaz.os.lector_pdf.vistas.VistaLibros;

public class ControladorLibros extends ControladorBase{
	private DistribuidorAutores autores;
	private DistribuidorEditoriales editoriales;
	private VistaLibros vista;
	
	public ControladorLibros(VistaLibros pVista) {
		super(pVista);
		
		autores = new DistribuidorAutores();
		editoriales = new DistribuidorEditoriales();
		vista = pVista;
		
		vista.definirAutores(autores.obtenerTodos());
		vista.definirEditoriales(editoriales.obtenerTodos());
	}

}
