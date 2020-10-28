package uiMain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import gestorAplicacion.Hotel.Habitacion;
import gestorAplicacion.Hotel.Pago;
import gestorAplicacion.Hotel.Reserva;
import gestorAplicacion.Terceros.Cliente;
import gestorAplicacion.Terceros.Empleado;

public class global {

	public void clearScr() {
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (Exception e) {
		}
		System.out.println("\n\n           HOTEL EL POODEROSO");
		System.out.println("------------------ // ------------------\n\n\n");
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
				System.out.println("Error: No se ha ingresado un número entero");
				System.out.println("Ingrese nuevamente");
			}
		}
		return aux;
	}

	public int valiEntrada() {
		@SuppressWarnings("resource")
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
		Date hoy = new Date();
		Calendar fechaHoy = Calendar.getInstance();
		fechaHoy.setTime(hoy);
		Calendar fecha1 = Calendar.getInstance();
		Date fecha = null;
		try {
			fecha = formatter.parse(string);
			fecha1.setTime(fecha);
		} catch (ParseException e) {
		}
		if (hoy.compareTo(fecha) <= 0) {
			return fecha;
		} else {
			if (fechaHoy.get(Calendar.DATE) == fecha1.get(Calendar.DATE)
					&& (fechaHoy.get(Calendar.MONTH) + 1) == (fecha1.get(Calendar.MONTH) + 1)
					&& fechaHoy.get(Calendar.YEAR) == fecha1.get(Calendar.YEAR)) {
				return fecha;
			} else {
				System.out.println("No se puede reservar fechas pasadas");
				return null;
			}
		}

	}

	public static boolean guardarInfo() {
		ObjectOutputStream oos;
		boolean error = true;
		File InfoFile = new File("src/Hotel/BaseDatos/Info");
		Info Info = new Info();
		try {
			oos = new ObjectOutputStream(new FileOutputStream(InfoFile));
			oos.writeObject(Info);
			oos.close();
			error = false;
		} catch (IOException e) {
			System.out.println("Error al intentar guardar Informacion\n    -> Error: " + e.getMessage());
			error = true;
		}
		return !error;
	}

	public boolean GuardarSesion() {
		boolean success = false;
		if (Cliente.Guardar()) {
			if (Empleado.Guardar()) {
				if (Habitacion.Guardar()) {
					if (Pago.Guardar()) {
						if (Reserva.Guardar()) {
							if (guardarInfo()) {
								success = true;
							}
						}
					}
				}
			}
		}
		return success;
	}

	public static boolean CargarInfo() {
		ObjectInputStream ois;
		boolean error = true;
		File InfoFile = new File("src/Hotel/BaseDatos/Info");

		try {
			ois = new ObjectInputStream(new FileInputStream(InfoFile));
			Info i = (Info) ois.readObject();
			Cliente.setNumClientes(i.numClientes);
			Habitacion.setNumero(i.numero);
			Pago.setPagos(i.pagos);
			Pago.setEgreso(i.egreso);
			Pago.setCaja(i.caja);
			Reserva.setNumReserva(i.numReserva);
			error = false;
		} catch (IOException e) {
			System.out.println("No hay Información adicional guardada\n    -> Error: " + e.getMessage());
			error = true;
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Error al intentar leer Informacion\n    -> Error: " + ae.getMessage());
			error = true;
		} catch (ClassNotFoundException ce) {
			System.out.println("Error al intentar leer Informacion\n    -> Error: " + ce.getMessage());
			error = true;
		}
		return !error;
	}

	public boolean CargarSesion() {
		boolean success = false;
		if (Cliente.Cargar()) {
			if (Empleado.Cargar()) {
				if (Habitacion.Cargar()) {
					if (Pago.Cargar()) {
						if (Reserva.Cargar()) {
							if (CargarInfo()) {
								success = true;
							}
						}
					}
				}
			}
		}
		return success;
	}

	public interface dataBase {
		void Guardar();

		void Cargar();
	}

}
