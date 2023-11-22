package bvaz.os.lector_pdf.controladores;

import bvaz.os.lector_pdf.modelos.SistemaArchivos;
import bvaz.os.lector_pdf.modelos.entidades.*;
import bvaz.os.lector_pdf.vistas.*;

public class ControladorPrincipal extends ControladorBase implements ObservadorDeSeleccion, 
	ObservadorDeCambiosEnBD{
	private VistaInicio vista;
	
	public ControladorPrincipal(VistaInicio pVista) {
		super(pVista);
		
		vista = pVista;
		vista.agregarObservadorDeExplorador(this);
		
		actualizarExplorador();
	}
	
	public void actualizarExplorador() {
		vista.actualizarExplorador(SistemaArchivos.obtenerInstancia().estructurarArchivos());
	}

	@Override
	public void nuevaSeleccion(Object nodo) {
		Libro libroSeleccionado = null;
		
		if(nodo instanceof Libro) {
			libroSeleccionado = (Libro) nodo;
			vista.agregarPestaña("Lector", new VisorPDF(libroSeleccionado.archivo));
		}
	}
	
	@Override
	public void operacionDML() {
		actualizarExplorador();
	}

}
