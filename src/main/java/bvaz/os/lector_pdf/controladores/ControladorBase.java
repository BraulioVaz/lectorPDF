package bvaz.os.lector_pdf.controladores;

import bvaz.os.lector_pdf.vistas.VistaBase;

public abstract class ControladorBase {
	private VistaBase vista;
	
	public ControladorBase(VistaBase pVista) {
		vista = pVista;
	}
	
	public VistaBase getVista() {
		return vista;
	}
}
