package bvaz.os.lector_pdf.vistas;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import org.apache.pdfbox.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.rendering.*;

public class VisorPDF extends VistaBase{
	private static final long serialVersionUID = 1L;
	private JTextField txtPagina;
	private JLabel lblTotalPaginas;
	private JButton btnAumentarZoom;
	private JButton btnDisminuirZoom;
	private JTextField txtZoom;
	private PDDocument documento;
	private Lienzo lienzo;
	private int paginaActual;
	
	public VisorPDF() {
		txtPagina = new JTextField(2);
		lblTotalPaginas = new JLabel();
		btnAumentarZoom = new JButton("+");
		btnDisminuirZoom = new JButton("-");
		txtZoom = new JTextField(3);
		lienzo = new Lienzo();
		paginaActual = 0;
		documento = null;
		
		//Configuracion de contador de paginas
		txtPagina.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int nuevaPagina = Integer.parseInt(txtPagina.getText());
					cambiarPagina(nuevaPagina - 1);
				}
				catch(Exception excepcion) {
					actualizarContadorDePagina();
				}
				
			}
			
		});
		
		txtPagina.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				actualizarContadorDePagina();
			}
		});
		
		actualizarContadorDePagina();
		
		String url = "PDF de prueba";
		cargarDocumento(url);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//Controles
		Box contenedor = null;
		int alturaMax = 30;
		
		contenedor = Box.createHorizontalBox();
		
		contenedor.setOpaque(true);
		contenedor.setBackground(Color.LIGHT_GRAY);
		txtPagina.setMaximumSize(new Dimension(30, alturaMax));
		txtZoom.setMaximumSize(new Dimension(30, alturaMax));
		
		contenedor.add(Box.createHorizontalGlue());
		contenedor.add(txtPagina);
		contenedor.add(Box.createRigidArea(new Dimension(10, alturaMax)));
		contenedor.add(this.crearEtiqueta("/"));
		contenedor.add(Box.createRigidArea(new Dimension(10, alturaMax)));
		contenedor.add(lblTotalPaginas);
		contenedor.add(Box.createRigidArea(new Dimension(30, alturaMax)));
		contenedor.add(btnDisminuirZoom);
		contenedor.add(Box.createRigidArea(new Dimension(10, alturaMax)));
		contenedor.add(txtZoom);
		contenedor.add(this.crearEtiqueta("%"));
		contenedor.add(Box.createRigidArea(new Dimension(10, 40)));
		contenedor.add(btnAumentarZoom);
		contenedor.add(Box.createHorizontalGlue());
		
		this.add(contenedor);
		
		//Lienzo
		this.add(lienzo);
	}
	
	private void cargarDocumento(String url) {
		File libro = null;
		
		try {
			libro = new File(url);
			documento = Loader.loadPDF(libro);
			lblTotalPaginas.setText(String.valueOf(documento.getNumberOfPages()));
		}
		catch(Exception e) {
			this.mostrarError("Error en carga", "No se puede encontrar el libro.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Establece el texto del contador de pagina al de la pagina actual.
	 * No activa ningun evento de cambio de pagina.
	 */
	private void actualizarContadorDePagina() {
		int paginaReal = paginaActual + 1;
		txtPagina.setText(String.valueOf(paginaReal));
	}
	
	private void cambiarPagina(int nuevaPagina) {
		if(nuevaPagina == paginaActual) {
			return;
		}
		
		if(nuevaPagina < 0 || nuevaPagina > documento.getNumberOfPages()) {
			throw new IllegalArgumentException();
		}
		
		paginaActual = nuevaPagina;
		actualizarContadorDePagina();
		lienzo.repaint();
	}
	
	private void siguientePagina() {
		if(paginaActual == documento.getNumberOfPages()) {
			return;
		}
		
		paginaActual++;
		actualizarContadorDePagina();
		lienzo.repaint();
	}
	
	private void paginaPrevia() {
		if(paginaActual == 0) {
			return;
		}
		
		paginaActual--;
		actualizarContadorDePagina();
		lienzo.repaint();
	}
	
	private class Lienzo extends JPanel{
		private static final long serialVersionUID = 1L;
		
		public Lienzo() {
			//Eventos
			
			/*Obtencion del foco*/
			this.setFocusable(true);
			
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					requestFocusInWindow();
				}
			});
			
			/*Cambio de pagina por teclado*/
			Action siguientePagina = new AbstractAction() {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					siguientePagina();
				}
			};
			
			Action paginaPrevia = new AbstractAction() {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent arg0) {
					paginaPrevia();
				}
			};
			
			int condicion = JComponent.WHEN_IN_FOCUSED_WINDOW;
			KeyStroke teclaSiguiente = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
			KeyStroke teclaRetroceso = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
			
			this.getInputMap(condicion).put(teclaSiguiente, "siguiente");
			this.getInputMap(condicion).put(teclaRetroceso, "previo");
			this.getActionMap().put("siguiente", siguientePagina);
			this.getActionMap().put("previo", paginaPrevia);
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;
			PDFRenderer render = null;
			
			if(documento == null) {
				return;
			}
			
			render = new PDFRenderer(documento);
			
			try {
				render.renderPageToGraphics(paginaActual, g2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
}
