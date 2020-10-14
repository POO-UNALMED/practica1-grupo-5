package uiMain;

import java.util.Scanner;

import gestorAplicacion.Empleado;

public class EmpleadoController {

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
						employee = EmpleadoController.newEmpleado(ced);
						bien = true;
					} else if (res.equals("n") || res.equals("N")) {
						System.out.println("Creación de empleado cancelada");
						bien = true;
					} else {
						System.out.println("Entrada inválida");
						System.out.print("¿Desea crearlo? S/N ");
					}
				}
			}

		}
		return employee;
	}

}
