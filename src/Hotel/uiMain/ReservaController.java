package uiMain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import gestorAplicacion.Cliente;
import gestorAplicacion.Reserva;

public class ReservaController {
	global globalServices = new global();
	Scanner sc = new Scanner(System.in);

	public ReservaController() {
		globalServices.clearScr();
		System.out.println("Reservas   ");
		System.out.println("    digite el número de la opción que desee:");
		System.out.println("1- Crear reserva");
		System.out.println("2- Buscar reserva");
		System.out.println("3- Editar reserva");
		System.out.println("4- Eliminar reserva");

		int aux = globalServices.validacionEntrada(4);

		switch (aux) {
		case 1:
			crearReserva();
			break;
		case 2:

			break;
		case 3:

			break;
		case 4:

			break;

		default:
			break;
		}
	}

	public void crearReserva() {
		globalServices.clearScr();
		Date fecha = new Date();
		Cliente cliente = null;
		boolean DateisCorrect = false;
		System.out.println("     Nueva Reserva");
//		Ingreso de fecha
		System.out.println("Ingrese la fecha: dd/mm/yyyy  ");
		while (!DateisCorrect) {
			try {
				fecha = StringToDate(sc.next());
				DateisCorrect = true;
			} catch (Exception e) {
				System.out.println("Formato inválido");
				System.out.println("Ingrese la fecha nuevamente: dd/mm/yyyy  ");
			}
		}

//		Ingreso del cliente
		cliente = ClienteController.ClienteExist();

		if (cliente != null) {
			new Reserva(fecha, cliente, null);
		} else {
			System.out.println("No se puede crear una reserva sin cliente");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			new MenuController();
		}
	}

	private Date StringToDate(String string) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.parse(string);

	}

}
