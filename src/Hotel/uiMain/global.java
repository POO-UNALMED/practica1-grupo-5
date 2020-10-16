package uiMain;

import java.util.Scanner;

public class global {

	public void clearScr() {
		for (int i = 0; i < 8; i++) {
			System.out.println();
			if (i == 2) {
				System.out.println("           HOTEL EL POODEROSO");
				System.out.println("------------------ // ------------------");
			}
		}
	}

	public int validacionEntrada(int fin) {
		Scanner sc = new Scanner(System.in);
		boolean correct = false;
		int aux = 0;
		String option = "";
		while (!correct) {
			try {
				option = sc.next();
				aux = Integer.valueOf(option) + 0;
				if (aux < fin + 1 && aux > 0) {
					correct = true;
				} else {
					System.out.println("El número ingresado es inválido");
				}

			} catch (Exception e) {
				System.out.println("Error: No se ha ingresado un número entero");
			}
		}
		return aux;
	}

}
