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
		System.out.print("Contrase�a: ");
		sc.next();
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
		System.out.println("Conexi�n exitosa");

		boolean cargaron = globalServices.CargarSesion();
//		if (!cargaron) {
//			System.out.println("Ocurri� un error al leer BaseDatos, desea reintentarlo?");
//			System.out.print("S/N ");
//			boolean bien = false;
//			while (!bien) {
//				String res = sc.next();
//				if (res.equals("s") || res.equals("S")) {
//					cargaron = globalServices.CargarSesion();
//					bien = cargaron;
//				} else if (res.equals("n") || res.equals("N")) {
//					bien = true;
//					System.out.println("Finalizando sesi�n");
//					try {
//						Thread.sleep(1000);
//						System.out.println("\n\n -> Sesi�n finalizada");
//					} catch (InterruptedException e) {
//					}
//				} else {
//					System.out.println("Entrada inv�lida");
//					System.out.print("�Desea crearlo? S/N ");
//				}
//			}
//		}
//		if (cargaron) {
//		Thread.sleep(1000);
		new MenuController();
//		}

	}

}
