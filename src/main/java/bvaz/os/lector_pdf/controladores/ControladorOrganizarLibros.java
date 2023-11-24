package bvaz.os.lector_pdf.controladores;

import bvaz.os.lector_pdf.vistas.*;

public class ControladorOrganizarLibros extends ControladorBase{
	private VistaOrganizarLibros vista;
	
	public ControladorOrganizarLibros(VistaOrganizarLibros pVista) {
		super(pVista);
		
		vista = pVista;
	}
}
