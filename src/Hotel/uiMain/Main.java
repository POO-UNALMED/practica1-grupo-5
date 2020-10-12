package uiMain;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

	public static void main(String[] args) {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			int tic = 0;

			@Override
			public void run() {
				if (tic % 2 == 0)
					System.out.println("TIC");
				else
					System.out.println("TOC");
				tic++;
			}
		};
		Scanner sc = new Scanner(System.in);
		System.out.println("       Bienvenido al Hotel El POOderoso");
		System.out.println();
		System.out.print("Porfavor ingrese su usuario: ");
		sc.next();
		System.out.print("Contraseña: ");
		sc.next();
		timer.schedule(task, 10, 1000);
		System.out.println("Conexión exitosa");
		Menu m1 = new Menu();

	}

}