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
	
	public VisorPDF(String url) {
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
		
		//Carga del PDF
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
		this.add(lienzo, BorderLayout.CENTER);
		lienzo.actualizar();
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
	
	private class Lienzo extends JScrollPane{
		private static final long serialVersionUID = 1L;
		private PanelDesplazable contenedorImagen;
		
		public Lienzo() {
			contenedorImagen = new PanelDesplazable();
			this.setViewportView(contenedorImagen);
			
			/*Nuevo layout manager en el viewport para que el
			 * panel desplazable siempre llene el viewport*/
			this.getViewport().setLayout(new ViewportLayout() {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void layoutContainer(Container pViewport) {
					JViewport viewport = (JViewport) pViewport;
					Scrollable vista = (Scrollable)viewport.getView();
					Dimension tamVista = vista.getPreferredScrollableViewportSize();
					Dimension tamViewport = viewport.getPreferredSize();
					
					if(!viewport.getSize().equals(new Dimension(0, 0))) {
						tamViewport = viewport.getSize();
					}
					
					if(tamVista.getWidth() < viewport.getWidth()) {
						tamVista = new Dimension(tamViewport.width, tamVista.height);
					}
					
					viewport.setViewSize(tamVista);
				}
			});
			
			//Obtención del foco
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Lienzo.this.requestFocusInWindow();
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
			
			InputMap mapaOriginal = (InputMap) UIManager.getDefaults().get("ScrollPane.ancestorInputMap");
			mapaOriginal.remove(teclaSiguiente);
			mapaOriginal.remove(teclaRetroceso);
			
			this.getInputMap(condicion).put(teclaSiguiente, "siguiente");
			this.getInputMap(condicion).put(teclaRetroceso, "previo");
			this.getActionMap().put("siguiente", siguientePagina);
			this.getActionMap().put("previo", paginaPrevia);
			
			/*Politicas de barras de desplazamiento*/
			this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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
				imgPagina = render.renderImageWithDPI(paginaActual, 124.0f);
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
			Image imgPagina = renderizarPagina();
			
			if(imgPagina == null) {
				return;
			}
			
			contenedorImagen.cambiarImagen(imgPagina);
		}
	}
	
	private class PanelDesplazable extends JPanel implements Scrollable{
		private static final long serialVersionUID = 1L;
		public JLabel imagen;
		private int desplazamiento;
		
		public PanelDesplazable() {
			imagen = new JLabel();
			desplazamiento = 50;
			
			this.add(imagen);
		}
		
		public void cambiarImagen(Image nuevaImagen) {
			imagen.setIcon(new ImageIcon(nuevaImagen));
		}
		
		@Override
		public Dimension getPreferredScrollableViewportSize() {
			return this.getPreferredSize();
		}

		@Override
		public int getScrollableBlockIncrement(Rectangle areaVisible, int orientacion, int direccion) {
			return desplazamiento;
		}

		@Override
		public boolean getScrollableTracksViewportHeight() {
			return false;
		}

		@Override
		public boolean getScrollableTracksViewportWidth() {
			return false;
		}

		@Override
		public int getScrollableUnitIncrement(Rectangle areaVisible, int orientacion, int direccion) {
			return desplazamiento;
		}
		
	}
}
