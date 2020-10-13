package uiMain;

import java.util.Scanner;

public class Menu {
	@SuppressWarnings({ "unused", "resource" })
	public Menu() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();

		imprimirOpciones();

		boolean correct = false;
		while (!correct) {
			try {
				String option = sc.next();
				int aux = Integer.valueOf(option) + 0;
				if (aux < 9 && aux > 0) {
					correct = true;
				} else {
					System.out.println("El n�mero ingresado es inv�lido");
				}

			} catch (Exception e) {
				System.out.println("Error: No se ha ingresado un n�mero entero");
			}
		}
	}

	public void imprimirOpciones() {
		System.out.println("Men� principal   ");
		System.out.println("    digite el n�mero de la opci�n que desee:");
		System.out.println("1- ");
		System.out.println("2- ");
		System.out.println("3- ");
		System.out.println("4- ");
		System.out.println("5- ");
		System.out.println("6- ");
		System.out.println("7- ");
		System.out.println("8- ");
	}
}
