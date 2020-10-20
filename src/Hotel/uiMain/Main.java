package uiMain;

import java.util.Scanner;

import gestorAplicacion.Cliente;
import gestorAplicacion.Empleado;
import gestorAplicacion.Habitacion;
import gestorAplicacion.Reserva;

public class Main {

	@SuppressWarnings({ "resource" })
	public static void main(String[] args) {

		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		System.out.println("       Bienvenido al Hotel El POOderoso");
		System.out.println();
		System.out.print("Porfavor ingrese su usuario: ");
		sc.next();
		System.out.print("Contraseña: ");
		sc.next();
//
////		// Datos pruebas
//		Empleado e1 = new Empleado(1, "Sebas", 500);
//		Empleado e2 = new Empleado(2, "Edwar", 100);
//		Empleado e3 = new Empleado(3, "Fulanito", 800);
//
//		Cliente c1 = new Cliente(4, "Pelanito", e1);
//		Cliente c2 = new Cliente(5, "Juanita", e2);
//		Cliente c3 = new Cliente(6, "Mrko", e3);
//
//		Habitacion h1 = new Habitacion(10, "Suite", "Habitacion en piso 15 con vista al mar");
//		Habitacion h2 = new Habitacion(11, "Familiar", "Habitacion en piso 3 con hermosa vista a las piscinas");
//		Habitacion h3 = new Habitacion(12, "Sencilla", "Habitacion en el primer piso cerca al bar");
//
//		Reserva r1 = new Reserva(c1, h1);
//		Reserva r2 = new Reserva(c2, h2);
//		Reserva r3 = new Reserva(c3, h3);

		boolean tru = true;
		int n = 6;
//		while (tru) {
//
//			Thread.sleep(500);
//			System.out.print(".");
//			n--;
//			if (n == 0) {
//				tru = false;
//				System.out.println(".");
//			}
//		}
		System.out.println("Conexión exitosa");

		boolean cargaron = globalServices.CargarSesion();

		if (!cargaron) {
			System.out.println("Ocurrió un error al leer BaseDatos, desea reintentarlo?");
			System.out.print("S/N ");
			boolean bien = false;
			while (!bien) {
				String res = sc.next();
				if (res.equals("s") || res.equals("S")) {
					cargaron = globalServices.CargarSesion();
					bien = cargaron;
				} else if (res.equals("n") || res.equals("N")) {
					bien = true;
					System.out.println("Finalizando sesión");
					try {
						Thread.sleep(1000);
						System.out.println("\n\n -> Sesión finalizada");
					} catch (InterruptedException e) {
					}
				} else {
					System.out.println("Entrada inválida");
					System.out.print("¿Desea crearlo? S/N ");
				}
			}
		}
		if (cargaron) {
//		Thread.sleep(1000);
			new MenuController();
		}

	}

}
