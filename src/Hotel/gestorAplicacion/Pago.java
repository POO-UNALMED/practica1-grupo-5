package gestorAplicacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import uiMain.MenuController;
import uiMain.global;

public class Pago implements Serializable {

	private static final long serialVersionUID = 1L;
	private double valor;
	private boolean temporadaAlta;
	private Reserva reserva;
	private final static int demanda = 20000;
	public static List<Pago> lstPago = new ArrayList<>();

	public Pago(double valor, boolean temporadaAlta, Reserva reserva) {
		this.valor = valor;
		this.temporadaAlta = temporadaAlta;
		this.reserva = reserva;
		Pago.lstPago.add(this);
	}

	public static void menuPago() {
		global globalServices = new global();
		globalServices.clearScr();
		System.out.println("Pagos   ");
		System.out.println("    digite el número de la opción que desee:");
		System.out.println("1- Imprimir factura");
		System.out.println("2- Mostrar pagos pendientes");

		int aux = globalServices.validacionEntrada(2);

		switch (aux) {
		case 1:
			imprimeFactura();
			break;
		case 2:
			mostrarPagosPendientes();
			break;
		case 3:
//			editarCliente();
			break;

		default:
			break;
		}
	}

	public static void crearPago(Reserva re, boolean temporada) {
		// calculos de dias entre las fechas
		// mientras, puse dias 8
		global globalServices = new global();
		int dias = 8;
		double costo = dias * re.getHabitacion().getPrecioDia();
		if (temporada) {
			costo += Pago.demanda;
		}
		re.setPago(new Pago(costo, temporada, re));
		System.out.println("Pago creado exitosamente");
		globalServices.GuardarSesion();
	}

	public static void imprimeFactura() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		boolean confirma = false;
		Reserva re = null;
		if (Pago.lstPago.size() > 0) {
			while (!confirma) {
				System.out.println("Ingrese el numero de la reserva");
				int aux = globalServices.valiEntrada();
				boolean aux1 = false;
				for (Pago p : Pago.lstPago) {
					if (p.getReserva().getId() == aux) {
						System.out.println();
						System.out.println("    FACTURA");
						System.out.println(" HOTEL POODEROSO");
						System.out.println();
						System.out.println("Cliente: " + p.getReserva().getCliente().getNombre());
						System.out.println("Habitacion tipo: " + p.getReserva().getHabitacion().getTipo());
						System.out.println("Costo por noche: " + p.getValor());
						System.out.println("Total de dias: ");
						String tem = null;
						if (p.isTemporadaAlta()) {
							tem = "ALTA";
						} else {
							tem = "BAJA";
						}
						System.out.println("Temporada: " + tem);
						System.out.println("Valor total a pagar: " + p.getValor());
						aux1 = true;
					}
				}
				if (aux1) {
					confirma = true;
				} else {
					System.out.println("Este numero de reserva no se encuentra registrado");
					System.out.println("¿Desea volver a intentar?");
					System.out.println("S/N");
					boolean bien = false;
					while (!bien) {
						String res = sc.next();
						if (res.equals("s") || res.equals("S")) {
							bien = true;
						} else if (res.equals("n") || res.equals("N")) {
							System.out.println("Impresion cancelada");
							bien = true;
							confirma = true;
						} else {
							System.out.println("Entrada inválida");
							System.out.print("¿Desea volver a intentar? S/N ");
						}
					}
				}
			}
			try {
				Thread.sleep(1200);
				new MenuController();
			} catch (InterruptedException e) {
				new MenuController();
			}
		} else {
			System.out.println("No hay pagos registrados");
			try {
				Thread.sleep(1200);
				new MenuController();
			} catch (InterruptedException e) {
				new MenuController();
			}
		}
	}

	public static void mostrarPagosPendientes() {
		global globalService = new global();
		Scanner sc = new Scanner(System.in);
		globalService.clearScr();
		System.out.println("PAGOS PENDIENTES");
		System.out.println();
		if (Pago.lstPago.size() > 0) {
			int n = 1;
			for (Pago p : Pago.lstPago) {
				System.out.println("--> Cliente: " + p.getReserva().getCliente().getNombre() + " Valor pendiente: "
						+ p.getValor());
				n++;
			}
			System.out.println("Presione '1' para regresar");
			sc.next();
			Pago.menuPago();
		} else {
			System.out.println("No hay pagos pendientes");
			try {
				Thread.sleep(1200);
				Pago.menuPago();
			} catch (InterruptedException e) {
				Pago.menuPago();
			}
		}
	}

	public static void elimarPago(Reserva re) {
		for (Pago pe : Pago.lstPago) {
			if (pe == re.getPago()) {
				Pago.lstPago.remove(pe);
				System.out.println("Pago eliminado");
				break;
			}
		}
	}

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

	public void pazYSalvo() {
		reserva.getCliente().setPazYSalvo(true);

	}
}
