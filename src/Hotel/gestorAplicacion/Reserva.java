package gestorAplicacion;

import java.util.Date;

public class Reserva {
	private int numReserva;
	private Date fecha;
	private Cliente cliente;
	private Habitacion habitacion;
	private double costo;
	private Pago pago;
	private static int id;

	public Reserva(Date fecha, Cliente cliente, Habitacion habitacion) {
		this.numReserva = id;
		this.fecha = fecha;
		this.reservarHabitacion(cliente, habitacion);
		id++;
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

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	// METODOS ADICIONALES alias FUNCIONALIDADES

	public boolean verificarDispo() {
		return habitacion.isDisponible();
	}

	public String reservarHabitacion(Cliente cliente, Habitacion habitacion) {
		if (this.verificarDispo() == true) {
			this.cliente = cliente;
			this.habitacion = habitacion;
			habitacion.setDisponible(false);
			return "Reserva exitosa";
		} else {
			return "Habitacion no disponible";
		}
	}

	public Pago establecerPago() {
		if (habitacion.getTipo() == "Sencilla") {
			costo = 80000;
			pago = new Pago(costo, true, this);
			return pago;
		} else if (habitacion.getTipo() == "Familiar") {
			costo = 195000;
			pago = new Pago(costo, true, this);
			return pago;
		} else {
			costo = 350000;
			pago = new Pago(costo, true, this);
			return pago;
		}
	}
}
