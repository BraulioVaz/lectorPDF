package bvaz.os.lector_pdf.vistas;

import javax.swing.JTree;
import javax.swing.tree.*;
import bvaz.os.lector_pdf.modelos.tda.Arbol;

class Explorador extends JTree{
	private static final long serialVersionUID = 1L;
	private Arbol estructura;
	
	public Explorador() {
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.setRootVisible(false);
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
}
