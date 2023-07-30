package bvaz.os.lector_pdf.modelos.tda;

public class Tupla<V, K> {
	public V valor;
	public K llave;
	
	public Tupla(V pValor, K pLlave) {
		this.valor = pValor;
		this.llave = pLlave;
	}
}
