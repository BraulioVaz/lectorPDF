package bvaz.os.lector_pdf.controladores;

import java.awt.event.*;
import bvaz.os.lector_pdf.modelos.entidades.Carpeta;
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
