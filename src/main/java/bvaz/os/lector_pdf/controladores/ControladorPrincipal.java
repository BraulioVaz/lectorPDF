package bvaz.os.lector_pdf.controladores;

import bvaz.os.lector_pdf.modelos.entidades.*;
import bvaz.os.lector_pdf.vistas.*;

public class ControladorPrincipal extends ControladorBase implements ObservadorDeSeleccion{
	private VistaInicio vista;
	
	public ControladorPrincipal(VistaInicio pVista) {
		super(pVista);
		
		vista = pVista;
		vista.agregarObservadorDeExplorador(this);
	}

	@Override
	public void nuevaSeleccion(Object nodo) {
		Libro libroSeleccionado = null;
		
		if(nodo instanceof Libro) {
			libroSeleccionado = (Libro) nodo;
			vista.agregarPestaña("Lector", new VisorPDF(libroSeleccionado.archivo));
		}
	}

}
