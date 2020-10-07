package Entidades;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Persona {
	private List<Habitacion> lstHabitacion = new ArrayList<>();
	private List<Reserva> lstReserva = new ArrayList<>();
	private int numClientes;
	private boolean pazYSalvo;
	private List<Pago> lstPago = new ArrayList<>();
	private List<Empleado> lstEmpleado = new ArrayList<>();

	public Cliente(int cedula, String nombre, List<Habitacion> lstHabitacion, List<Reserva> lstReserva, int numClientes,
			boolean pazYSalvo, List<Pago> lstPago, List<Empleado> lstEmpleado) {
		super(cedula, nombre);
		this.lstHabitacion = lstHabitacion;
		this.lstReserva = lstReserva;
		this.numClientes = numClientes;
		this.pazYSalvo = pazYSalvo;
		this.lstPago = lstPago;
		this.lstEmpleado = lstEmpleado;
	}

	public List<Habitacion> getLstHabitacion() {
		return lstHabitacion;
	}

	public void setLstHabitacion(List<Habitacion> lstHabitacion) {
		this.lstHabitacion = lstHabitacion;
	}

	public List<Reserva> getLstReserva() {
		return lstReserva;
	}

	public void setLstReserva(List<Reserva> lstReserva) {
		this.lstReserva = lstReserva;
	}

	public int getNumClientes() {
		return numClientes;
	}

	public void setNumClientes(int numClientes) {
		this.numClientes = numClientes;
	}

	public boolean isPazYSalvo() {
		return pazYSalvo;
	}

	public void setPazYSalvo(boolean pazYSalvo) {
		this.pazYSalvo = pazYSalvo;
	}

	public List<Pago> getLstPago() {
		return lstPago;
	}

	public void setLstPago(List<Pago> lstPago) {
		this.lstPago = lstPago;
	}

	public List<Empleado> getLstEmpleado() {
		return lstEmpleado;
	}

	public void setLstEmpleado(List<Empleado> lstEmpleado) {
		this.lstEmpleado = lstEmpleado;
	}

}
