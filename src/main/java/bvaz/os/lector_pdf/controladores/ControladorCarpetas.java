package bvaz.os.lector_pdf.controladores;

import java.awt.event.*;
import java.util.*;
import bvaz.os.lector_pdf.modelos.entidades.Carpeta;
import bvaz.os.lector_pdf.modelos.tda.Arbol;
import bvaz.os.lector_pdf.modelos.DistribuidorCarpetas;
import bvaz.os.lector_pdf.modelos.SistemaArchivos;
import bvaz.os.lector_pdf.vistas.*;

public class ControladorCarpetas extends ControladorBase{
	private VistaCarpetas vista;
	private DistribuidorCarpetas modelo;
	private ArrayList<ObservadorDeCambiosEnBD> oyentesDeModificacionesBD;
	
	public ControladorCarpetas(VistaCarpetas pVista) {
		super(pVista);
		
		vista = pVista;
		modelo = new DistribuidorCarpetas();
		oyentesDeModificacionesBD = new ArrayList<ObservadorDeCambiosEnBD>();
		
		definirEventos();
		actualizarListadoDeCarpetas();
		actualizarExplorador();
		
		//TODO Configurar para que actualice la vista inicial
	}
	
	private void definirEventos() {
		vista.definirEventoCrearCarpeta(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearCarpeta();
			}
		});
		
		vista.definirEventoAsignarCarpeta(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Carpeta supercarpeta = vista.elementoActivoDelExplorador();
				Carpeta subcarpeta = vista.elementoActivoDelListado();
				
				asignarSubcarpeta(supercarpeta, subcarpeta);
			}
		});
		
		vista.definirEventoDesasignarCarpeta(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Carpeta subcarpeta = vista.elementoActivoDelExplorador();
				
				desasignarSubcarpeta(subcarpeta);
			}
		});
	}
	
	private void actualizarListadoDeCarpetas() {
		vista.definirCarpetas(modelo.obtenerTodos());
	}
	
	private void actualizarExplorador() {
		Arbol raiz = SistemaArchivos.obtenerInstancia().estructurarCarpetas();
		vista.actualizarExplorador(raiz);
	}
	
	private void crearCarpeta() {
		Carpeta nuevaCarpeta = new Carpeta();
		boolean operacionExitosa = false;
		
		nuevaCarpeta.nombre = vista.nombreIntroducido();
		operacionExitosa = modelo.insertar(nuevaCarpeta);
		
		if(operacionExitosa) {
			vista.mostrarMensaje("Operación exitosa", "Se ha agregado la carpeta correctamente");
			actualizarListadoDeCarpetas();
			actualizarExplorador();
			notificarCambioEnBD();
		}
		else {
			vista.mostrarError("Error", "No se ha podido crear la carpeta");
		}
	}
	
	private void asignarSubcarpeta(Carpeta supercarpeta, Carpeta subcarpeta) {
		boolean operacionExitosa = false;
		
		if(supercarpeta == null) {
			return;
		}
		
		subcarpeta.raiz = supercarpeta.id_carpeta;
		operacionExitosa = modelo.actualizar(subcarpeta);
		
		if(operacionExitosa) {
			actualizarExplorador();
			notificarCambioEnBD();
		}
	}
	
	private void desasignarSubcarpeta(Carpeta subcarpeta) {
		boolean operacionExitosa = false;
		Integer[] idSupercarpeta = {subcarpeta.raiz};
		Carpeta supercarpeta = modelo.buscarPorID(idSupercarpeta);
		
		subcarpeta.raiz = supercarpeta.raiz;
		operacionExitosa = modelo.actualizar(subcarpeta);
		
		if(operacionExitosa) {
			actualizarExplorador();
			notificarCambioEnBD();
		}
	}
	
	public void agregarOyente(ObservadorDeCambiosEnBD o) {
		oyentesDeModificacionesBD.add(o);
	}
	
	private void notificarCambioEnBD() {
		for(ObservadorDeCambiosEnBD o : oyentesDeModificacionesBD) {
			o.operacionDML();
		}
	}
}
