package gestorAplicacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import uiMain.MenuController;
import uiMain.global;

public class Reserva implements Serializable {

	private static final long serialVersionUID = 1L;
	private static int numReserva;
	private Date fecha;
	private Cliente cliente;
	private Habitacion habitacion;
	private final static double costo = 12;
	private Pago pago;
	private int id;
	private boolean confirmacion = false;
	public static List<Reserva> lstReserva = new ArrayList<>();

	public Reserva(Cliente cliente, Habitacion habitacion) {
		numReserva++;
		id = numReserva;
		this.fecha = new Date();
		this.cliente = cliente;
		this.habitacion = habitacion;
		this.cliente.setPazYSalvo(false);
		Reserva.lstReserva.add(this);
	}

	public static void menuReserva() {
		global globalServices = new global();
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

	public static void crearReserva() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		Date fecha = new Date();
		Cliente cliente = null;
		boolean DateisCorrect = false;
		System.out.println("     Nueva Reserva");

//		Ingreso del cliente
		cliente = Cliente.ClienteExist();

		if (cliente != null) {
			System.out.println("¿Qué tipo de habitación desea reservar?");
			System.out.println("1- Suite    ($250.000/noche)");
			System.out.println("2- Familiar ($110.000/noche)");
			System.out.println("3- Sencilla ($55.000/noche)");
			int tipo = globalServices.validacionEntrada(3);

			System.out.println("Fechas");
			System.out.println("   Desde:");
			Date fecha1 = new Date();
			while (!DateisCorrect) {
				fecha1 = globalServices.StringToDate(sc.next());
				if (fecha1 != null) {
					DateisCorrect = true;
				} else {
					System.out.println("Ocurrio un problema ingresando la fecha, intentelo nuevamente");
				}
			}

			DateisCorrect = false;
			Date fecha2 = new Date();
			System.out.println("   Hasta:");
			while (!DateisCorrect) {
				fecha2 = globalServices.StringToDate(sc.next());
				if (fecha2 != null) {
					DateisCorrect = true;
				} else {
					System.out.println("Ocurrio un problema ingresando la fecha, intentelo nuevamente");
				}
			}

			List<Habitacion> lstHabitaciones = new ArrayList<>();
			lstHabitaciones = Habitacion.habitacionesDisponiblesPorTipo(tipo, fecha1, fecha2);
			for (int i = 1; i <= lstHabitaciones.size(); i++) {
				System.out.println(i + "- " + lstHabitaciones.get(i).getNumero() + "\n   -> "
						+ lstHabitaciones.get(i).getDescripcion());
			}
			int aux = globalServices.validacionEntrada(lstHabitaciones.size());

			Reserva newReserva = new Reserva(cliente, lstHabitaciones.get(aux));
			// --------- Falta setear fechas y crear metodo para ver si esta disponible
			Habitacion.ocuparHabitacion(null, null, null, newReserva.getId());
			// -------------------------------------------------------------------------------------------
		} else {
			System.out.println("No se pudo crear la reserva");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				new MenuController();
			}
			new MenuController();
		}
	}

	public static boolean Guardar() {
		ObjectOutputStream oos;
		boolean error = true;
		File ReservaFile = new File("src/Hotel/BaseDatos/Reserva");
		try {
			oos = new ObjectOutputStream(new FileOutputStream(ReservaFile));
			oos.writeObject(Reserva.lstReserva);
			oos.close();
			error = false;
		} catch (IOException e) {
			System.out.println("Error al intentar guardar Reservas\n    -> Error: " + e.getMessage());
			error = true;
		}
		return !error;
	}

	@SuppressWarnings("unchecked")
	public static boolean Cargar() {
		ObjectInputStream ois;
		boolean error = true;
		File ReservaFile = new File("src/Hotel/BaseDatos/Reserva");

		try {
			ois = new ObjectInputStream(new FileInputStream(ReservaFile));
			Reserva.lstReserva = (List<Reserva>) ois.readObject();
			error = false;
		} catch (IOException e) {
			System.out.println("No hay Reservas guardadas");
			error = false;
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Error al intentar leer Reservas\n    -> Error: " + ae.getMessage());
			error = true;
		} catch (ClassNotFoundException ce) {
			System.out.println("Error al intentar leer Reservas\n    -> Error: " + ce.getMessage());
			error = true;
		}
		return !error;
	}

	public static int getNumReserva() {
		return numReserva;
	}

	public static void setNumReserva(int numReserv) {
		numReserva = numReserv;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	public double getCosto() {
		return costo;
	}

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// METODOS ADICIONALES alias FUNCIONALIDADES

	public boolean verificarDispo() {
//		return habitacion.isDisponible();
		return false;
	}

	public String alquilarHabitacion() {
		if (this.verificarDispo() == true) {
			confirmacion = true;
//			habitacion.setDisponible(false);
			return "Reserva exitosa";
		} else {
			return "Habitacion no disponible";
		}
	}

	public Pago establecerPago() {
		if (confirmacion) {
			pago = new Pago(habitacion.valor(), true, this);
			return pago;
		} else {
			pago = new Pago(costo, true, this);
			return pago;
		}
	}

	public void cancelar() {
		confirmacion = false;
		this.establecerPago();
	}
}
