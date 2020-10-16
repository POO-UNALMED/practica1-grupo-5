package gestorAplicacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

	public static Cliente ClienteExist() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese la cédula: (Ex: 1001366265)");
		boolean ClienteisCorrect = false;
		Cliente cliente = null;
		while (!ClienteisCorrect) {
			int ced = sc.nextInt();
			if (Cliente.ClienteExist(ced)) {
				cliente = Cliente.ClientePorCedula(ced);
			} else {
				System.out.println("El cliente no está registrado, ¿Desea crearlo?");
				System.out.print("S/N ");
				boolean bien = false;
				while (!bien) {
					String res = sc.next();
					if (res.equals("s") || res.equals("S")) {
						cliente = newCliente(ced);
						bien = true;
						ClienteisCorrect = true;
					} else if (res.equals("n") || res.equals("N")) {
						System.out.println("Creación de cliente cancelada");
						bien = true;
						ClienteisCorrect = true;
					} else {
						System.out.println("Entrada inválida");
						System.out.print("¿Desea crearlo? S/N ");
					}
				}
			}

		}
		return cliente;
	}

	private static Cliente newCliente(int cedula) {
		Scanner sc = new Scanner(System.in);
		Scanner scf = new Scanner(System.in);
		scf.useDelimiter("\n");
		System.out.println("Ingrese el nombre del cliente: ");
		String nombre = scf.next();
		System.out.println(nombre);
		Empleado employee = Empleado.EmpleadoExist();
		if (employee == null) {
			return null;
		} else {
			return new Cliente(cedula, nombre, employee);
		}
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
