/* Esta clase es la encargada de crear los resúmenes de todo lo relacionado con el pago
sea de las deudas que tiene los clientes con el hotel o la nómina de los empleados del hotel*/

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
import java.util.List;
import java.util.Scanner;

import gestorAplicacion.Terceros.Cliente;
import gestorAplicacion.Terceros.Empleado;
import uiMain.MenuController;
import uiMain.global;

public class Pago implements Serializable {

	// Definición de atributos

	private static final long serialVersionUID = 1L;
	private double valor;
	private boolean temporadaAlta;
	private Reserva reserva;
	private final static int multa = 20000;
	private final static int demanda = 20000;
	private static double pagos;
	private static double egreso;
	private static double caja;
	public static List<Pago> lstPago = new ArrayList<>();

	// Contructores

	public Pago(double valor, boolean temporadaAlta, Reserva reserva) {
		this.valor = valor;
		this.temporadaAlta = temporadaAlta;
		this.reserva = reserva;
		Pago.lstPago.add(this);
	}

	// Creación del menu de pago

	public static void menuPago() {
		global globalServices = new global();
		globalServices.clearScr();
		System.out.println("Pagos   ");
		System.out.println("    digite el numero de la opcion que desee:");
		System.out.println("1- Pagar factura");
		System.out.println("2- Mostrar pagos pendientes");
		System.out.println("3- Informacion de caja");
		System.out.println("4- Pagar empleados (nomina)");
		System.out.println("5- Regresar");

		int aux = globalServices.validacionEntrada(5);

		switch (aux) {
		case 1:
			pagarFactura();
			break;
		case 2:
			mostrarPagosPendientes();
			break;
		case 3:
			caja();
			break;
		case 4:
			pagarEmpleados();
			break;
		case 5:
			new MenuController();
			break;
		default:
			break;
		}
	}

	/*
	 * Método para calcular el pago que genera una reserva, para esto recibe un
	 * objeto de tipo reserva que contiene las fechas inicial y final de la reserva
	 * de la habitacion y un atributo booleano que indica si la temporada es
	 * alta(true) o baja(false)
	 */

	public static void crearPago(Reserva re, boolean temporada) {
		global globalServices = new global();
		int milisecondsByDay = 86400000;
		int dias = (int) ((re.getFechaFin().getTime() - re.getFechaInicio().getTime()) / milisecondsByDay);
		double costo = dias * re.getHabitacion().getPrecioDia();
		if (temporada) {
			costo += Pago.demanda;
		}
		re.setPago(new Pago(costo, temporada, re));
		System.out.println("Pago creado exitosamente");
		globalServices.GuardarSesion();
	}

	// Método para generar multa por cancelar una reserva

	public static void crearPagoMulta(Reserva re) {
		global globalServices = new global();
		re.setPago(new Pago(multa, false, re));
		System.out.println("Pago multa creado exitosamente");
		globalServices.GuardarSesion();
	}

	// Eliminar pago cuando el cliente lo cancela(paga)

	public static void eliminarPagoRealizado(Pago p) {
		int index = 0;
		for (int i = 0; i < lstPago.size(); i++) {
			if (lstPago.get(i).reserva.getId() == p.getReserva().getId()) {
				index = i;
				break;
			}
		}
		Pago.lstPago.remove(index);
	}

	/*
	 * Menú de cancelación de factura, para esto pide la cedula del cliente al que
	 * va a efectuar la factura y al final cambiar el atributo pazYSalvo a true
	 */

