/*Esta clase es la que controla los datos relacionados a las habitaciones que tiene hotel,
 controlando el valor de cada habitacion segun su tipo y su descripcion si la tiene*/

package gestorAplicacion.Hotel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;

import javafx.util.Pair;
import uiMain.MenuController;
import uiMain.global;

public class Habitacion implements Serializable {

	private static final long serialVersionUID = 1L;
	private static int numero;
	private Map<Pair<String, String>, Integer> busyDates = new HashMap<>();
	private String tipo;
	private String descripcion;
	private int precioDia;
	private int numeroHabitacion;
	private static List<Habitacion> lstHabitacion = new ArrayList<>();

	// Constructor

	public Habitacion(String tipo, String descripcion) {
		Habitacion.numero++;
		this.numeroHabitacion = Habitacion.numero;
		this.tipo = tipo;
		if (tipo == "Sencilla") {
			precioDia = 55000;
		} else if (tipo == "Familiar") {
			precioDia = 110000;
		} else if (tipo == "Suite") {
			precioDia = 250000;
		}
		this.descripcion = descripcion;
		Habitacion.lstHabitacion.add(this);
	}

	public Habitacion(String tipo) {
		this(tipo, "");
	}

	// Creacion del menu de habitacion

	public static void menuHabitacion() {
		global globalServices = new global();
		globalServices.clearScr();
		System.out.println("Habitaciones   ");
		System.out.println("    digite el numero de la opcion que desee:");
		System.out.println("1- Crear habitacion");
		System.out.println("2- Buscar habitacion");
		System.out.println("3- Editar habitacion");
		System.out.println("4- Eliminar habitacion");
		System.out.println("5- Mostrar listado de habitaciones");
		System.out.println("6- Verificar disponibilidad");
		System.out.println("7- Regresar");

		int aux = globalServices.validacionEntrada(7);

		switch (aux) {
		case 1:
			crearHabitacion();
			break;
		case 2:
			buscarHabitacion();
			break;
		case 3:
			editarHabitacion();
			break;
		case 4:
			eliminarHabitacion();
			break;
		case 5:
			mostrarHabitacionesExistente();
			break;
		case 6:
			habitacionesDisponible();
			break;
		case 7:
			new MenuController();
			break;
		default:
			break;
		}
	}

	// Mwtodo que crea una habitacion en la base de datos

