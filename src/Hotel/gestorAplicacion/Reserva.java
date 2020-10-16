package gestorAplicacion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import uiMain.MenuController;
import uiMain.global;

public class Reserva {
	private static int numReserva;
	private Date fecha;
	private Cliente cliente;
	private Habitacion habitacion;
	private final static double costo = 12;
	private Pago pago;
	private int id;
	private boolean confirmacion = false;

	public Reserva(Date fecha, Cliente cliente, Habitacion habitacion) {
		numReserva++;
		id = numReserva;
		this.fecha = fecha;
		this.cliente = cliente;
		this.habitacion = habitacion;
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
//		Ingreso de fecha
		System.out.println("Ingrese la fecha: dd/mm/yyyy  ");
		while (!DateisCorrect) {
			try {
				fecha = StringToDate(sc.next());
				DateisCorrect = true;
			} catch (Exception e) {
				System.out.println("Formato inválido");
				System.out.println("Ingrese la fecha nuevamente: dd/mm/yyyy  ");
			}
		}

//		Ingreso del cliente
		cliente = Cliente.ClienteExist();

		if (cliente != null) {
			new Reserva(fecha, cliente, null);
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

	private static Date StringToDate(String string) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.parse(string);

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
		return habitacion.isDisponible();
	}

	public String alquilarHabitacion() {
		if (this.verificarDispo() == true) {
			confirmacion = true;
			habitacion.setDisponible(false);
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
