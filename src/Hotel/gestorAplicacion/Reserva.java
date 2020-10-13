package gestorAplicacion;

import java.util.Date;

public class Reserva {
	private static int numReserva;
	private Date fecha;
	private Cliente cliente;
	private Habitacion habitacion;
	private final static double costo=12;
	private Pago pago;
	private int id;
	private boolean confirmacion=false;

	public Reserva(Date fecha, Cliente cliente, Habitacion habitacion) {
		numReserva++;
		id=numReserva;
		this.fecha = fecha;
		this.cliente=cliente;
		this.habitacion=habitacion;
	}

	public static int getNumReserva() {
		return numReserva;
	}

	public static void setNumReserva(int numReserv) {
		numReserva = numReserv;
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

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// METODOS ADICIONALES alias FUNCIONALIDADES

	public boolean verificarDispo() {
		return habitacion.isDisponible();
	}

	public String alquilarHabitacion() {
		if (this.verificarDispo() == true) {
			confirmacion=true;
			habitacion.setDisponible(false);
			return "Reserva exitosa";
		} else {
			return "Habitacion no disponible";
		}
	}

	public Pago establecerPago() {
		if (confirmacion) {
			pago = new Pago(habitacion.valor(), true, this);
			return pago;
		}
		else {
			pago = new Pago(costo, true, this);
			return pago;
		}
	}
	public void cancelar() {
		confirmacion=false;
		this.establecerPago();
	}
}
