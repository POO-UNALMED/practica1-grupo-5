package Entidades;

public class Pago {
	private float valor;
	private boolean temporadaAlta;
	private Cliente cliente;

	public Pago(float valor, boolean temporadaAlta, Cliente cliente) {
		this.valor = valor;
		this.temporadaAlta = temporadaAlta;
		this.cliente = cliente;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public boolean isTemporadaAlta() {
		return temporadaAlta;
	}

	public void setTemporadaAlta(boolean temporadaAlta) {
		this.temporadaAlta = temporadaAlta;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
