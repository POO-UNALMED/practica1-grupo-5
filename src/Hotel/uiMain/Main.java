package uiMain;

import java.util.Scanner;

public class Main {

	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] args) throws InterruptedException {

		Scanner sc = new Scanner(System.in);
		System.out.println("       Bienvenido al Hotel El POOderoso");
		System.out.println();
		System.out.print("Porfavor ingrese su usuario: ");
		sc.next();
		System.out.print("Contraseña: ");
		sc.next();
		boolean tru = true;
		int n = 6;
		while (tru) {

			Thread.sleep(500);
			System.out.print(".");
			n--;
			if (n == 0) {
				tru = false;
				System.out.println(".");
			}
		}
		System.out.println("Conexión exitosa");
		Thread.sleep(1000);
		Menu m1 = new Menu();

	}

}
