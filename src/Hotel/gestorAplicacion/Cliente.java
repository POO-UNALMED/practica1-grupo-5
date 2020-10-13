package gestorAplicacion;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Persona {

	private static List<Reserva> lstReserva = new ArrayList<>();
	public static int numClientes;
	private boolean pazYSalvo = true;
	private static List<Pago> lstPago = new ArrayList<>();
	private Empleado empleado;

	public Cliente(int cedula, String nombre, Empleado empleado) {
		super(cedula, nombre);
		this.empleado = empleado;
		numClientes++;
	}

	public List<Reserva> getLstReserva() {
		return lstReserva;
	}

	public void setLstReserva(List<Reserva> lstReserva) {
		Cliente.lstReserva = lstReserva;
	}

	public boolean isPazYSalvo() {
		return pazYSalvo;
	}

	public void setPazYSalvo(boolean pazYSalvo) {
		this.pazYSalvo = pazYSalvo;
	}

	public static List<Pago> getLstPago() {
		return lstPago;
	}

	public static void setLstPago(List<Pago> lstPago) {
		Cliente.lstPago = lstPago;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public String pedirInfo(int opcion) {
		return empleado.darInfo(opcion);
	}

	public String pagarRecibo() {
		for (Reserva l : lstReserva) {
			l.getPago().generarRecibo();
		}
		return "";
	}

}
