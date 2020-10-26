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
	private Date fechaInicio;
	private Date fechaFin;
	private Cliente cliente;
	private Habitacion habitacion;
	private Pago pago;
	private int id;
	public static List<Reserva> lstReserva = new ArrayList<>();

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
		System.out.println("    digite el n�mero de la opci�n que desee:");
		System.out.println("1- Crear reserva");
		System.out.println("2- Buscar reserva");
		System.out.println("3- Editar reserva");
		System.out.println("4- Eliminar reserva");
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
			editarReserva();
			break;
		case 4:
			eliminarReserva();
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

	public static void crearReserva() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		Cliente cliente = null;
		boolean DateisCorrect = false;
		boolean valid = false;
		System.out.println("     NUEVA RESERVA");

//		Ingreso del cliente
		cliente = Cliente.ClienteExist();
		globalServices.clearScr();
		if (cliente != null) {
			System.out.println("�Qu� tipo de habitaci�n desea reservar?");
			System.out.println("1- Suite    ($250.000/noche)");
			System.out.println("2- Familiar ($110.000/noche)");
			System.out.println("3- Sencilla ($55.000/noche)");
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
					System.out.println((i + 1) + "- N�" + lstHabitaciones.get(i).getNumero() + "   -> "
							+ lstHabitaciones.get(i).getDescripcion());
				}
				int aux = globalServices.validacionEntrada(lstHabitaciones.size());

				Reserva newReserva = new Reserva(cliente, lstHabitaciones.get(aux - 1), fecha1, fecha2);
				System.out.println("�Desea confirmar la reserva?");
				System.out.print("S/N ");
				boolean bien = false;
				while (!bien) {
					String res = sc.next();
					if (res.equals("s") || res.equals("S")) {
						Habitacion.ocuparHabitacion(lstHabitaciones.get(aux - 1), fecha1, fecha2, newReserva.getId());
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
						cliente.setPazYSalvo(false);
						cliente.setLstReserva(newReserva);
						Pago.crearPago(newReserva, term);
						System.out.println("Reserva creada exitosamente");
						bien = true;
					} else if (res.equals("n") || res.equals("N")) {
						System.out.println("Cancelanding reserva...");
						Reserva.lstReserva.remove(newReserva);
						bien = true;
					} else {
						System.out.println("Entrada inv�lida");
						System.out.print("�Desea confirmar la reserva? S/N ");
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
				System.out.println("No hay habitaciones disponibles para este tipo de habitacion");
				try {
					Thread.sleep(1200);
					globalServices.GuardarSesion();
					new MenuController();
				} catch (InterruptedException e) {
					globalServices.GuardarSesion();
					new MenuController();
				}
			}
		} else {
			System.out.println("No se pudo crear la reserva");
			try {
				Thread.sleep(1000);
				globalServices.GuardarSesion();
				new MenuController();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				new MenuController();
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

	public static void ActualizarHabitacion(Habitacion hab) {
		for (Reserva reserva : lstReserva) {
			if (reserva.getHabitacion().getNumeroHabitacion() == hab.getNumeroHabitacion()) {
				reserva.setHabitacion(hab);
				Pago.ActualizarReserva(reserva);
			}
		}
		Reserva.Guardar();
	}

	public static void editarReserva() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("     EDICION RESERVA");
		boolean confirma = false;
		if (Reserva.lstReserva.size() > 0) {
			while (!confirma) {
				System.out.println("     Ingrese el numero de la reserva a editar:");
				int aux = globalServices.valiEntrada();
				boolean aux1 = false;
				for (Reserva r : Reserva.lstReserva) {
					if (r.getId() == aux) {
						System.out.println("Que edicion desea realizar?");
						System.out.println("1- Editar la fecha de inicio de reserva");
						System.out.println("2- Editar la fecha de fin de reserva");
						int aux2 = globalServices.validacionEntrada(2);
						switch (aux2) {
						case 1:
							Date fecha1 = new Date();
							boolean DateisCorrect = false;
							while (!DateisCorrect) {
								System.out.println("Ingrese el nuevo tipo de la habitacion:");
								fecha1 = globalServices.StringToDate(sc.next());
								if (fecha1 != null) {
									if (fecha1.compareTo(r.getFechaFin()) <= 0) {
										// r.setFechaFin(fecha1);
										DateisCorrect = true;
									} else {
										System.out.println("La fecha que ingreso es mayor que fecha Fin de su reserva");
									}
								} else {
									System.out.println("Ocurrio un problema ingresando la fecha, intentelo nuevamente");
								}
							}
							boolean p = Habitacion.isAvailable(r.getHabitacion(), fecha1, r.getFechaFin());
							if (p) {
								r.setFechaInicio(fecha1);
								System.out.println("Cambio de fecha exitoso");
							} else {
								System.out.println(
										"La habitacion ya se encuentra ocupada para el intervalo de tiempo nuevo");
							}

							break;
						case 2:
							Date fecha2 = new Date();
							boolean DateisCorrec = false;
							while (!DateisCorrec) {
								System.out.println("Ingrese el nuevo tipo de la habitacion:");
								fecha2 = globalServices.StringToDate(sc.next());
								if (fecha2 != null) {
									if (fecha2.compareTo(r.getFechaInicio()) >= 0) {
										DateisCorrec = true;
									} else {
										System.out.println(
												"La fecha que ingreso es menor que fecha Inicio de su reserva");
										System.out.println("intentelo nuevamente");
									}
								} else {
									System.out.println("Ocurrio un problema ingresando la fecha, intentelo nuevamente");
								}
							}
							boolean w = Habitacion.isAvailable(r.getHabitacion(), r.getFechaInicio(), fecha2);
							if (w) {
								r.setFechaFin(fecha2);
								System.out.println("Cambio de fecha exitoso");
							} else {
								System.out.println(
										"La habitacion ya se encuentra ocupada para el intervalo de tiempo nuevo");
							}
							break;
						default:
							break;
						}
						aux1 = true;
						Pago.ActualizarReserva(r);
						break;
					}
				}
				if (!aux1) {
					System.out.println("No se encuentra ninguna reserva registrada con este numero");
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
			System.out.println("No hay reservas registradas");
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

	public static void buscarReserva() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("     BUSQUEDA DE RESERVA");
		boolean confirma = false;
		if (Reserva.lstReserva.size() > 0) {
			while (!confirma) {
				System.out.println("     Ingrese el numero de la reserva:");
				int aux = globalServices.valiEntrada();
				boolean aux1 = false;
				for (Reserva r : Reserva.lstReserva) {
					if (r.getId() == aux) {
						System.out.println("Esta es su reserva: ");
						System.out.println();
						System.out.println("Numero de reserva: " + r.getId());
						System.out.println("Cliente: " + r.getCliente().getNombre());
						System.out.println("Fecha de la reserva: " + r.getFecha());
						System.out.println(
								"Tiempo de la reserva: Desde " + r.getFechaInicio() + " hasta " + r.getFechaFin());
						aux1 = true;
						break;
					}
				}
				if (!aux1) {
					System.out.println("Este numero de reserva no se encuentra registrado");
					System.out.println("�Desea volver a intentar?");
					System.out.println("S/N");
					boolean bien = false;
					while (!bien) {
						String res = sc.next();
						if (res.equals("s") || res.equals("S")) {
							bien = true;
						} else if (res.equals("n") || res.equals("N")) {
							System.out.println("Feliz dia");
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
			System.out.println("No hay reservas registradas");
			try {
				Thread.sleep(1200);
				new MenuController();
			} catch (InterruptedException e) {
				new MenuController();
			}
		}
	}

	public static void eliminarReserva() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("     ELIMINAR RESERVA");
		boolean confirma = false;
		if (Reserva.lstReserva.size() > 0) {
			while (!confirma) {
				System.out.println("     Ingrese el numero de la reserva:");
				int aux = globalServices.valiEntrada();
				boolean aux1 = false;
				for (Reserva r : Reserva.lstReserva) {
					if (r.getId() == aux) {
						System.out.println("Numero de reserva: " + r.getId());
						System.out.println("Cliente: " + r.getCliente().getNombre());
						System.out.println("Fecha de la reserva: " + r.getFecha());
						System.out.println(
								"Tiempo de la reserva: Desde " + r.getFechaInicio() + " hasta " + r.getFechaFin());
						System.out.println();
						System.out.println("�Esta Seguro que desea eliminar la reserva?");
						System.out.println("S/N");
						boolean bien = false;
						while (!bien) {
							String res = sc.next();
							if (res.equals("s") || res.equals("S")) {
								bien = true;
								Pago.elimarPago(r);
								Reserva.lstReserva.remove(r);
								System.out.println("Reserva eliminada exitosamente");
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
					System.out.println("Este numero de reserva no se encuentra registrado");
					System.out.println("�Desea volver a intentar?");
					System.out.println("S/N");
					boolean bien = false;
					while (!bien) {
						String res = sc.next();
						if (res.equals("s") || res.equals("S")) {
							bien = true;
						} else if (res.equals("n") || res.equals("N")) {
							System.out.println("Feliz dia");
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
			System.out.println("No hay reservas registradas");
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

	public static void mostarReservasExistente() {
		global globalService = new global();
		Scanner sc = new Scanner(System.in);
		globalService.clearScr();
		System.out.println("    RESERVAS EXISTENTES ACTUALMENTE");
		if (Reserva.lstReserva.size() > 0) {
			int n = 1;
			for (Reserva r : Reserva.lstReserva) {
				System.out.println(n + "- Numero de reserva: " + r.getId() + " Cliente: " + r.getCliente().getNombre()
						+ "\n    Fecha de reserva: Desde: " + r.getFechaInicio() + " Hasta: " + r.getFechaFin());
				n++;
			}
			System.out.println("Presione '1' para regresar");
			sc.next();
			Reserva.menuReserva();
		} else {
			System.out.println("No hay reservas existentes por el momento.");
			try {
				Thread.sleep(1200);
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
}
