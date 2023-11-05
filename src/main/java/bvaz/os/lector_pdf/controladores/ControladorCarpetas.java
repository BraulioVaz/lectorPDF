package bvaz.os.lector_pdf.controladores;

import java.awt.event.*;
import java.util.*;
import bvaz.os.lector_pdf.modelos.entidades.Carpeta;
import bvaz.os.lector_pdf.modelos.tda.Arbol;
import bvaz.os.lector_pdf.modelos.DistribuidorCarpetas;
import bvaz.os.lector_pdf.vistas.*;

public class ControladorCarpetas extends ControladorBase{
	private VistaCarpetas vista;
	private DistribuidorCarpetas modelo;
	
	public ControladorCarpetas(VistaCarpetas pVista) {
		super(pVista);
		
		vista = pVista;
		modelo = new DistribuidorCarpetas();
		
		definirEventos();
		actualizarListadoDeCarpetas();
		actualizarExplorador();
	}
	
	private void definirEventos() {
		vista.definirEventoCrearCarpeta(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearCarpeta();
			}
		});
	}
	
	private void actualizarListadoDeCarpetas() {
		vista.definirCarpetas(modelo.obtenerTodos());
	}
	
	private void actualizarExplorador() {
		List<Carpeta> carpetas = modelo.obtenerTodos();
		Arbol raiz = new Arbol("Raiz");
		ArrayList<Arbol> nodosSinRevisar = new ArrayList<Arbol>();
		Arbol nodoActual, subarbol;
		Carpeta contenidoNodo;
		
		for(Carpeta c : carpetas) {
			if(c.raiz == -1) {
				nodoActual = new Arbol(c);
				raiz.agregarHijo(nodoActual);
				nodosSinRevisar.add(nodoActual);
			}
		}
		
		while(!nodosSinRevisar.isEmpty()) {
			nodoActual = nodosSinRevisar.get(0);
			contenidoNodo = (Carpeta) nodoActual.raiz();
			
			for(Carpeta c : carpetas) {
				if(c.raiz == contenidoNodo.id_carpeta) {
					subarbol = new Arbol(c);
					nodoActual.agregarHijo(subarbol);
					nodosSinRevisar.add(subarbol);
				}
			}
			
			nodosSinRevisar.remove(0);
		}
		
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
		}
		else {
			vista.mostrarError("Error", "No se ha podido crear la carpeta");
		}
	}
}
