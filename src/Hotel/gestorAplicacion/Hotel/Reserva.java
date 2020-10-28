package gestorAplicacion.Hotel;

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
import java.util.List;
import java.util.Scanner;

import gestorAplicacion.Terceros.Cliente;
import uiMain.MenuController;
import uiMain.global;

public class Reserva implements Serializable {

	private static final long serialVersionUID = 1L;
	private static int numReserva;
	private Date fecha;
	private Date fechaInicio;
	private Date fechaFin;
	private Cliente cliente;
	private Habitacion habitacion;
	private Pago pago;
	private int id;
	private static List<Reserva> lstReserva = new ArrayList<>();

	public Reserva(Cliente cliente, Habitacion habitacion, Date fechaInicio, Date fechaFin) {
		numReserva++;
		id = numReserva;
		this.fecha = new Date();
		this.cliente = cliente;
		this.habitacion = habitacion;
		this.cliente.setPazYSalvo(false);
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		Reserva.lstReserva.add(this);
	}

	public static void menuReserva() {
		global globalServices = new global();
		globalServices.clearScr();
		System.out.println("Reservas   ");
		System.out.println("    digite el numero de la opcion que desee:");
		System.out.println("1- Crear reserva");
		System.out.println("2- Buscar reserva");
		System.out.println("3- Eliminar reserva");
		System.out.println("4- Cancelar reserva");
		System.out.println("5- Mostrar listado de reservas");
		System.out.println("6- Regresar");

		int aux = globalServices.validacionEntrada(6);

		switch (aux) {
		case 1:
			crearReserva();
			break;
		case 2:
			buscarReserva();
			break;
		case 3:
			eliminarReserva();
			break;
		case 4:
			cancelarReserva();
			break;
		case 5:
			mostarReservasExistente();
			break;
		case 6:
			new MenuController();
			break;

		default:
			break;
		}
	}

