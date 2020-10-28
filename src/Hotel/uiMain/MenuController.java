package uiMain;

import java.util.Scanner;

import gestorAplicacion.Hotel.Habitacion;
import gestorAplicacion.Hotel.Pago;
import gestorAplicacion.Hotel.Reserva;
import gestorAplicacion.Terceros.Cliente;
import gestorAplicacion.Terceros.Empleado;

public class MenuController {
	@SuppressWarnings({ "resource", "unused" })
	public MenuController() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);

		globalServices.clearScr();
		imprimirOpciones();

		int aux = globalServices.validacionEntrada(8);

		switch (aux) {
		case 1:
			Reserva.menuReserva();
			break;
		case 2:
			Reserva.checkIn();
			break;
		case 3:
			Habitacion.menuHabitacion();
			break;
		case 4:
			Pago.menuPago();
			break;
		case 5:
			Cliente.menuCliente();
			break;
		case 6:
			Empleado.menuEmpleado();
			break;
		case 7:
			Empleado.informacionHotel();
			break;
		case 8:
			if (globalServices.GuardarSesion()) {
				System.out.println("Sesión guardada y finalizada exitosamente");
				System.exit(0);
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
		System.out.println("7- Informacion del hotel");
		System.out.println("8- Guardar y Cerrar sesión");
	}
}
