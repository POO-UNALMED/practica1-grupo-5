package gestorAplicacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Empleado extends Persona {
	private float salario;
	private boolean activo;
	private static List<Cliente> lstCliente = new ArrayList<>();
	private static List<Empleado> lstEmpleado = new ArrayList<>();

	public Empleado(int cedula, String nombre, String cargo, float salario) {
		super(cedula, nombre);
		this.salario = salario;
		this.activo = true;
		lstEmpleado.add(this);
	}

	public static Empleado newEmpleado(int cedula) {
		Scanner sc = new Scanner(System.in);
//		System.out.println("Ingrese el nombre del cliente: ");
//		String nombre = sc.next();
//		System.out.println("Ingrese la cedula del empleado a cargo: ");
//		Empleado employee = Empleado.EmpleadoPorCedula(sc.nextInt());

		return null;
	}

	public static Empleado EmpleadoExist() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese la cédula del empleado: (Ex: 1001366265)");
		boolean EmpleadoisCorrect = false;
		Empleado employee = null;
		while (!EmpleadoisCorrect) {
			int ced = sc.nextInt();
			if (Empleado.EmpleadoExist(ced)) {
				employee = Empleado.EmpleadoPorCedula(ced);
			} else {
				System.out.println("El empleado no está registrado, ¿Desea crearlo?");
				System.out.print("S/N ");
				boolean bien = false;
				while (!bien) {
					String res = sc.next();
					if (res.equals("s") || res.equals("S")) {
						employee = newEmpleado(ced);
						bien = true;
						EmpleadoisCorrect = true;
					} else if (res.equals("n") || res.equals("N")) {
						System.out.println("Creación de empleado cancelada");
						bien = true;
						EmpleadoisCorrect = true;
					} else {
						System.out.println("Entrada inválida");
						System.out.print("¿Desea crearlo? S/N ");
					}
				}
			}

		}
		return employee;
	}

	public static boolean EmpleadoExist(int cedula) {
		boolean exist = false;
		for (Empleado employee : lstEmpleado) {
			if (employee.getCedula() == cedula) {
				exist = true;
				break;
			}
		}
		return exist;
	}

	public static Empleado EmpleadoPorCedula(int cedula) {
		Empleado Empleado = null;
		for (Empleado empleado : lstEmpleado) {
			if (empleado.getCedula() == cedula) {
				Empleado = empleado;
				break;
			}
		}
		return Empleado;
	}

	public float getSalario() {
		return salario;
	}

	public void setSalario(float salario) {
		this.salario = salario;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public List<Cliente> getLstCliente() {
		return lstCliente;
	}

	public static void setLstCliente(List<Cliente> lstCliente) {
		Empleado.lstCliente = lstCliente;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public Cliente registrarCliente(int cedula, String nombre) {
		Cliente c = new Cliente(cedula, nombre, this);
		lstCliente.add(c);
		System.out.println("Registro Exitoso");
		return c;
	}

	public String darInfo(int opcion) {
		if (opcion == 1) {
			return "Las habitaciones Sencillas cuenta con capacidad para dos personas(1 cama, 1 baï¿½o)";
		} else if (opcion == 2) {
			return "Las habitaciones Familiares cuenta con capacidad para 6 personas(3 camas, 2 baï¿½o)";
		} else {
			return "Las habitaciones Suits cuenta con capacidad para 6 personas(3 camas, 2 baï¿½o, 1 salon, 1 jacuzzy)";
		}
	}

	public String asignarReserva(Cliente c, Date fecha, Habitacion h) {
		if (h.isDisponible()) {
			Reserva r = new Reserva(fecha, c, h);
			c.setLstReserva(r);
			return "Asignacion exitosa";
		} else {
			return "La habitacion esta ocupado, debe elegir otra habitacion";
		}
	}

	public String comfirmarReserva(Reserva r) {
		if (r.alquilarHabitacion() == "Reserva exitosa") {
			return "Confirmacion exitosa";
		} else {
			return "Otro usuario comfirmo la habitacion";
		}
	}

}