	@SuppressWarnings("resource")
	public static void checkIn() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("     CHECK IN");
		System.out.println("Area para atender a clientes que llegan sin ninguna reserva\n");
		System.out.println("Cual es su solicitud?");
		System.out.println("1- Verificar disponibilidad de habitacion por tipo");
		System.out.println("2- Asignar habitacion al cliente");
		System.out.println("3- Mostrar habitaciones disponibles");
		System.out.println("4- Regresar");
		int tipo = globalServices.validacionEntrada(4);
		switch (tipo) {
		case 1:
			Date hoy = new Date();
			Date fechaF = Reserva.validFecha();
			System.out.println("Que tipo de habitacion desea?");
			System.out.println("1- Sencilla");
			System.out.println("2- Familiar");
			System.out.println("3- Suite");
			int tipo1 = globalServices.validacionEntrada(3);
			List<Habitacion> lsthab = new ArrayList<>();
			lsthab = Habitacion.habitacionesDisponiblesPorTipo(tipo1, hoy, fechaF);
			if (lsthab.size() > 0) {
				for (Habitacion h : lsthab) {
					System.out.println("--> Numero de habitacion: " + h.getNumeroHabitacion() + " Descripcion: "
							+ h.getDescripcion());
					System.out.println();
				}
			} else {
				System.out.println("No hay habitaciones disponibles para este tipo.");
			}
			break;
		case 2:
			Date hoy1 = new Date();
			Date fechaFin = Reserva.validFecha();
			Reserva.crearReserva(hoy1, fechaFin);
			break;
		case 3:
			boolean hay = false;
			Date hoy3 = new Date();
			Date fechaFin2 = Reserva.validFecha();
			List<Habitacion> lsthabi = new ArrayList<>();
			lsthabi = Habitacion.habitacionesDisponiblesPorTipo(1, hoy3, fechaFin2);
			if (lsthabi.size() > 0) {
				hay = true;
				for (Habitacion h : lsthabi) {
					System.out.println("--> Tipo Habitacion: " + h.getTipo() + "\n    Numero de habitacion: "
							+ h.getNumeroHabitacion() + " Descripcion: " + h.getDescripcion());
					System.out.println();
				}
			}
			lsthabi = Habitacion.habitacionesDisponiblesPorTipo(2, hoy3, fechaFin2);
			if (lsthabi.size() > 0) {
				hay = true;
				for (Habitacion h : lsthabi) {
					System.out.println("--> Tipo Habitacion: " + h.getTipo() + "\n    Numero de habitacion: "
							+ h.getNumeroHabitacion() + " Descripcion: " + h.getDescripcion());
					System.out.println();
				}
			}
			lsthabi = Habitacion.habitacionesDisponiblesPorTipo(3, hoy3, fechaFin2);
			if (lsthabi.size() > 0) {
				hay = true;
				for (Habitacion h : lsthabi) {
					System.out.println("--> Tipo Habitacion: " + h.getTipo() + "\n    Numero de habitacion: "
							+ h.getNumeroHabitacion() + " Descripcion: " + h.getDescripcion());
					System.out.println();
				}
			}
			if (!hay) {
				System.out.println("No hay habitaciones disponibles hasta esa fecha");
			}
			break;
		case 4:
			new MenuController();
			break;
		default:
			break;
		}
		System.out.println();
		System.out.println("Presione '1' para regresar");
		sc.next();
		Reserva.checkIn();
	}

	@SuppressWarnings("resource")
	public static Date validFecha() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		Date hoy = new Date();
		Date fechaF = new Date();
		boolean valid = false;
		while (!valid) {
			System.out.println("Ingrese fecha final");
			boolean DateisCorrect = false;
			while (!DateisCorrect) {
				fechaF = globalServices.StringToDate(sc.next());
				if (fechaF != null) {
					DateisCorrect = true;
				} else {
					System.out.println("Ocurrio un problema ingresando la fecha, intentelo nuevamente");
				}
			}
			if (hoy.compareTo(fechaF) <= 0) {
				valid = true;
			} else {
				System.out.println("Ingreso una fecha antigua");
				System.out.println("Desea volver a intentar?");
				System.out.println("S/N");
				boolean bien = false;
				while (!bien) {
					String res = sc.next();
					if (res.equals("s") || res.equals("S")) {
						bien = true;
					} else if (res.equals("n") || res.equals("N")) {
						System.out.println("Cancelando");
						bien = true;
						valid = true;
					} else {
						System.out.println("Entrada invalida");
						System.out.print("Desea volver a intentar? S/N ");
					}
				}

			}
		}
		return fechaF;

	}

	@SuppressWarnings("resource")
	public static void crearReserva(Date fechaIn, Date fechaFin) {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		Cliente cliente = null;
		cliente = Cliente.ClienteExist();
		if (cliente != null) {
			boolean paz = cliente.isPazYSalvo();
			if (paz == true) {
				System.out.println("Que tipo de habitacion desea reservar?");
				System.out.println("1- Sencilla ($55.000/noche)");
				System.out.println("2- Familiar ($110.000/noche)");
				System.out.println("3- Suite    ($250.000/noche)");
				int tipo = globalServices.validacionEntrada(3);
				List<Habitacion> lsthab = new ArrayList<>();
				lsthab = Habitacion.habitacionesDisponiblesPorTipo(tipo, fechaIn, fechaFin);
				if (lsthab.size() > 0) {
					for (int i = 0; i < lsthab.size(); i++) {
						System.out.println((i + 1) + "- No." + lsthab.get(i).getNumeroHabitacion() + "   -> "
								+ lsthab.get(i).getDescripcion());
					}
					int aux = globalServices.validacionEntrada(lsthab.size());
					System.out.println("Que temporada es?");
					System.out.println("1- Alta");
					System.out.println("2- Baja");
					int aux2 = globalServices.validacionEntrada(2);
					boolean term = false;
					switch (aux2) {
					case 1:
						term = true;
						break;
					case 2:
						term = false;
						break;
					default:
						break;
					}

					System.out.println("Desea confirmar Check-In?");
					System.out.print("S/N ");
					boolean bien = false;
					while (!bien) {
						String res = sc.next();
						if (res.equals("s") || res.equals("S")) {
							Reserva newReserva = new Reserva(cliente, lsthab.get(aux - 1), fechaIn, fechaFin);
							Habitacion.ocuparHabitacion(lsthab.get(aux - 1), fechaIn, fechaFin, newReserva.getId());
							cliente.setReserva(newReserva);
							;
							Pago.crearPago(newReserva, term);
							System.out.println("Numero de reserva: " + newReserva.getId());
							System.out.println("Reserva creada exitosamente");
							bien = true;
						} else if (res.equals("n") || res.equals("N")) {
							System.out.println("Cancelanding reserva...");
							bien = true;
						} else {
							System.out.println("Entrada invalida");
							System.out.print("Desea confirmar la reserva? S/N ");
						}
					}

				} else {
					System.out.println("No hay habitaciones disponibles para este tipo.");
				}
				try {
					Thread.sleep(3000);
					globalServices.GuardarSesion();
					Reserva.checkIn();
				} catch (InterruptedException e) {
					globalServices.GuardarSesion();
					Reserva.checkIn();
				}
			} else {
				System.out.println("El cliente tiene un pago pendiente");
				try {
					Thread.sleep(3000);
					globalServices.GuardarSesion();
					Reserva.checkIn();
				} catch (InterruptedException e) {
					globalServices.GuardarSesion();
					Reserva.checkIn();
				}
			}
		} else {
			System.out.println("No se pudo crear la reserva");
			try {
				Thread.sleep(3000);
				globalServices.GuardarSesion();
				Reserva.checkIn();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				Reserva.checkIn();

			}
		}

	}

	@SuppressWarnings("resource")
	public static void crearReserva() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		Cliente cliente = null;
		boolean DateisCorrect = false;
		boolean valid = false;
		System.out.println("     NUEVA RESERVA\n");

