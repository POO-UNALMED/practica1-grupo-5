package uiMain;

import java.util.Scanner;

public class Menu {
	@SuppressWarnings({ "resource", "unused" })
	public Menu() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		int aux = 0;

		globalServices.clearScr();
		imprimirOpciones();

		boolean correct = false;
		while (!correct) {
			try {
				String option = sc.next();
				aux = Integer.valueOf(option) + 0;
				if (aux < 8 && aux > 0) {
					correct = true;
				} else {
					System.out.println("El n�mero ingresado es inv�lido");
				}

			} catch (Exception e) {
				System.out.println("Error: No se ha ingresado un n�mero entero");
			}
		}
		switch (aux) {
		case 1:

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
			Main m = new Main();
			break;

		default:
			break;
		}
	}

	public void imprimirOpciones() {
		System.out.println("Men� principal   ");
		System.out.println("    digite el n�mero de la opci�n que desee:");
		System.out.println("1- Reservas");
		System.out.println("2- Check-In");
		System.out.println("3- Habitaciones");
		System.out.println("4- Pagos");
		System.out.println("5- Clientes");
		System.out.println("6- Empleados");
		System.out.println("7- Cerrar sesi�n");
	}
}
