package gestorAplicacion;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Persona {

	private List<Reserva> lstReserva = new ArrayList<>();
	public static int numClientes;
	private boolean pazYSalvo = true;
	private Empleado empleado;
	private static List<Cliente> lstCliente = new ArrayList<>();

	public Cliente(int cedula, String nombre, Empleado empleado) {
		super(cedula, nombre);
		this.empleado = empleado;
		numClientes++;
		lstCliente.add(this);
	}

	public List<Reserva> getLstReserva() {
		return lstReserva;
	}

	public static boolean ClienteExist(int cedula) {
		boolean exist = false;
		for (Cliente cliente : lstCliente) {
			if (cliente.getCedula() == cedula) {
				exist = true;
				break;
			}
		}
		return exist;
	}

	public static Cliente ClientePorCedula(int cedula) {
		Cliente Cliente = null;
		for (Cliente cliente : lstCliente) {
			if (cliente.getCedula() == cedula) {
				Cliente = cliente;
				break;
			}
		}
		return Cliente;
	}

	public void setLstReserva(Reserva r) {
		lstReserva.add(r);
	}

	public boolean isPazYSalvo() {
		return pazYSalvo;
	}

	public void setPazYSalvo(boolean pazYSalvo) {
		this.pazYSalvo = pazYSalvo;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	@Override
	public String toString() {
		return null;
	}

	public String pedirInfo(int opcion) {
		return empleado.darInfo(opcion);
	}

	public void pagarRecibo() {
		for (Reserva l : lstReserva) {
			Pago p = l.establecerPago();
			p.generarRecibo();
		}
	}

}
