package bvaz.os.lector_pdf.controladores;

import java.awt.event.*;
import java.util.*;
import bvaz.os.lector_pdf.modelos.entidades.Autor;
import bvaz.os.lector_pdf.modelos.DistribuidorAutores;
import bvaz.os.lector_pdf.vistas.VistaAutores;

public class ControladorAutores extends ControladorBase{
	private DistribuidorAutores modelo;
	private VistaAutores vista;
	private ArrayList<ObservadorBD> oyentesDeModificacionesEnBD;
	
	public ControladorAutores(VistaAutores pVista) {
		super(pVista);
		
		modelo = new DistribuidorAutores();
		oyentesDeModificacionesEnBD = new ArrayList<ObservadorBD>();
		vista = pVista;
		
		definirEventos();
		actualizarTablaAutores();
	}
	
	private void definirEventos() {
		vista.definirEventoAgregar(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agregarAutor();
			}
		});
		
		vista.definirEventoEliminar(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminarAutor();
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
	
	private void eliminarAutor() {
		Autor autorSeleccionado = vista.autorSeleccionado();
		boolean operacionExitosa = false;
		
		if(autorSeleccionado == null) {
			vista.mostrarError("Operacion invalida", "Debe seleccionar un autor");
			return;
		}
		
		operacionExitosa = modelo.eliminar(autorSeleccionado.id_autor);
		
		if(operacionExitosa) {
			vista.mostrarMensaje("Operación exitosa", "Se ha eliminado el autor correctamente.");
			actualizarTablaAutores();
			notificarCambioEnBD();
		}
		else {
			vista.mostrarError("Hubo un problema", "No se ha podido eliminar el autor.");
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
}