	@SuppressWarnings("resource")
	public static void pagarFactura() {
		DecimalFormat moneda = new DecimalFormat("###,###");
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("CANCELACION DE FACTURA ");
		System.out.println();
		boolean confirma = false;
		while (!confirma) {
			System.out.println("Ingrese la cédula del cliente: (Ex: 1001366265)");
			int ced = sc.nextInt();
			boolean aux = false;
			if (Cliente.clienteExist(ced)) {
				Cliente cliente = Cliente.ClientePorCedula(ced);
				if (cliente.getReserva() != null) {
					System.out.println("Pago pendinte");
					System.out.println("--> Reserva: " + cliente.getReserva().getId() + " Valor: $ "
							+ moneda.format(cliente.getReserva().getPago().getValor()));
					System.out.println("Desea pagarlo?");
					System.out.println("S/N");
					boolean bien = false;
					while (!bien) {
						String res = sc.next();
						if (res.equals("s") || res.equals("S")) {
							cliente.getReserva().getPago().imprimeFactura(cliente.getReserva().getId()); // Imprime
																											// factura
							Pago.ingresoCaja(cliente.getReserva().getPago().getValor()); // Aumenta caja
							Reserva.eliminarReservaPagada(cliente.getReserva()); // elimina reserva y pago de lStRESERVA
							Habitacion.eliminarReserva(cliente.getReserva()); // Se elimina la reserva que ya finalizó
							cliente.setPazYSalvo(true); // El cliente ahora queda a paz y salvo
							cliente.getReserva().setPago(null); // Elimina pago reserva(opcional)
							cliente.setReserva(null); // Elimina reserva cliente
							System.out.println("\nFactura pagada exitosamente");
							bien = true;
						} else if (res.equals("n") || res.equals("N")) {
							System.out.println("Cancelado");
							bien = true;
						} else {
							System.out.println("Entrada invalida");
							System.out.print("Desea volver a intentar? S/N ");
						}
					}
				} else {
					System.out.println("Este usuario no tiene pagos pendientes");
				}
				aux = true;
			}
			if (aux) {
				confirma = true;
				System.out.println("Presione '1' para regresar");
				sc.next();
				Pago.menuPago();
			} else {
				System.out.println("No se encuentra ningun cliente registrado con este numero");
				System.out.println("Desea volver a intentar?");
				System.out.println("S/N");
				boolean bien = false;
				while (!bien) {
					String res = sc.next();
					if (res.equals("s") || res.equals("S")) {
						bien = true;
					} else if (res.equals("n") || res.equals("N")) {
						System.out.println("Cancelado");
						bien = true;
						confirma = true;
					} else {
						System.out.println("Entrada invalida");
						System.out.print("Desea volver a intentar? S/N ");
					}
				}
			}
		}
		globalServices.GuardarSesion();
		try {
			Thread.sleep(3000);
			Pago.menuPago();
		} catch (InterruptedException e) {
			Pago.menuPago();
		}
	}

	// Método para pagar la nómina de los empleados del hotel

