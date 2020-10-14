package uiMain;

import java.util.Scanner;

import gestorAplicacion.Cliente;
import gestorAplicacion.Empleado;

public class ClienteController {

	private static Cliente newCliente(int cedula) {
		Scanner sc = new Scanner(System.in);
		Scanner scf = new Scanner(System.in);
		scf.useDelimiter("\n");
		System.out.println("Ingrese el nombre del cliente: ");
		String nombre = scf.next();
		System.out.println(nombre);
		Empleado employee = EmpleadoController.EmpleadoExist();

		return new Cliente(cedula, nombre, employee);
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
						cliente = ClienteController.newCliente(ced);
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

}
