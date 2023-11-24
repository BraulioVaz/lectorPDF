package bvaz.os.lector_pdf;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import bvaz.os.lector_pdf.controladores.*;
import bvaz.os.lector_pdf.vistas.*;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	ControladorPrincipal cPrincipal;
    	ControladorAutores cAutores;
    	ControladorEditoriales cEditoriales;
    	ControladorLibros cLibros;
    	ControladorCarpetas cCarpetas;
    	Ventana ventana;
    	
    	cPrincipal = new ControladorPrincipal(new VistaInicio());
    	cAutores = new ControladorAutores(new VistaAutores());
    	cEditoriales = new ControladorEditoriales(new VistaEditoriales());
    	cLibros = new ControladorLibros(new VistaLibros());
    	cCarpetas = new ControladorCarpetas(new VistaCarpetas());
    	
    	/*Establecen relaciones Observador-Observado*/
    	cLibros.agregarOyente(cPrincipal);
    	cAutores.agregarOyente(cLibros);
    	cEditoriales.agregarOyente(cLibros);
    	cCarpetas.agregarOyente(cPrincipal);
    	
    	/*Definicion del menu*/
    	ventana = new Ventana((VistaInicio) cPrincipal.getVista());
    	ventana.agregarMenu("Autores", cAutores.getVista());
    	ventana.agregarMenu("Editoriales", cEditoriales.getVista());
    	ventana.agregarMenu("Libros", cLibros.getVista());
    	ventana.agregarMenu("Carpetas", cCarpetas.getVista());
    	
    	ventana.setVisible(true);
    }
}

class Ventana extends JFrame{
	private static final long serialVersionUID = 1L;
	private JMenuBar menu;
	private VistaInicio base;
	
	public Ventana(VistaInicio vistaBase) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension tamPantalla = tk.getScreenSize();
		int alto, ancho, x, y;
		
		alto = 500;
		ancho = 800;
		x = (tamPantalla.width - ancho) / 2;
		y = (tamPantalla.height - alto) / 2;
		
		menu = new JMenuBar();
		this.setJMenuBar(menu);
		
		base = vistaBase;
		this.add(base);
		
		this.setSize(ancho, alto);
		this.setLocation(x, y);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void agregarMenu(String nombre, VistaBase vista) {
		JMenu item = new JMenu(nombre);
		
		item.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				base.agregarPestaña(nombre, vista);
			}
		});
		
		menu.add(item);
	}
}
