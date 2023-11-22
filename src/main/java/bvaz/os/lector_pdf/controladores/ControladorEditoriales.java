package bvaz.os.lector_pdf.controladores;

import java.awt.event.*;
import java.util.*;
import bvaz.os.lector_pdf.modelos.DistribuidorEditoriales;
import bvaz.os.lector_pdf.vistas.VistaEditoriales;

public class ControladorEditoriales extends ControladorBase{
	private DistribuidorEditoriales modelo;
	private VistaEditoriales vista;
	private ArrayList<ObservadorDeCambiosEnBD> oyentesDeModificacionesEnBD;
	
	public ControladorEditoriales(VistaEditoriales pVista) {
		super(pVista);
		
		modelo = new DistribuidorEditoriales();
		oyentesDeModificacionesEnBD = new ArrayList<ObservadorDeCambiosEnBD>();
		vista = pVista;
		
		vista.setEventoAgregarEditorial(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarEditorial();
			}
		});
		
		actualizarEditoriales();
	}
	
	private void actualizarEditoriales() {
		vista.setEditoriales(modelo.obtenerTodos());
	}
	
	private void agregarEditorial() {
		modelo.insertar(vista.getNuevaEditorial());
		
		actualizarEditoriales();
		notificarCambioEnBD();
	}
	
	public void agregarOyente(ObservadorDeCambiosEnBD o) {
		oyentesDeModificacionesEnBD.add(o);
	}
	
	private void notificarCambioEnBD() {
		for(ObservadorDeCambiosEnBD o : oyentesDeModificacionesEnBD) {
			o.operacionDML();
		}
	}
}
