package uiMain;

import java.util.Scanner;

import gestorAplicacion.Reserva;

public class MenuController {
	@SuppressWarnings({ "resource" })
	public MenuController() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);

		globalServices.clearScr();
		imprimirOpciones();

		int aux = globalServices.validacionEntrada(7);

		switch (aux) {
		case 1:
			Reserva.menuReserva();
			break;
		case 2:

			break;
		case 3:

			break;
		case 4:

			break;
		case 5:

			break;
		case 6:

			break;
		case 7:
			if (globalServices.GuardarSesion()) {
				System.out.println("Sesión guardada y finalizada exitosamente");
				break;
			} else {
				System.out.println("Ocurrió un error al guardar, intentelo nuevamente");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
				new MenuController();
			}

		default:
			break;
		}
	}

	public void imprimirOpciones() {
		System.out.println("Menú principal   ");
		System.out.println("    digite el número de la opción que desee:");
		System.out.println("1- Reservas");
		System.out.println("2- Check-In");
		System.out.println("3- Habitaciones");
		System.out.println("4- Pagos");
		System.out.println("5- Clientes");
		System.out.println("6- Empleados");
		System.out.println("7- Guardar y Cerrar sesión");
	}
}
