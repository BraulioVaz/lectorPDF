package bvaz.os.lector_pdf;

import java.awt.*;
import javax.swing.*;
import bvaz.os.lector_pdf.vistas.*;

public class App 
{
    public static void main( String[] args )
    {
    	Ventana v = new Ventana();
    	
    	v.add(new VistaInicio());
    	v.setVisible(true);
    }
}

class Ventana extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public Ventana() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension tamPantalla = tk.getScreenSize();
		int alto, ancho, x, y;
		
		alto = 500;
		ancho = 800;
		x = (tamPantalla.width - ancho) / 2;
		y = (tamPantalla.height - alto) / 2;
		
		this.setSize(ancho, alto);
		this.setLocation(x, y);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}