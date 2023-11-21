package bvaz.os.lector_pdf.vistas;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import org.apache.pdfbox.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.rendering.*;

public class VisorPDF extends VistaBase{
	private static final long serialVersionUID = 1L;
	private JTextField txtPagina;
	private JLabel lblTotalPaginas;
	private JButton btnAumentarEscala;
	private JButton btnDisminuirEscala;
	private JTextField txtEscala;
	private PDDocument documento;
	private Lienzo lienzo;
	private int paginaActual;
	private int escalaActual;
	
	public VisorPDF() {
		txtPagina = new JTextField(2);
		lblTotalPaginas = new JLabel();
		btnAumentarEscala = new JButton("+");
		btnDisminuirEscala = new JButton("-");
		txtEscala = new JTextField(3);
		lienzo = new Lienzo();
		paginaActual = 0;
		escalaActual = 100;
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
		
		//Configuracion de zoom
		txtEscala.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int nuevaEscala = 0;
				
				try {
					nuevaEscala = Integer.parseInt(txtEscala.getText());
					cambiarEscala(nuevaEscala);
				}
				catch(Exception excepcion) {
					actualizarContadorEscala();
				}
			}
		});
		
		btnAumentarEscala.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				incrementarEscala();
			}
		});
		
		btnDisminuirEscala.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				disminuirEscala();
			}
		});
		
		txtEscala.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				actualizarContadorEscala();
			}
		});
		
		actualizarContadorEscala();
		
		String url = "PDF de prueba";
		cargarDocumento(url);
		
		this.setLayout(new BorderLayout());
		
		//Controles
		Box contenedor = null;
		int alturaMax = 30;
		
		contenedor = Box.createHorizontalBox();
		
		contenedor.setOpaque(true);
		contenedor.setBackground(Color.LIGHT_GRAY);
		txtPagina.setMaximumSize(new Dimension(30, alturaMax));
		txtEscala.setMaximumSize(new Dimension(30, alturaMax));
		
		contenedor.add(Box.createHorizontalGlue());
		contenedor.add(txtPagina);
		contenedor.add(Box.createRigidArea(new Dimension(10, alturaMax)));
		contenedor.add(this.crearEtiqueta("/"));
		contenedor.add(Box.createRigidArea(new Dimension(10, alturaMax)));
		contenedor.add(lblTotalPaginas);
		contenedor.add(Box.createRigidArea(new Dimension(30, alturaMax)));
		contenedor.add(btnDisminuirEscala);
		contenedor.add(Box.createRigidArea(new Dimension(10, alturaMax)));
		contenedor.add(txtEscala);
		contenedor.add(this.crearEtiqueta("%"));
		contenedor.add(Box.createRigidArea(new Dimension(10, 40)));
		contenedor.add(btnAumentarEscala);
		contenedor.add(Box.createHorizontalGlue());
		
		this.add(contenedor, BorderLayout.NORTH);
		
		//Lienzo
		lienzo.actualizar();
		this.add(lienzo, BorderLayout.CENTER);
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
	
	/**
	 * Cambia de pagina y actualiza el lienzo. Si la nueva pagina es igual
	 * a la actual no realiza ningun cambio.
	 * @param nuevaPagina
	 */
	private void cambiarPagina(int nuevaPagina) {
		if(nuevaPagina == paginaActual) {
			return;
		}
		
		if(nuevaPagina < 0 || nuevaPagina > documento.getNumberOfPages()) {
			throw new IllegalArgumentException();
		}
		
		paginaActual = nuevaPagina;
		actualizarContadorDePagina();
		lienzo.actualizar();
	}
	
	/**
	 * Cambia a la siguiente pagina validando que sea posible.
	 */
	private void siguientePagina() {
		if(paginaActual == documento.getNumberOfPages()) {
			return;
		}
		
		cambiarPagina(paginaActual + 1);
	}
	
	/**
	 * Cambia a la pagina previa validando que sea posible.
	 */
	private void paginaPrevia() {
		if(paginaActual == 0) {
			return;
		}
		
		cambiarPagina(paginaActual - 1);
	}
	
	/**
	 * Cambia el texto en el contador de escala sin activar algun
	 * evento de cambio en la escala de la pagina.
	 */
	private void actualizarContadorEscala() {
		txtEscala.setText(String.valueOf(escalaActual));
	}
	
	/**
	 * Cambia la escala y actualiza el lienzo. Si la nueva escala es igual a 
	 * la actual no realiza ningun cambio.
	 * @param nuevaEscala
	 */
	private void cambiarEscala(int nuevaEscala) {
		if(escalaActual == nuevaEscala) {
			return;
		}
		
		if(nuevaEscala < 25 || nuevaEscala > 300) {
			throw new IllegalArgumentException();
		}
		
		escalaActual = nuevaEscala;
		actualizarContadorEscala();
		lienzo.actualizar();
	}
	
	/**
	 * Incrementa la escala en una cantidad determinada.
	 */
	private void incrementarEscala() {
		if(escalaActual > 275) {
			return;
		}
		
		cambiarEscala(escalaActual + 25);
	}
	
	/**
	 * Disminuye la escala en una cantidad determinada.
	 */
	private void disminuirEscala() {
		if(escalaActual < 50) {
			return;
		}
		
		cambiarEscala(escalaActual - 25);
	}
	
	private class Lienzo extends JPanel{
		private static final long serialVersionUID = 1L;
		private JLabel contenedorImagen;
		
		public Lienzo() {
			JPanel panel = new JPanel();
			JScrollPane panelDeslizable = new JScrollPane(panel);
			contenedorImagen = new JLabel();
			contenedorImagen.setHorizontalAlignment(SwingConstants.CENTER);
			
			/*Configuracion de eventos del panel deslizable*/
			
			//Obtención del foco
			panelDeslizable.addMouseListener(new MouseAdapter() {
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
			
			panelDeslizable.getInputMap(condicion).put(teclaSiguiente, "siguiente");
			panelDeslizable.getInputMap(condicion).put(teclaRetroceso, "previo");
			panelDeslizable.getActionMap().put("siguiente", siguientePagina);
			panelDeslizable.getActionMap().put("previo", paginaPrevia);
			
			/*Politicas de barras de desplazamiento*/
			panelDeslizable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
			/*Centrado de la imagen*/
			panel.setLayout(new BorderLayout());
			panel.add(contenedorImagen, BorderLayout.CENTER);
			
			this.setLayout(new GridLayout(1,1));
			this.add(panelDeslizable);
		}
		
		private BufferedImage renderizarPagina() {
			PDFRenderer render = null;
			BufferedImage imgPagina = null;
			Image imgIntermedia = null;
			int ancho, alto;
			
			if(documento == null) {
				return null;
			}
			
			render = new PDFRenderer(documento);
			
			try {
				imgPagina = render.renderImage(paginaActual);
				ancho = (int) (imgPagina.getWidth() * (escalaActual / 100.0));
				alto = (int) (imgPagina.getHeight() * (escalaActual / 100.0));
				
				imgIntermedia = imgPagina.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
				imgPagina = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
				imgPagina.getGraphics().drawImage(imgIntermedia, 0, 0, null);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			
			return imgPagina;
		}
		
		public void actualizar() {
			ImageIcon icono = null;
			Image imgPagina = renderizarPagina();
			
			if(imgPagina == null) {
				return;
			}
			
			icono = new ImageIcon(imgPagina);
			contenedorImagen.setIcon(icono);
		}
	}
}
