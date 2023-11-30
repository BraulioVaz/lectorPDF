package bvaz.os.lector_pdf.controladores;

import java.awt.event.*;
import java.util.*;
import bvaz.os.lector_pdf.modelos.*;
import bvaz.os.lector_pdf.modelos.entidades.*;
import bvaz.os.lector_pdf.vistas.*;

public class ControladorOrganizarLibros extends ControladorBase implements ObservadorBD{
	private VistaOrganizarLibros vista;
	private DistribuidorLibros modeloLibros;
	private DistribuidorCarpetas modeloCarpetas;
	private ArrayList<ObservadorBD> oyentesBD;
	
	public ControladorOrganizarLibros(VistaOrganizarLibros pVista) {
		super(pVista);
		
		modeloLibros = new DistribuidorLibros();
		modeloCarpetas = new DistribuidorCarpetas();
		vista = pVista;
		oyentesBD = new ArrayList<ObservadorBD>();
		
		actualizarExplorador();
		actualizarListadoDeLibros();
		definirEventos();
	}
	
	private void asignarLibro() {
		Carpeta carpetaSeleccionada = null;
		Libro libroSeleccionado = null;
		Object seleccionDelExplorador = vista.seleccionDelExplorador();
		
		if(!(seleccionDelExplorador instanceof Carpeta)) {
			return;
		}
		
		carpetaSeleccionada = (Carpeta)seleccionDelExplorador;
		libroSeleccionado = vista.seleccionDelListado();
		
		if(libroSeleccionado == null) {
			return;
		}
		
		modeloCarpetas.registrarLibro(carpetaSeleccionada, libroSeleccionado);
		notificarOyentes();
		actualizarExplorador();
	}
	
	private void desasignarLibro() {
		Carpeta carpetaActiva = null;
		Libro libroSeleccionado = null;
		Object seleccionDelExplorador = vista.seleccionDelExplorador();
		Object carpetaActivaSinConvertir = vista.carpetaActivaDelExplorador();
		
		if(!(seleccionDelExplorador instanceof Libro)) {
			return;
		}
		
		if(carpetaActivaSinConvertir == null) {
			return;
		}
		
		libroSeleccionado = (Libro) seleccionDelExplorador;
		carpetaActiva = (Carpeta) vista.carpetaActivaDelExplorador();
		
		modeloCarpetas.quitarLibroDeCarpeta(carpetaActiva, libroSeleccionado);
		notificarOyentes();
		actualizarExplorador();
	}
	
	private void definirEventos() {
		vista.eventoAsignarLibro(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				asignarLibro();
			}
		});
		
		vista.eventoDesasignarLibro(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				desasignarLibro();
			}
		});
	}
	
	private void actualizarExplorador() {
		SistemaArchivos sa = SistemaArchivos.obtenerInstancia();
		vista.actualizarExplorador(sa.estructurarArchivos());
	}
	
	private void actualizarListadoDeLibros() {
		List<Libro> libros = modeloLibros.obtenerTodos();
		vista.definirLibros(libros);
	}
	
	@Override
	public void operacionDML() {
		actualizarListadoDeLibros();
		actualizarExplorador();
	}
	
	public void agregarOyente(ObservadorBD o) {
		oyentesBD.add(o);
	}
	
	private void notificarOyentes() {
		for(ObservadorBD o : oyentesBD) {
			o.operacionDML();
		}
	}
}
