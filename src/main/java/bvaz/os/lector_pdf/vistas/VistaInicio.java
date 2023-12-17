package bvaz.os.lector_pdf.vistas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import bvaz.os.lector_pdf.modelos.tda.*;
import bvaz.os.lector_pdf.controladores.ObservadorDeSeleccion;
import info.clearthought.layout.*;

public class VistaInicio extends VistaBase{
	private static final long serialVersionUID = 1L;
	private Explorador explorador;
	private JTabbedPane pnlLibros;
	
	public VistaInicio() {
		JScrollPane pnlExplorador;
		
		explorador = new Explorador();
		
		//Layout
		double[][] cuadricula = {{0.20, 0.8}, {TableLayout.FILL}};
		this.setLayout(new TableLayout(cuadricula));
		
		//Explorador
		pnlExplorador = new JScrollPane(explorador);
		this.add(pnlExplorador, "0,0");
		
		//Seccion de libros
		pnlLibros = new JTabbedPane();
		this.add(pnlLibros, "1,0");
	}
	
	public void agregarPestaña(String nombre, VistaBase vista) {
		Tabulador tabulador = new Tabulador(pnlLibros, nombre, vista);
		int indice;
		
		pnlLibros.add(vista);
		indice = pnlLibros.indexOfComponent(vista);
		pnlLibros.setTabComponentAt(indice, tabulador);
	}
	
	public void actualizarExplorador(Arbol a) {
		explorador.definirEstructura(a);
		explorador.construir();
	}
	
	public void agregarObservadorDeExplorador(ObservadorDeSeleccion o) {
		explorador.agregarObservadorSeleccion(o);
	}
}

class Tabulador extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton btnCerrar;
	
	public Tabulador(JTabbedPane contenedor, String titulo, VistaBase componente) {
		btnCerrar = new JButton("X");
		
		btnCerrar.setContentAreaFilled(false);
		btnCerrar.setBorderPainted(false);
		btnCerrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				contenedor.remove(componente);
			}
			
		});
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		this.add(new JLabel(titulo));
		this.add(btnCerrar);
		
		this.setOpaque(false);
	}
}