	@SuppressWarnings("resource")
	public static void pagarEmpleados() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("PAGO A EMPLEADOS");
		System.out.println("Desea pagar nomina: ");
		System.out.println("S/N");
		boolean bien = false;
		while (!bien) {
			String res = sc.next();
			if (res.equals("s") || res.equals("S")) {
				if (Empleado.getLstEmpleado().size() > 0) {
					Pago.pagos = 0;
					for (Empleado e : Empleado.getLstEmpleado()) {
						System.out.println("--> Empleado: " + e.getNombre() + "   pagado :D");
						Pago.pagos += e.getSalario();
					}
					Pago.egreso += Pago.pagos;
					System.out.println("Desea ver la caja?");
					System.out.println("S/N");
					boolean buenon = false;
					while (!buenon) {
						String resp = sc.next();
						if (resp.equals("s") || resp.equals("S")) {
							Pago.caja();
							buenon = true;
						} else if (resp.equals("n") || resp.equals("N")) {
							buenon = true;
						} else {
							System.out.println("Entrada invalida");
							System.out.print("Desea volver a intentar? S/N ");
						}

					}
					System.out.println();

					Pago.menuPago();
				} else {
					System.out.println("No hay empleados en el sistema");
					try {
						Thread.sleep(1500);
						Pago.menuPago();
					} catch (InterruptedException e) {
						Pago.menuPago();
					}
				}
				bien = true;
			} else if (res.equals("n") || res.equals("N")) {
				System.out.println("Cancelado");
				bien = true;
				try {
					Thread.sleep(3000);
					Pago.menuPago();
				} catch (InterruptedException e) {
					Pago.menuPago();
				}
			} else {
				System.out.println("Entrada invalida");
				System.out.print("Desea volver a intentar? S/N ");
			}
		}
		globalServices.GuardarSesion();

	}

	public static void ActualizarReserva(Reserva rese) {
		for (Pago pago : lstPago) {
			if (pago.getReserva().getCliente().getCedula() == rese.getCliente().getCedula()) {
				pago.setReserva(rese);
			}
		}
		Pago.Guardar();
	}

	// Método para generar resumen de pagos pendientes

	@SuppressWarnings("resource")
	public static void mostrarPagosPendientes() {
		DecimalFormat moneda = new DecimalFormat("###,###");
		global globalService = new global();
		Scanner sc = new Scanner(System.in);
		globalService.clearScr();
		System.out.println("PAGOS PENDIENTES\n");
		System.out.println();
		boolean confirma = false;
		while (!confirma) {
			System.out.println("Ingrese la cedula del cliente: (Ex: 1001366265)");
			int ced = sc.nextInt();
			boolean aux = false;
			if (Cliente.clienteExist(ced)) {
				Cliente cliente = Cliente.ClientePorCedula(ced);
				if (cliente.getReserva() != null) {
					System.out.println("--> Reserva: " + cliente.getReserva().getId() + " Valor: $ "
							+ moneda.format(cliente.getReserva().getPago().getValor()));
				} else {
					System.out.println("Este usuario no tiene pagos pendientes");
				}
				aux = true;
			}
			if (aux) {
				confirma = true;
				System.out.println();
				System.out.println("Presione '1' para regresar");
				sc.next();
				Pago.menuPago();
			} else {
				System.out.println("No se encuentra ningun cliente registrado con este numero");
				System.out.println("Â¿Desea volver a intentar?");
				System.out.println("S/N");
				boolean bien = false;
				while (!bien) {
					String res = sc.next();
					if (res.equals("s") || res.equals("S")) {
						bien = true;
					} else if (res.equals("n") || res.equals("N")) {
						System.out.println("Cancelado");
						bien = true;
						confirma = true;
					} else {
						System.out.println("Entrada invalida");
						System.out.print("Desea volver a intentar? S/N ");
					}
				}
			}
		}
		try {
			Thread.sleep(3000);
			Pago.menuPago();
		} catch (InterruptedException e) {
			Pago.menuPago();
		}
	}

	// Método para mostrar el flujo de caja del hotel

	@SuppressWarnings("resource")
	public static void caja() {
		DecimalFormat moneda = new DecimalFormat("###,###");
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("CAJA");
		System.out.println();
		System.out.println("Estado del Hotel POOderoso");
		System.out.println("Ultimo egreso: $ " + moneda.format(Pago.pagos));
		System.out.println();
		System.out.println("Ingresos obtenidos: $ " + moneda.format(Pago.caja));
		System.out.println("Egresos obtenidos: $ " + moneda.format(Pago.egreso));
		System.out.println();
		System.out.println("Presione '1' para regresar");
		sc.next();
		Pago.menuPago();
	}

	public static void ingresoCaja(double valor) {
		Pago.caja += valor;
	}

	// Método que muestra el resumen de la factura para ser entregada a un cliente

	public void imprimeFactura(int num) {
		DecimalFormat moneda = new DecimalFormat("###,###");
		for (Pago p : Pago.lstPago) {
			if (p.getReserva().getId() == num) {
				System.out.println();
				System.out.println("    FACTURA");
				System.out.println(" HOTEL POODEROSO");
				System.out.println();
				System.out.println("Cliente: " + p.getReserva().getCliente().getNombre());
				System.out.println("Habitacion tipo: " + p.getReserva().getHabitacion().getTipo());
				System.out
						.println("Costo por noche: $ " + moneda.format(p.getReserva().getHabitacion().getPrecioDia()));
				String tem = null;
				if (p.isTemporadaAlta()) {
					tem = "ALTA";
				} else {
					tem = "BAJA";
				}
				System.out.println("Temporada: " + tem);
				System.out.println("Valor total a pagar: $ " + moneda.format(p.getValor()));
			}
		}
	}

	public static void elimarPago(Reserva re) {
		global globalServices = new global();
		for (Pago pe : Pago.lstPago) {
			if (pe.getReserva().getId() == re.getId()) {
				Pago.lstPago.remove(pe);
				System.out.println("Pago eliminado");
				break;
			}
		}
		globalServices.GuardarSesion();
	}

	// Método para guardar cambios en la base de datos de Pago

	public static boolean Guardar() {
		ObjectOutputStream oos;
		boolean error = true;
		File PagoFile = new File("src/Hotel/BaseDatos/Pago");
		try {
			oos = new ObjectOutputStream(new FileOutputStream(PagoFile));
			oos.writeObject(Pago.lstPago);
			oos.close();
			error = false;
		} catch (IOException e) {
			System.out.println("Error al intentar guardar Pagos\n    -> Error: " + e.getMessage());
			error = true;
		}
		return !error;
	}

	// Método para cargar los datos almacenados en la base de datos de Pago

	@SuppressWarnings("unchecked")
	public static boolean Cargar() {
		ObjectInputStream ois;
		boolean error = true;
		File PagoFile = new File("src/Hotel/BaseDatos/Pago");

		try {
			ois = new ObjectInputStream(new FileInputStream(PagoFile));
			Pago.lstPago = (List<Pago>) ois.readObject();
			error = false;
		} catch (IOException e) {
			System.out.println("No hay pagos guardados\n    -> Error: " + e.getMessage());
			error = true;
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Error al intentar leer Pagos\n    -> Error: " + ae.getMessage());
			error = true;
		} catch (ClassNotFoundException ce) {
			System.out.println("Error al intentar leer Pagos\n    -> Error: " + ce.getMessage());
			error = true;
		}
		return !error;
	}

	// Métodos get y set de atributos

	public static double getPagos() {
		return pagos;
	}

	public static void setPagos(double pagos) {
		Pago.pagos = pagos;
	}

	public static double getEgreso() {
		return egreso;
	}

	public static void setEgreso(double egreso) {
		Pago.egreso = egreso;
	}

	public static double getCaja() {
		return caja;
	}

	public static void setCaja(double caja) {
		Pago.caja = caja;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public boolean isTemporadaAlta() {
		return temporadaAlta;
	}

	public void setTemporadaAlta(boolean temporadaAlta) {
		this.temporadaAlta = temporadaAlta;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

}
