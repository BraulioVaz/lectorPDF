package bvaz.os.lector_pdf.vistas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VistaInicio extends VistaBase{
	private static final long serialVersionUID = 1L;
	private Explorador explorador;
	private JTabbedPane pnlLibros;
	
	public VistaInicio() {
		JScrollPane pnlExplorador;
		GridBagConstraints c;
		
		explorador = new Explorador();
		
		this.setLayout(new GridBagLayout());
		
		//Explorador
		pnlExplorador = new JScrollPane(explorador);
		c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.15;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		this.add(pnlExplorador, c);
		
		//Seccion de libros
		pnlLibros = new JTabbedPane();
		c = new GridBagConstraints();
		
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.85;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		this.add(pnlLibros, c);
	}
	
	public void agregarPestaña(String nombre, VistaBase vista) {
		Tabulador tabulador = new Tabulador(pnlLibros, nombre, vista);
		int indice;
		
		pnlLibros.add(vista);
		indice = pnlLibros.indexOfComponent(vista);
		pnlLibros.setTabComponentAt(indice, tabulador);
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