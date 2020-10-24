package uiMain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import gestorAplicacion.Cliente;
import gestorAplicacion.Empleado;
import gestorAplicacion.Habitacion;
import gestorAplicacion.Pago;
import gestorAplicacion.Reserva;

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

	@SuppressWarnings("resource")
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
					System.out.println("Ingrese nuevamente");
				}

			} catch (Exception e) {
				System.out.println("Error: No se ha ingresado un nÃºmero entero");
				System.out.println("Ingrese nuevamente");
			}
		}
		return aux;
	}
	
	public int valiEntrada() {
		Scanner sc = new Scanner(System.in);
		boolean correct = false;
		int aux = 0;
		String option = "";
		while (!correct) {
			try {
				option = sc.next();
				aux = Integer.valueOf(option) + 0;
				correct = true;

			} catch (Exception e) {
				System.out.println("Error: No se ha ingresado un numero entero");
				System.out.println("Ingrese nuevamente");
			}
		}
		return aux;
	}

	public Date StringToDate(String string) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return formatter.parse(string);
		} catch (ParseException e) {
			return null;
		}

	}

	public boolean GuardarSesion() {
		boolean success = false;
		if (Cliente.Guardar()) {
			if (Empleado.Guardar()) {
				if (Habitacion.Guardar()) {
					if (Pago.Guardar()) {
						if (Reserva.Guardar()) {
							success = true;
						}
					}
				}
			}
		}
		return success;
	}

	public boolean CargarSesion() {
		boolean success = false;
		if (Cliente.Cargar()) {
			if (Empleado.Cargar()) {
				if (Habitacion.Cargar()) {
					if (Pago.Cargar()) {
						if (Reserva.Cargar()) {
							success = true;
						}
					}
				}
			}
		}
		return success;
	}

}
