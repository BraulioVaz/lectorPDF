package bvaz.os.lector_pdf.vistas;

import java.awt.*;
import javax.swing.*;

public class VistaBase extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private float tamGeneral;
	private float tamTitulos;

	public VistaBase() {
		tamGeneral = 18.0f;
		tamTitulos = 24.0f;
	}
	
	public void mostrarMensaje(String titulo, String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.PLAIN_MESSAGE);
	}
	
	public void mostrarAlerta(String titulo, String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.WARNING_MESSAGE);
	}
	
	public void mostrarError(String titulo, String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
	}
	
	protected JLabel crearEtiqueta(String mensaje) {
		JLabel etiqueta = new JLabel(mensaje);
		Font fuente = etiqueta.getFont();
		
		fuente = fuente.deriveFont(tamGeneral);
		etiqueta.setFont(fuente);
		
		return etiqueta;
	}
	
	protected JLabel crearTitulo(String mensaje) {
		JLabel etiqueta = new JLabel(mensaje);
		Font fuente = etiqueta.getFont();
		
		fuente = fuente.deriveFont(tamTitulos);
		etiqueta.setFont(fuente);
		
		return etiqueta;
	}
}
