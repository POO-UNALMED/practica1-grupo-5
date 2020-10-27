package gestorAplicacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
	private Map<Pair<Date, Date>, Integer> busyDates = new HashMap<>();
	private String tipo;
	private String descripcion;
	private int precioDia;
	private int numeroHabitacion;
	private static List<Habitacion> lstHabitacion = new ArrayList<>();

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

	public static void menuHabitacion() {
		global globalServices = new global();
		globalServices.clearScr();
		System.out.println("Habitaciones   ");
		System.out.println("    digite el n�mero de la opci�n que desee:");
		System.out.println("1- Crear habitaci�n");
		System.out.println("2- Buscar habitaci�n");
		System.out.println("3- Editar habitaci�n");
		System.out.println("4- Eliminar habitaci�n");
		System.out.println("5- Mostrar listado de habitaciones");
		System.out.println("6- Regresar");

		int aux = globalServices.validacionEntrada(6);

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
			new MenuController();
			break;
		default:
			break;
		}
	}

	public static void crearHabitacion() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		Scanner scn = new Scanner(System.in);
		scn.useDelimiter("\n");
		globalServices.clearScr();
		System.out.println("     CREACION HABITACION");
		System.out.println("�Que tipo de Habitacion desea crear?");
		System.out.println("1- Sencilla");
		System.out.println("2- Familiar");
		System.out.println("3- Suite");
		int tipo = globalServices.validacionEntrada(3);
		System.out.println("�Qu� descripci�n posee la habitaci�n?");
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
		globalServices.GuardarSesion();
		try {
			Thread.sleep(1000);
			new MenuController();
		} catch (InterruptedException e) {
			new MenuController();
		}

	}

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
					System.out.println("�Desea volver a intentar?");
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
							System.out.println("Entrada inv�lida");
							System.out.print("�Desea volver a intentar? S/N ");
						}
					}
				} else {
					confirma = true;
				}
			}
			try {
				Thread.sleep(1200);
				new MenuController();
			} catch (InterruptedException e) {
				new MenuController();
			}
		} else {
			System.out.println("No hay habitaciones registradas");
			try {
				Thread.sleep(1200);
				new MenuController();
			} catch (InterruptedException e) {
				new MenuController();
			}
		}
	}

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
					System.out.println("�Desea volver a intentar?");
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
							System.out.println("Entrada inv�lida");
							System.out.print("�Desea volver a intentar? S/N ");
						}
					}
				} else {
					confirma = true;
				}
			}
			try {
				Thread.sleep(1200);
				globalServices.GuardarSesion();
				new MenuController();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				new MenuController();
			}
		} else {
			System.out.println("No hay habitaciones registradas");
			try {
				Thread.sleep(1200);
				globalServices.GuardarSesion();
				new MenuController();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				new MenuController();
			}
		}
	}

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
						System.out.println("Numero de habitacion: " + h.getNumeroHabitacion());
						System.out.println("Tipo: " + h.getTipo());
						System.out.println("Descripcion: " + h.getDescripcion());
						System.out.println();
						System.out.println("�Esta Seguro que desea eliminar la Habitacion?");
						System.out.println("S/N");
						boolean bien = false;
						while (!bien) {
							String res = sc.next();
							if (res.equals("s") || res.equals("S")) {
								bien = true;
								Habitacion.lstHabitacion.remove(h);
								System.out.println("Eliminacion exitosa");
							} else if (res.equals("n") || res.equals("N")) {
								System.out.println("Eliminacion cancelada");
								bien = true;
								confirma = true;
							} else {
								System.out.println("Entrada inv�lida");
								System.out.print("�Desea volver a intentar? S/N ");
							}
						}
						aux1 = true;
						break;
					}
				}
				if (!aux1) {
					System.out.println("Este numero de habitacion no se encuentra registrado");
					System.out.println("�Desea volver a intentar?");
					System.out.println("S/N");
					boolean bien = false;
					while (!bien) {
						String res = sc.next();
						if (res.equals("s") || res.equals("S")) {
							bien = true;
							System.out.println("Eliminacion exitosa");
						} else if (res.equals("n") || res.equals("N")) {
							System.out.println("Eliminacion cancelada");
							bien = true;
							confirma = true;
						} else {
							System.out.println("Entrada inv�lida");
							System.out.print("�Desea volver a intentar? S/N ");
						}
					}
				} else {
					confirma = true;
				}
			}
			try {
				Thread.sleep(1200);
				globalServices.GuardarSesion();
				new MenuController();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				new MenuController();
			}
		} else {
			System.out.println("No hay habitaciones registradas");
			try {
				Thread.sleep(1200);
				globalServices.GuardarSesion();
				new MenuController();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				new MenuController();
			}
		}

	}

	public static void mostrarHabitacionesExistente() {
		global globalService = new global();
		Scanner sc = new Scanner(System.in);
		globalService.clearScr();
		System.out.println("    HABITACIONES EXISTENTES ACTUALMENTE");
		if (Habitacion.lstHabitacion.size() > 0) {
			int n = 1;
			for (Habitacion h : Habitacion.lstHabitacion) {
				System.out.println(n + "- Numero de habitacion: " + h.getNumeroHabitacion() + " Descripcion: "
						+ h.getDescripcion() + "\n   Tipo: " + h.getTipo() + "  Precio por dia : " + h.getPrecioDia());
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
				Thread.sleep(1200);
				Habitacion.menuHabitacion();
			} catch (InterruptedException e) {
				Habitacion.menuHabitacion();
			}
		}
	}

	public static List<Habitacion> habitacionesDisponiblesPorTipo(int tipo, Date fechaIni, Date fechaFin) {
		String tipoHab = null;
		List<Habitacion> lst = new ArrayList<>();
		switch (tipo) {
		case 1:
			tipoHab = "Suite";
			break;
		case 2:
			tipoHab = "Familiar";
			break;
		case 3:
			tipoHab = "Sencilla";
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

	@SuppressWarnings("deprecation")
	public static boolean isAvailable(Habitacion hab, Date fechaIni, Date fechaFin) {
		org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
		global globalServices = new global();
		Date fecha1 = null;
		Date fecha2 = null;
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

		for (Map.Entry<Pair<Date, Date>, Integer> x : hab.getBusyDates().entrySet()) {
			fecha1 = globalServices.StringToDate(x.getKey().toString().split("=")[0]);
			fecha2 = globalServices.StringToDate(x.getKey().toString().split("=")[1]);
			DateTime fechaInicio1 = formatter.parseDateTime(fecha1.toString());
			DateTime fechaFin1 = formatter.parseDateTime(fecha2.toString());

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

	public static void ocuparHabitacion(Habitacion hab, Date fechaIni, Date fechaFin, int idReserva) {
		Pair<Date, Date> fechas = new Pair<>(fechaIni, fechaFin);
		hab.busyDates.put(fechas, idReserva);
	}

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

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Map<Pair<Date, Date>, Integer> getBusyDates() {
		return busyDates;
	}

	public void setBusyDates(Map<Pair<Date, Date>, Integer> busyDates) {
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
