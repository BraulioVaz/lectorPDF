package bvaz.os.lector_pdf.vistas;

import java.awt.event.*;
import java.util.*;
import javax.swing.JTree;
import javax.swing.tree.*;
import bvaz.os.lector_pdf.modelos.tda.Arbol;
import bvaz.os.lector_pdf.controladores.ObservadorDeSeleccion;

class Explorador extends JTree{
	private static final long serialVersionUID = 1L;
	private Arbol estructura;
	private ArrayList<ObservadorDeSeleccion> oyentesDeSeleccion;
	
	public Explorador() {
		oyentesDeSeleccion = new ArrayList<ObservadorDeSeleccion>();
		
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.setRootVisible(false);
		
		/*Configuracion de eventos de seleccion*/
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int filaSeleccionada = getRowForLocation(e.getX(), e.getY());
				TreePath rutaAlNodo = getPathForLocation(e.getX(), e.getY());
				DefaultMutableTreeNode nodoSeleccionado;
				
				if(filaSeleccionada == -1) {
					return;
				}
				
				if(e.getClickCount() == 2) {
					for(ObservadorDeSeleccion o : oyentesDeSeleccion) {
						nodoSeleccionado = (DefaultMutableTreeNode) rutaAlNodo.getLastPathComponent();
						o.nuevaSeleccion(nodoSeleccionado.getUserObject());
					}
				}
			}
		});
	}
	
	public void definirEstructura(Arbol arbol) {
		this.estructura = arbol;
	}
	
	public void construir() {
		DefaultMutableTreeNode nodoRaiz = new DefaultMutableTreeNode(estructura.raiz());
		DefaultTreeModel modelo = new DefaultTreeModel(nodoRaiz);
		
		recorrerArbol(estructura, nodoRaiz);
		this.setModel(modelo);
	}
	
	private void recorrerArbol(Arbol arbol, DefaultMutableTreeNode nodoRaiz) {
		DefaultMutableTreeNode nodo = null;
		
		for(Arbol a : arbol.hijos()) {
			nodo = new DefaultMutableTreeNode(a.raiz());
			nodoRaiz.add(nodo);
			
			recorrerArbol(a, nodo);
		}
	}
	
	public Object elementoSeleccionado() {
		DefaultMutableTreeNode nodo;
		
		if(this.isSelectionEmpty()) {
			return null;
		}
		
		nodo = (DefaultMutableTreeNode) this.getLastSelectedPathComponent();
		return nodo.getUserObject();
	}
	
	public Object carpetaActiva() {
		DefaultMutableTreeNode nodoActual, nodoPadre;
		
		if(this.isSelectionEmpty()) {
			return null;
		}
		
		nodoActual = (DefaultMutableTreeNode) this.getLastSelectedPathComponent();
		nodoPadre = (DefaultMutableTreeNode) nodoActual.getParent();
		
		if(nodoPadre == null || nodoPadre.getUserObject().equals("RAIZ")) {
			return null;
		}
		
		return nodoPadre.getUserObject();
	}
	
	public void agregarObservadorSeleccion(ObservadorDeSeleccion o) {
		oyentesDeSeleccion.add(o);
	}
}
