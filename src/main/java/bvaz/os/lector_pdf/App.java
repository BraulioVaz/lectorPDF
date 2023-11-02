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
    	ControladorAutores cAutores;
    	ControladorEditoriales cEditoriales;
    	ControladorLibros cLibros;
    	ControladorCarpetas cCarpetas;
    	Ventana v = new Ventana();
    	
    	cAutores = new ControladorAutores(new VistaAutores());
    	cEditoriales = new ControladorEditoriales(new VistaEditoriales());
    	cLibros = new ControladorLibros(new VistaLibros());
    	cCarpetas = new ControladorCarpetas(new VistaCarpetas());
    	
    	v.agregarMenu("Autores", cAutores.getVista());
    	v.agregarMenu("Editoriales", cEditoriales.getVista());
    	v.agregarMenu("Libros", cLibros.getVista());
    	v.agregarMenu("Carpetas", cCarpetas.getVista());
    	
    	v.setVisible(true);
    }
}

class Ventana extends JFrame{
	private static final long serialVersionUID = 1L;
	private JMenuBar menu;
	private VistaInicio base;
	
	public Ventana() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension tamPantalla = tk.getScreenSize();
		int alto, ancho, x, y;
		
		alto = 500;
		ancho = 800;
		x = (tamPantalla.width - ancho) / 2;
		y = (tamPantalla.height - alto) / 2;
		
		menu = new JMenuBar();
		this.setJMenuBar(menu);
		
		base = new VistaInicio();
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