//		Ingreso del cliente
		cliente = Cliente.ClienteExist();
		globalServices.clearScr();
		if (cliente != null) {
			boolean paz = cliente.isPazYSalvo();
			if (paz == true) {
				System.out.println("Que tipo de habitacion desea reservar?");
				System.out.println("1- Sencilla ($55.000/noche)");
				System.out.println("2- Familiar ($110.000/noche)");
				System.out.println("3- Suite    ($250.000/noche)");
				int tipo = globalServices.validacionEntrada(3);
				Date fecha1 = new Date();
				Date fecha2 = new Date();
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
						Reserva.menuReserva();
					}
				}

				List<Habitacion> lstHabitaciones = new ArrayList<>();
				lstHabitaciones = Habitacion.habitacionesDisponiblesPorTipo(tipo, fecha1, fecha2);
				if (lstHabitaciones.size() > 0) {
					for (int i = 0; i < lstHabitaciones.size(); i++) {
						System.out.println((i + 1) + "- No." + lstHabitaciones.get(i).getNumeroHabitacion() + "   -> "
								+ lstHabitaciones.get(i).getDescripcion());
					}
					int aux = globalServices.validacionEntrada(lstHabitaciones.size());

					System.out.println("Desea confirmar la reserva?");
					System.out.print("S/N ");
					boolean bien = false;
					while (!bien) {
						String res = sc.next();
						if (res.equals("s") || res.equals("S")) {
							Reserva newReserva = new Reserva(cliente, lstHabitaciones.get(aux - 1), fecha1, fecha2);
							Habitacion.ocuparHabitacion(lstHabitaciones.get(aux - 1), fecha1, fecha2,
									newReserva.getId());
							System.out.println("Que temporada es?");
							System.out.println("1- Alta");
							System.out.println("2- Baja");
							int aux2 = globalServices.validacionEntrada(2);
							boolean term = false;
							switch (aux2) {
							case 1:
								term = true;
								break;
							case 2:
								term = false;
								break;
							default:
								break;
							}
							cliente.setReserva(newReserva);
							;
							Pago.crearPago(newReserva, term);
							System.out.println("Numero de reserva: " + newReserva.getId());
							System.out.println("Reserva creada exitosamente");
							bien = true;
						} else if (res.equals("n") || res.equals("N")) {
							System.out.println("Cancelanding reserva...");
							bien = true;
						} else {
							System.out.println("Entrada invalida");
							System.out.print("Desea confirmar la reserva? S/N ");
						}
					}
					try {
						Thread.sleep(3000);
						globalServices.GuardarSesion();
						Reserva.menuReserva();
					} catch (InterruptedException e) {
						globalServices.GuardarSesion();
						Reserva.menuReserva();
					}
				} else {
					System.out.println("No hay habitaciones disponibles para este tipo de habitacion");
					try {
						Thread.sleep(3000);
						globalServices.GuardarSesion();
						Reserva.menuReserva();
					} catch (InterruptedException e) {
						globalServices.GuardarSesion();
						Reserva.menuReserva();
					}
				}
			} else {
				System.out.println("El cliente tiene un pago pendiente");
				try {
					Thread.sleep(3000);
					globalServices.GuardarSesion();
					Reserva.menuReserva();
				} catch (InterruptedException e) {
					globalServices.GuardarSesion();
					Reserva.menuReserva();
				}
			}
		} else {
			System.out.println("No se pudo crear la reserva");
			try {
				Thread.sleep(3000);
				globalServices.GuardarSesion();
				Reserva.menuReserva();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				Reserva.menuReserva();

			}
		}

	}

	public static void ActualizarCliente(Cliente cli) {
		for (Reserva reserva : lstReserva) {
			if (reserva.getCliente().getCedula() == cli.getCedula()) {
				reserva.setCliente(cli);
				Pago.ActualizarReserva(reserva);
			}
		}
		Reserva.Guardar();
	}

	public static void EliminarCliente(Cliente cli) {
		for (int j = 0; j < lstReserva.size(); j++) {
			if (lstReserva.get(j).getCliente().getCedula() == cli.getCedula()) {
				lstReserva.get(j).setCliente(null);
				Pago.ActualizarReserva(lstReserva.get(j));
			}
		}
		Reserva.Guardar();
	}

	public static void EliminarHabitacion(Habitacion hab) {
		for (int j = 0; j < lstReserva.size(); j++) {
			if (lstReserva.get(j).getHabitacion().getNumeroHabitacion() == hab.getNumeroHabitacion()) {
				lstReserva.get(j).setHabitacion(null);
				Pago.ActualizarReserva(lstReserva.get(j));
			}
		}
		Reserva.Guardar();
	}

	public static void ActualizarHabitacion(Habitacion hab) {
		for (Reserva reserva : lstReserva) {
			if (reserva.getHabitacion().getNumeroHabitacion() == hab.getNumeroHabitacion()) {
				reserva.setHabitacion(hab);
				Pago.ActualizarReserva(reserva);
			}
		}
		Reserva.Guardar();
	}

	@SuppressWarnings("resource")
	public static void buscarReserva() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("     BUSQUEDA DE RESERVA\n");
		boolean confirma = false;
		if (Reserva.lstReserva.size() > 0) {
			while (!confirma) {
				System.out.println("     Ingrese el numero de la reserva:");
				int aux = globalServices.valiEntrada();
				boolean aux1 = false;
				for (Reserva r : Reserva.lstReserva) {
					if (r.getId() == aux) {
						// Se le da formato a las fechas para que imprima dd/mm/yyyy
						Calendar fechaIniAux = Calendar.getInstance();
						fechaIniAux.setTime(r.getFechaInicio());
						Calendar fechaFinAux = Calendar.getInstance();
						fechaFinAux.setTime(r.getFechaFin());
						String string1 = fechaIniAux.get(Calendar.DATE) + "/" + (fechaIniAux.get(Calendar.MONTH) + 1)
								+ "/" + fechaIniAux.get(Calendar.YEAR);
						String string2 = fechaFinAux.get(Calendar.DATE) + "/" + (fechaFinAux.get(Calendar.MONTH) + 1)
								+ "/" + fechaFinAux.get(Calendar.YEAR);

						System.out.println("Esta es su reserva: ");
						System.out.println();
						System.out.println("Numero de reserva: " + r.getId());
						System.out.println("Cliente: " + r.getCliente().getNombre());
						System.out.println("Fecha de la reserva: " + r.getFecha());
						System.out.println("Tiempo de la reserva: Desde " + string1 + " hasta " + string2);
						aux1 = true;
						break;
					}
				}
				if (!aux1) {
					System.out.println("Este numero de reserva no se encuentra registrado");
					System.out.println("Desea volver a intentar?");
					System.out.println("S/N");
					boolean bien = false;
					while (!bien) {
						String res = sc.next();
						if (res.equals("s") || res.equals("S")) {
							bien = true;
						} else if (res.equals("n") || res.equals("N")) {
							System.out.println("Busquedad cancelada");
							bien = true;
							confirma = true;
						} else {
							System.out.println("Entrada invalida");
							System.out.print("Desea volver a intentar? S/N ");
						}
					}
				} else {
					confirma = true;
					System.out.println();
					System.out.println("Presione '1' para regresar");
					sc.next();
					Reserva.menuReserva();
				}
			}
			try {
				Thread.sleep(3000);
				Reserva.menuReserva();
			} catch (InterruptedException e) {
				Reserva.menuReserva();
			}
		} else {
			System.out.println("No hay reservas registradas");
			try {
				Thread.sleep(3000);
				Reserva.menuReserva();
			} catch (InterruptedException e) {
				Reserva.menuReserva();
			}
		}
	}

	@SuppressWarnings("resource")
	public static void cancelarReserva() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("     CANCELAR RESERVA\n");
		boolean confirma = false;
		if (Reserva.lstReserva.size() > 0) {
			while (!confirma) {
				System.out.println("     Ingrese el numero de la reserva:");
				int aux = globalServices.valiEntrada();
				boolean aux1 = false;
				for (Reserva r : Reserva.lstReserva) {
					if (r.getId() == aux) {
						// Se le da formato a las fechas para que imprima dd/mm/yyyy
						Calendar fechaIniAux = Calendar.getInstance();
						fechaIniAux.setTime(r.getFechaInicio());
						Calendar fechaFinAux = Calendar.getInstance();
						fechaFinAux.setTime(r.getFechaFin());
						String string1 = fechaIniAux.get(Calendar.DATE) + "/" + (fechaIniAux.get(Calendar.MONTH) + 1)
								+ "/" + fechaIniAux.get(Calendar.YEAR);
						String string2 = fechaFinAux.get(Calendar.DATE) + "/" + (fechaFinAux.get(Calendar.MONTH) + 1)
								+ "/" + fechaFinAux.get(Calendar.YEAR);

						System.out.println("Numero de reserva: " + r.getId());
						System.out.println("Cliente: " + r.getCliente().getNombre());
						System.out.println("Fecha de la reserva: " + r.getFecha());
						System.out.println("Tiempo de la reserva: Desde " + string1 + " hasta " + string2);
						System.out.println();
						System.out.println("Esta Seguro que desea cancelar la reserva?");
						System.out.println("S/N");
						boolean bien = false;
						while (!bien) {
							String res = sc.next();
							if (res.equals("s") || res.equals("S")) {
								bien = true;
								Pago.elimarPago(r);
								Habitacion.eliminarReserva(r);
								Pago.crearPagoMulta(r);
								System.out.println("Reserva cancelada exitosamente");
								System.out.println("Multa pendiente");
								globalServices.GuardarSesion();
							} else if (res.equals("n") || res.equals("N")) {
								System.out.println("Cancelacion fallida");
								bien = true;
								confirma = true;
							} else {
								System.out.println("Entrada invalida");
								System.out.print("Desea volver a intentar? S/N ");
							}
						}
						aux1 = true;
						break;
					}
				}
				if (!aux1) {
					System.out.println("Este numero de reserva no se encuentra registrado");
					System.out.println("Desea volver a intentar?");
					System.out.println("S/N");
					boolean bien = false;
					while (!bien) {
						String res = sc.next();
						if (res.equals("s") || res.equals("S")) {
							bien = true;
						} else if (res.equals("n") || res.equals("N")) {
							System.out.println("Cancelacion fallida");
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
				Reserva.menuReserva();
			} catch (InterruptedException e) {
				Reserva.menuReserva();
			}
		} else {
			System.out.println("No hay reservas registradas");
			try {
				Thread.sleep(3000);
				Reserva.menuReserva();
			} catch (InterruptedException e) {
				Reserva.menuReserva();
			}
		}

	}

	@SuppressWarnings("resource")
	public static void eliminarReserva() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("     ELIMINAR RESERVA\n");
		boolean confirma = false;
		if (Reserva.lstReserva.size() > 0) {
			while (!confirma) {
				System.out.println("     Ingrese el numero de la reserva:");
				int aux = globalServices.valiEntrada();
				boolean aux1 = false;
				for (Reserva r : Reserva.lstReserva) {
					if (r.getId() == aux) {
						// Se le da formato a las fechas para que imprima dd/mm/yyyy
						Calendar fechaIniAux = Calendar.getInstance();
						fechaIniAux.setTime(r.getFechaInicio());
						Calendar fechaFinAux = Calendar.getInstance();
						fechaFinAux.setTime(r.getFechaFin());
						String string1 = fechaIniAux.get(Calendar.DATE) + "/" + (fechaIniAux.get(Calendar.MONTH) + 1)
								+ "/" + fechaIniAux.get(Calendar.YEAR);
						String string2 = fechaFinAux.get(Calendar.DATE) + "/" + (fechaFinAux.get(Calendar.MONTH) + 1)
								+ "/" + fechaFinAux.get(Calendar.YEAR);

						System.out.println("Numero de reserva: " + r.getId());
						System.out.println("Cliente: " + r.getCliente().getNombre());
						System.out.println("Fecha de la reserva: " + r.getFecha());
						System.out.println("Tiempo de la reserva: Desde " + string1 + " hasta " + string2);
						System.out.println();
						System.out.println("Esta Seguro que desea eliminar la reserva?");
						System.out.println("S/N");
						boolean bien = false;
						while (!bien) {
							String res = sc.next();
							if (res.equals("s") || res.equals("S")) {
								bien = true;
								Pago.elimarPago(r);
								Habitacion.eliminarReserva(r);
								r.getCliente().setPazYSalvo(true);
								r.getCliente().setReserva(null);
								eliminarReservaPagada(r);
								System.out.println("Reserva eliminada exitosamente");
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
					}
				}
				if (!aux1) {
					System.out.println("Este numero de reserva no se encuentra registrado");
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
				Reserva.menuReserva();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				Reserva.menuReserva();
			}
		} else {
			System.out.println("No hay reservas registradas");
			try {
				Thread.sleep(3000);
				globalServices.GuardarSesion();
				Reserva.menuReserva();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				Reserva.menuReserva();
			}
		}

	}

	public static void eliminarReservaPagada(Reserva r) {
		int index = 0;
		for (int i = 0; i < lstReserva.size(); i++) {
			if (lstReserva.get(i).getCliente().getCedula() == r.getCliente().getCedula()) {
				index = i;
				break;
			}
		}
		Reserva.lstReserva.remove(index);
		Pago.elimarPago(r);
	}

	@SuppressWarnings("resource")
	public static void mostarReservasExistente() {
		global globalService = new global();
		Scanner sc = new Scanner(System.in);
		globalService.clearScr();
		System.out.println("    RESERVAS EXISTENTES ACTUALMENTE\n");
		if (Reserva.lstReserva.size() > 0) {
			int n = 1;
			for (Reserva r : Reserva.lstReserva) {
				// Se le da formato a las fechas para que imprima dd/mm/yyyy
				Calendar fechaIniAux = Calendar.getInstance();
				fechaIniAux.setTime(r.getFechaInicio());
				Calendar fechaFinAux = Calendar.getInstance();
				fechaFinAux.setTime(r.getFechaFin());
				String string1 = fechaIniAux.get(Calendar.DATE) + "/" + (fechaIniAux.get(Calendar.MONTH) + 1) + "/"
						+ fechaIniAux.get(Calendar.YEAR);
				String string2 = fechaFinAux.get(Calendar.DATE) + "/" + (fechaFinAux.get(Calendar.MONTH) + 1) + "/"
						+ fechaFinAux.get(Calendar.YEAR);

				System.out.println(n + "- Numero de reserva: " + r.getId() + " Cliente: " + r.getCliente().getNombre()
						+ "\n   Habitacion No. " + r.getHabitacion().getNumeroHabitacion() + " - "
						+ r.getHabitacion().getTipo() + "\n   Fecha de reserva: Desde: " + string1 + " Hasta: "
						+ string2);
				n++;
			}
			System.out.println();
			System.out.println("Presione '1' para regresar");
			sc.next();
			Reserva.menuReserva();
		} else {
			System.out.println("No hay reservas existentes por el momento.");
			try {
				Thread.sleep(3000);
				Reserva.menuReserva();
			} catch (InterruptedException e) {
				Reserva.menuReserva();
			}
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

	public static Reserva reservaExist(int num) {
		Reserva re = null;
		if (Reserva.lstReserva.size() > 0) {
			for (Reserva r : Reserva.lstReserva) {
				if (r.getId() == num) {
					re = r;
				}
			}
			return re;
		} else {
			return re;
		}
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

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public static List<Reserva> getLstReserva() {
		return lstReserva;
	}

	public static void setLstReserva(List<Reserva> lstReserva) {
		Reserva.lstReserva = lstReserva;
	}

}