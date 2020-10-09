package gestorAplicacion;

import java.util.Date;

public class Reserva {
	private int numReserva;
	private Date fecha;
	private Cliente cliente;
	private Habitacion habitacion;
	private int costo;

	public Reserva(int numReserva, Date fecha, Cliente cliente, Habitacion habitacion, int costo) {
		this.numReserva = numReserva;
		this.fecha = fecha;
		this.cliente = cliente;
		this.habitacion = habitacion;
		this.costo = costo;
	}

	public int getNumReserva() {
		return numReserva;
	}

	public void setNumReserva(int numReserva) {
		this.numReserva = numReserva;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

}
