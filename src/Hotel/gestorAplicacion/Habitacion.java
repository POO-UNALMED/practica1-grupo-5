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

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;

import javafx.util.Pair;
import uiMain.global;

public class Habitacion implements Serializable {

	private static final long serialVersionUID = 1L;
	private int numero;
	private Map<Pair<Date, Date>, Integer> busyDates = new HashMap<>();
	private String tipo;
	private String descripcion;
	private int precioDia;
	private static List<Habitacion> lstHabitacion = new ArrayList<>();

	public Habitacion(int numero, String tipo, String descripcion) {
		this.numero = numero;

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
		System.out.println(string1 + "\n" + string2);
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
		Pair<Date, Date> fechas = new Pair<>(new Date(), new Date());
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
			System.out.println("Error al intentar leer Habitaciones\n    -> Error: " + e.getMessage());
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

	public int valor() {
		return 0;
	}

}
