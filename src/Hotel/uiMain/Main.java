package uiMain;

import java.util.Scanner;

public class Main {

	@SuppressWarnings({ "resource", "unused" })
	public static void main(String[] args) {

		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println();
		System.out.print("Porfavor ingrese su usuario: ");
		sc.next();
		System.out.print("Contraseña: ");
		sc.next();
		boolean tru = true;
		int n = 6;
		while (tru) {

			try {
				Thread.sleep(350);
			} catch (InterruptedException e) {
			}
			System.out.print(".");
			n--;
			if (n == 0) {
				tru = false;
				System.out.println(".");
			}
		}
		System.out.println("Conexión exitosa");

		boolean cargaron = globalServices.CargarSesion();
//		if (cargaron) {
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
		}
		new MenuController();
//		}

	}

}
