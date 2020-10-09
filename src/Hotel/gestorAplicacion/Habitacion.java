package gestorAplicacion;

import java.util.Date;

public class Habitacion {
	private int numero;
	private Date fechaInicial;
	private Date fechaFin;
	private String tipo;
	private int precioDia;
	private boolean disponible;
	private Cliente cliente;

	public Habitacion(int numero, Date fechaInicial, Date fechaFin, String tipo, int precioDia, boolean disponible,
			Cliente cliente) {
		this.numero = numero;
		this.fechaInicial = fechaInicial;
		this.fechaFin = fechaFin;
		this.tipo = tipo;
		this.precioDia = precioDia;
		this.disponible = disponible;
		this.cliente = cliente;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getPrecioDia() {
		return precioDia;
	}

	public void setPrecioDia(int precioDia) {
		this.precioDia = precioDia;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
