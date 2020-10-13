package gestorAplicacion;

import java.util.Date;

public class Reserva {
	private static int numReserva;
	private Date fecha;
	private Cliente cliente;
	private Habitacion habitacion;
	private double costo = 0.1;

	public Reserva() {
		numReserva++;
	}
	public Reserva(Date fecha, Cliente cliente, Habitacion habitacion) {
		this.fecha = fecha;
		this.reservarHabitacion(cliente, habitacion);
		numReserva++;
	}

	public static int getNumReserva() {
		return numReserva;
	}

	public void setNumReserva(int numReserva) {
		Reserva.numReserva = numReserva;
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

	//METODOS ADICIONALES alias FUNCIONALIDADES
	
	public boolean verificarDispo(Habitacion habit){
		return habit.isDisponible();
	}
	
	public String reservarHabitacion(Cliente cliente, Habitacion habitacion){
		if (this.verificarDispo(habitacion) == true) {
			this.cliente = cliente;
			this.habitacion = habitacion;
			habitacion.setDisponible(false);
			return "Reserva exitosa";
		}
		else {
			return "Habitacion no disponible";
		}
	}
}
