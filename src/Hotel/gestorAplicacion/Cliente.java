package gestorAplicacion;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Persona {
	
	private static List<Reserva> lstReserva = new ArrayList<>();
	public static int numClientes;
	private boolean pazYSalvo;
	private static List<Pago> lstPago = new ArrayList<>();
	private static List<Empleado> lstEmpleado = new ArrayList<>();

	public Cliente(int cedula, String nombre,
			boolean pazYSalvo) {
		super(cedula, nombre);
		this.pazYSalvo = pazYSalvo;
		numClientes++;
	}

	

	public List<Reserva> getLstReserva() {
		return lstReserva;
	}

	public static void setLstReserva(List<Reserva> lstReserva) {
		Cliente.lstReserva = lstReserva;
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

	public static void setLstPago(List<Pago> lstPago) {
		Cliente.lstPago = lstPago;
	}

	public List<Empleado> getLstEmpleado() {
		return lstEmpleado;
	}

	public static void setLstEmpleado(List<Empleado> lstEmpleado) {
		Cliente.lstEmpleado = lstEmpleado;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
