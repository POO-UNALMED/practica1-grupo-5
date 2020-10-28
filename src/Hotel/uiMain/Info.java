package uiMain;

import java.io.Serializable;

import gestorAplicacion.Hotel.Habitacion;
import gestorAplicacion.Hotel.Pago;
import gestorAplicacion.Hotel.Reserva;
import gestorAplicacion.Terceros.Cliente;

public class Info implements Serializable {

	private static final long serialVersionUID = 1L;
	// Cliente
	public int numClientes;
	// Habitacion
	public int numero;
	// Pago
	public double pagos;
	public double egreso;
	public double caja;
	// Reserva
	public int numReserva = Reserva.getNumReserva();

	public Info() {
		numClientes = Cliente.getNumClientes();
		// Habitacion
		numero = Habitacion.getNumero();
		// Pago
		pagos = Pago.getPagos();
		egreso = Pago.getEgreso();
		caja = Pago.getCaja();
		// Reserva
		numReserva = Reserva.getNumReserva();
	}
}