	@SuppressWarnings("resource")
	public static void crearHabitacion() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		Scanner scn = new Scanner(System.in);
		scn.useDelimiter("\n");
		globalServices.clearScr();
		System.out.println("     CREACION HABITACION");
		System.out.println("Que tipo de Habitacion desea crear?");
		System.out.println("1- Sencilla");
		System.out.println("2- Familiar");
		System.out.println("3- Suite");
		int tipo = globalServices.validacionEntrada(3);
		System.out.println("Desea crear la habitacion con una descripcion?");
		System.out.println("S/N");
		boolean bien = false;
		while (!bien) {
			String res = sc.next();
			if (res.equals("s") || res.equals("S")) {
				System.out.println("Que descripcion posee la habitacion?");
				String a = scn.next();
				switch (tipo) {
				case 1:
					new Habitacion("Sencilla", a);
					break;
				case 2:
					new Habitacion("Familiar", a);
					break;
				case 3:
					new Habitacion("Suite", a);
					break;
				default:
					break;
				}
				System.out.println("Habitacion creada exitosamente");
				bien = true;
			} else if (res.equals("n") || res.equals("N")) {
				switch (tipo) {
				case 1:
					new Habitacion("Sencilla");
					break;
				case 2:
					new Habitacion("Familiar");
					break;
				case 3:
					new Habitacion("Suite");
					break;
				default:
					break;
				}
				System.out.println("Habitacion creada exitosamente");
				bien = true;
			} else {
				System.out.println("Entrada invalida");
				System.out.print("Desea crearlo? S/N ");
			}
		}
		globalServices.GuardarSesion();
		try {
			Thread.sleep(3000);
			Habitacion.menuHabitacion();
			;
		} catch (InterruptedException e) {
			Habitacion.menuHabitacion();
		}

	}

	// Metodo que permite buscar la id de una habitacion en la base de datos

	@SuppressWarnings("resource")
	public static void buscarHabitacion() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("     BUSQUEDAD DE HABITACION");
		boolean confirma = false;
		if (Habitacion.lstHabitacion.size() > 0) {
			while (!confirma) {
				System.out.println("     Ingrese el numero de la Habitacion:");
				int aux = globalServices.valiEntrada();
				boolean aux1 = false;
				for (Habitacion h : Habitacion.lstHabitacion) {
					if (h.getNumeroHabitacion() == aux) {
						System.out.println("Esta es su habitacion: ");
						System.out.println();
						System.out.println("Numero de habitacion: " + h.getNumeroHabitacion());
						System.out.println("Tipo: " + h.getTipo());
						System.out.println("Descripcion: " + h.getDescripcion());
						aux1 = true;
						break;
					}
				}
				if (!aux1) {
					System.out.println("Este numero de habitacion no se encuentra registrado");
					System.out.println("Desea volver a intentar?");
					System.out.println("S/N");
					boolean bien = false;
					while (!bien) {
						String res = sc.next();
						if (res.equals("s") || res.equals("S")) {
							bien = true;
						} else if (res.equals("n") || res.equals("N")) {
							System.out.println("Busqueda cancelada");
							bien = true;
							confirma = true;
						} else {
							System.out.println("Entrada invalida");
							System.out.print("Desea volver a intentar? S/N ");
						}
					}
				} else {
					confirma = true;
				}
			}
			try {
				Thread.sleep(3000);
				menuHabitacion();
			} catch (InterruptedException e) {
				menuHabitacion();
			}
		} else {
			System.out.println("No hay habitaciones registradas");
			try {
				Thread.sleep(3000);
				menuHabitacion();
			} catch (InterruptedException e) {
				menuHabitacion();
			}
		}
	}

	// Metodo que permite modificar tipo o descripcion de una habitacion

	@SuppressWarnings("resource")
	public static void editarHabitacion() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		Scanner scn = new Scanner(System.in);
		scn.useDelimiter("\n");
		globalServices.clearScr();
		System.out.println("     EDICION HABITACION");
		boolean confirma = false;
		if (Habitacion.lstHabitacion.size() > 0) {
			while (!confirma) {
				System.out.println("     Ingrese el numero de la habitacion a editar:");
				int aux = globalServices.valiEntrada();
				boolean aux1 = false;
				for (Habitacion h : Habitacion.lstHabitacion) {
					if (h.getNumeroHabitacion() == aux) {
						System.out.println("Esta es su Habitacion:");
						System.out.println("Numero de habitacion: " + h.getNumeroHabitacion());
						System.out.println("Tipo: " + h.getTipo());
						System.out.println("Descripcion: " + h.getDescripcion());
						System.out.println();
						System.out.println("Que edicion desea realizar?");
						System.out.println("1- Editar tipo de habitacion");
						System.out.println("2- Editar descripcion de la habitacion");
						int aux2 = globalServices.validacionEntrada(2);
						switch (aux2) {
						case 1:
							System.out.println("Ingrese el nuevo tipo de la habitacion:");
							System.out.println("1- Sencilla");
							System.out.println("2- Familiar");
							System.out.println("3- Suite");
							int a = globalServices.validacionEntrada(3);
							switch (a) {
							case 1:
								h.setTipo("Sencilla");
								System.out.println("Cambio exitoso");
								break;
							case 2:
								h.setTipo("Familiar");
								System.out.println("Cambio exitoso");
								break;
							case 3:
								h.setTipo("Suite");
								System.out.println("Cambio exitoso");
								break;
							default:
								break;
							}
							break;
						case 2:
							System.out.println("Ingrese la nueva descripcion de la habitacion:");
							String d = scn.next();
							h.setDescripcion(d);
							System.out.println("Descripcion editada exitosamente");
							break;
						default:
							break;
						}
						Reserva.ActualizarHabitacion(h);
						aux1 = true;
						break;
					}
				}
				if (!aux1) {
					System.out.println("No se encuentra ninguna habitacion registrada con este numero");
					System.out.println("Desea volver a intentar?");
					System.out.println("S/N");
					boolean bien = false;
					while (!bien) {
						String res = sc.next();
						if (res.equals("s") || res.equals("S")) {
							bien = true;
						} else if (res.equals("n") || res.equals("N")) {
							System.out.println("Edicion cancelada");
							bien = true;
							confirma = true;
						} else {
							System.out.println("Entrada invalida");
							System.out.print("Desea volver a intentar? S/N ");
						}
					}
				} else {
					confirma = true;
				}
			}
			try {
				Thread.sleep(3000);
				globalServices.GuardarSesion();
				menuHabitacion();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				menuHabitacion();
			}
		} else {
			System.out.println("No hay habitaciones registradas");
			try {
				Thread.sleep(3000);
				globalServices.GuardarSesion();
				menuHabitacion();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				menuHabitacion();
			}
		}
	}

	// Metodo que elimina la habitacion de la base de datos

	@SuppressWarnings("resource")
	public static void eliminarHabitacion() {
		System.out.println("     ELIMINAR HABITACION");
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		boolean confirma = false;
		if (Habitacion.lstHabitacion.size() > 0) {
			while (!confirma) {
				System.out.println("     Ingrese el numero de la habitacion:");
				int aux = globalServices.valiEntrada();
				boolean aux1 = false;
				for (Habitacion h : Habitacion.lstHabitacion) {
					if (h.getNumeroHabitacion() == aux) {
						if (h.getBusyDates().size() <= 0) {
							System.out.println("Numero de habitacion: " + h.getNumeroHabitacion());
							System.out.println("Tipo: " + h.getTipo());
							System.out.println("Descripcion: " + h.getDescripcion());
							System.out.println();
							System.out.println("Esta Seguro que desea eliminar la Habitacion?");
							System.out.print("S/N ");
							boolean bien = false;
							while (!bien) {
								String res = sc.next();
								if (res.equals("s") || res.equals("S")) {
									bien = true;
									eliminarHabitacion(h);
									System.out.println("Eliminacion exitosa");
								} else if (res.equals("n") || res.equals("N")) {
									System.out.println("Eliminacion cancelada");
									bien = true;
									confirma = true;
								} else {
									System.out.println("Entrada invalida");
									System.out.print("Desea volver a intentar? S/N ");
								}
							}
							aux1 = true;
							break;
						} else {
							System.out.println("La habitacion no se puede eliminar, tiene reservas pendientes");
							aux1 = true;
						}
					}
				}
				if (!aux1) {
					System.out.println("Este numero de habitacion no se encuentra registrado");
					System.out.println("Desea volver a intentar?");
					System.out.println("S/N");
					boolean bien = false;
					while (!bien) {
						String res = sc.next();
						if (res.equals("s") || res.equals("S")) {
							bien = true;
						} else if (res.equals("n") || res.equals("N")) {
							System.out.println("Eliminacion cancelada");
							bien = true;
							confirma = true;
						} else {
							System.out.println("Entrada invalida");
							System.out.print("Desea volver a intentar? S/N ");
						}
					}
				} else {
					confirma = true;
				}
			}
			try {
				Thread.sleep(3000);
				globalServices.GuardarSesion();
				menuHabitacion();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				menuHabitacion();
			}
		} else {
			System.out.println("No hay habitaciones registradas");
			try {
				Thread.sleep(1200);
				globalServices.GuardarSesion();
				menuHabitacion();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				menuHabitacion();
			}
		}

	}

	// Metodo para mostrar las habitaciones existentes en el hotel

	private static void eliminarHabitacion(Habitacion h) {
		for (int i = 0; i < lstHabitacion.size(); i++) {
			if (lstHabitacion.get(i).getNumeroHabitacion() == h.getNumeroHabitacion()) {
				lstHabitacion.remove(i);
				Reserva.EliminarHabitacion(h);
			}
		}
		Habitacion.Guardar();
	}

	public static void eliminarReserva(Reserva r) {

		Calendar fechaIniAux = Calendar.getInstance();
		fechaIniAux.setTime(r.getFechaInicio());
		Calendar fechaFinAux = Calendar.getInstance();
		fechaFinAux.setTime(r.getFechaFin());
		String string1 = fechaIniAux.get(Calendar.DATE) + "/" + (fechaIniAux.get(Calendar.MONTH) + 1) + "/"
				+ fechaIniAux.get(Calendar.YEAR);
		String string2 = fechaFinAux.get(Calendar.DATE) + "/" + (fechaFinAux.get(Calendar.MONTH) + 1) + "/"
				+ fechaFinAux.get(Calendar.YEAR);

		for (Map.Entry<Pair<String, String>, Integer> x : r.getHabitacion().getBusyDates().entrySet()) {
			String fechaInicio1 = x.getKey().toString().split("=")[0];
			String fechaFin1 = x.getKey().toString().split("=")[1];

			if (fechaInicio1.equals(string1) && fechaFin1.equals(string2) && x.getValue() == r.getId()) {
				r.getHabitacion().getBusyDates().remove(x.getKey());
			}
		}
		Reserva.ActualizarHabitacion(r.getHabitacion());
		Habitacion.Guardar();
	}

	@SuppressWarnings("resource")
	public static void mostrarHabitacionesExistente() {
		DecimalFormat moneda = new DecimalFormat("###,###");
		global globalService = new global();
		Scanner sc = new Scanner(System.in);
		globalService.clearScr();
		System.out.println("    HABITACIONES EXISTENTES ACTUALMENTE");
		if (Habitacion.lstHabitacion.size() > 0) {
			int n = 1;
			for (Habitacion h : Habitacion.lstHabitacion) {
				System.out.println(n + "- Numero de habitacion: " + h.getNumeroHabitacion() + " Descripcion: "
						+ h.getDescripcion() + "\n   Tipo: " + h.getTipo() + "  Precio por dia : $ "
						+ moneda.format(h.getPrecioDia()));
				n++;
			}
			System.out.println();
			System.out.println("Total de habitaciones: " + Habitacion.lstHabitacion.size());
			System.out.println("Presione '1' para regresar");
			sc.next();
			Habitacion.menuHabitacion();

		} else {
			System.out.println("No hay habitaciones existentes por el momento.");
			try {
				Thread.sleep(3000);
				Habitacion.menuHabitacion();
			} catch (InterruptedException e) {
				Habitacion.menuHabitacion();
			}
		}
	}

	@SuppressWarnings("resource")
	public static void habitacionesDisponible() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("     VERIFICACION DE HABITACIONES\n");

		Date fecha1 = new Date();
		Date fecha2 = new Date();
		boolean DateisCorrect = false;
		boolean valid = false;
		while (!valid) {
			System.out.println("Fechas");
			System.out.println("   Desde:");
			while (!DateisCorrect) {
				fecha1 = globalServices.StringToDate(sc.next());
				if (fecha1 != null) {
					DateisCorrect = true;
				} else {
					System.out.println("Ocurrio un problema ingresando la fecha, intentelo nuevamente");
				}
			}

			DateisCorrect = false;
			System.out.println("   Hasta:");
			while (!DateisCorrect) {
				fecha2 = globalServices.StringToDate(sc.next());
				if (fecha2 != null) {
					DateisCorrect = true;
				} else {
					System.out.println("Ocurrio un problema ingresando la fecha, intentelo nuevamente");
				}
			}
			if (fecha1.compareTo(fecha2) <= 0) {
				valid = true;
			} else {
				DateisCorrect = false;
				System.out.println("Tiempo de reserva invalido, por favor intente de nuevo");
				System.out.println("------------------------------------------------------");
				System.out.println("");
				globalServices.clearScr();
				try {
					Thread.sleep(2600);
				} catch (InterruptedException e) {
				}
				menuHabitacion();
			}
		}
		System.out.println("Que tipo de habitacion desea?");
		System.out.println("1- Sencilla");
		System.out.println("2- Familiar");
		System.out.println("3- Suite");
		int tipo1 = globalServices.validacionEntrada(3);
		List<Habitacion> lsthab = new ArrayList<>();
		lsthab = Habitacion.habitacionesDisponiblesPorTipo(tipo1, fecha1, fecha2);
		if (lsthab.size() > 0) {
			for (Habitacion h : lsthab) {
				System.out.println(
						"--> Numero de habitacion: " + h.getNumeroHabitacion() + " Descripcion: " + h.getDescripcion());
				System.out.println();
			}
			System.out.println();
			System.out.println("Presione '1' para regresar");
			sc.next();
			menuHabitacion();
		} else {
			System.out.println("No hay habitaciones disponibles para este tipo.");
			System.out.println();
			System.out.println("Presione '1' para regresar");
			sc.next();
			menuHabitacion();
		}
	}

	public static List<Habitacion> habitacionesDisponiblesPorTipo(int tipo, Date fechaIni, Date fechaFin) {
		String tipoHab = null;
		List<Habitacion> lst = new ArrayList<>();
		switch (tipo) {
		case 1:
			tipoHab = "Sencilla";
			break;
		case 2:
			tipoHab = "Familiar";
			break;
		case 3:
			tipoHab = "Suite";
			break;
		default:
			break;
		}
		for (Habitacion habitacion : lstHabitacion) {
			if (habitacion.getTipo().equals(tipoHab)) {
				if (isAvailable(habitacion, fechaIni, fechaFin)) {
					lst.add(habitacion);
				}
			}

		}

		return lst;
	}

	// Metodo para verificar la disponibilidad de una habitacion en una fecha
	// especifica

	public static boolean isAvailable(Habitacion hab, Date fechaIni, Date fechaFin) {
		org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
		Calendar fechaIniAux = Calendar.getInstance();
		fechaIniAux.setTime(fechaIni);
		Calendar fechaFinAux = Calendar.getInstance();
		fechaFinAux.setTime(fechaFin);
		boolean available = true;
		String string1 = fechaIniAux.get(Calendar.DATE) + "/" + (fechaIniAux.get(Calendar.MONTH) + 1) + "/"
				+ fechaIniAux.get(Calendar.YEAR);
		String string2 = fechaFinAux.get(Calendar.DATE) + "/" + (fechaFinAux.get(Calendar.MONTH) + 1) + "/"
				+ fechaFinAux.get(Calendar.YEAR);
		DateTime fechaInicio2 = formatter.parseDateTime(string1);
		DateTime fechaFin2 = formatter.parseDateTime(string2);

		for (Map.Entry<Pair<String, String>, Integer> x : hab.getBusyDates().entrySet()) {
			DateTime fechaInicio1 = formatter.parseDateTime(x.getKey().toString().split("=")[0]);
			DateTime fechaFin1 = formatter.parseDateTime(x.getKey().toString().split("=")[1]);

			Interval intervalo1 = new Interval(fechaInicio1, fechaFin1);
			Interval intervalo2 = new Interval(fechaInicio2, fechaFin2);
			if (intervalo1.overlaps(intervalo2)) {
				available = false;
				break;
			} else {
				available = true;
			}
		}

		return available;
	}

	/*
	 * Metodo para asignar una habitacion, debe verificar que la fecha sea valida,
	 * es decir, que no sea del pasado o que la fecha inicial sea mayor a la fecha
	 * final
	 */

	public static void ocuparHabitacion(Habitacion hab, Date fechaIni, Date fechaFin, int idReserva) {
		Calendar fechaIniAux = Calendar.getInstance();
		fechaIniAux.setTime(fechaIni);
		Calendar fechaFinAux = Calendar.getInstance();
		fechaFinAux.setTime(fechaFin);
		String string1 = fechaIniAux.get(Calendar.DATE) + "/" + (fechaIniAux.get(Calendar.MONTH) + 1) + "/"
				+ fechaIniAux.get(Calendar.YEAR);
		String string2 = fechaFinAux.get(Calendar.DATE) + "/" + (fechaFinAux.get(Calendar.MONTH) + 1) + "/"
				+ fechaFinAux.get(Calendar.YEAR);

		Pair<String, String> fechas = new Pair<>(string1, string2);
		hab.busyDates.put(fechas, idReserva);
		Reserva.ActualizarHabitacion(hab);
	}

	// Metodo para guardar cambios en la base de datos de Habitacion

	public static boolean Guardar() {
		ObjectOutputStream oos;
		boolean error = true;
		File HabitacionFile = new File("src/Hotel/BaseDatos/Habitacion");
		try {
			oos = new ObjectOutputStream(new FileOutputStream(HabitacionFile));
			oos.writeObject(Habitacion.lstHabitacion);
			oos.close();
			error = false;
		} catch (IOException e) {
			System.out.println("Error al intentar guardar Habitaciones\n    -> Error: " + e.getMessage());
			error = true;
		}
		return !error;
	}

	// Metodo para cargar los datos almacenados en la base de datos de Habitacion

	@SuppressWarnings("unchecked")
	public static boolean Cargar() {
		ObjectInputStream ois;
		boolean error = true;
		File HabitacionFile = new File("src/Hotel/BaseDatos/Habitacion");

		try {
			ois = new ObjectInputStream(new FileInputStream(HabitacionFile));
			Habitacion.lstHabitacion = (List<Habitacion>) ois.readObject();
			error = false;
		} catch (IOException e) {
			System.out.println("No hay habitaciones guardadas\n    -> Error: " + e.getMessage());
			error = true;
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Error al intentar leer Habitaciones\n    -> Error: " + ae.getMessage());
			error = true;
		} catch (ClassNotFoundException ce) {
			System.out.println("Error al intentar leer Habitaciones\n    -> Error: " + ce.getMessage());
			error = true;
		}
		return !error;
	}

	public static int getNumero() {
		return numero;
	}

	public static void setNumero(int numero) {
		Habitacion.numero = numero;
	}

	public Map<Pair<String, String>, Integer> getBusyDates() {
		return busyDates;
	}

	public void setBusyDates(Map<Pair<String, String>, Integer> busyDates) {
		this.busyDates = busyDates;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getPrecioDia() {
		return precioDia;
	}

	public void setPrecioDia(int precioDia) {
		this.precioDia = precioDia;
	}

	public int getNumeroHabitacion() {
		return numeroHabitacion;
	}

	public void setNumeroHabitacion(int numeroHabitacion) {
		this.numeroHabitacion = numeroHabitacion;
	}

	public static List<Habitacion> getLstHabitacion() {
		return lstHabitacion;
	}

	public static void setLstHabitacion(List<Habitacion> lstHabitacion) {
		Habitacion.lstHabitacion = lstHabitacion;
	}

}
