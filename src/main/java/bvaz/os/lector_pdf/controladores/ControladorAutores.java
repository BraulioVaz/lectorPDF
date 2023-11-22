package bvaz.os.lector_pdf.controladores;

import java.awt.event.*;
import java.util.*;
import bvaz.os.lector_pdf.modelos.DistribuidorAutores;
import bvaz.os.lector_pdf.vistas.VistaAutores;

public class ControladorAutores extends ControladorBase{
	private DistribuidorAutores modelo;
	private VistaAutores vista;
	private ArrayList<ObservadorDeCambiosEnBD> oyentesDeModificacionesEnBD;
	
	public ControladorAutores(VistaAutores pVista) {
		super(pVista);
		
		modelo = new DistribuidorAutores();
		oyentesDeModificacionesEnBD = new ArrayList<ObservadorDeCambiosEnBD>();
		vista = pVista;
		
		definirEventos();
		actualizarTablaAutores();
	}
	
	private void definirEventos() {
		vista.definirEventoAgregar(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				agregarAutor();
			}
		});
	}
	
	private void actualizarTablaAutores() {
		vista.llenarTabla(modelo.obtenerTodos());
	}
	
	private void agregarAutor() {
		boolean operacionExitosa = modelo.insertar(vista.nuevoAutor());
		
		if(operacionExitosa) {
			vista.mostrarMensaje("Operación exitosa", "Se ha agregado el autor correctamente.");
			actualizarTablaAutores();
			notificarCambioEnBD();
		}
		else {
			vista.mostrarError("Error", "No se pudo insertar el autor");
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
}
