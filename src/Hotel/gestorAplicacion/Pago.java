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
	private final static int multa = 20000;
	private final static int demanda = 20000;
	private static double pagos;
	private static double egreso;
	private static double caja;
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
		System.out.println("    digite el n�mero de la opci�n que desee:");
		System.out.println("1- Pagar factura");
		System.out.println("2- Mostrar pagos pendientes");
		System.out.println("3- Informacion de caja");
		System.out.println("4- Pagar empleados");
		

		int aux = globalServices.validacionEntrada(4);

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

		default:
			break;
		}
	}

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

	public static void crearPagoMulta(Reserva re) {
		global globalServices = new global();
		re.setPago(new Pago(multa, false, re));
		System.out.println("Pago multa creado exitosamente");
		globalServices.GuardarSesion();
	}
	
	public static void pagarFactura() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("CANCELACION DE FACTURA ");
		System.out.println();
		boolean confirma=false;
		while(!confirma) {
			System.out.println("Ingrese la c�dula del cliente: (Ex: 1001366265)");
			int ced = sc.nextInt();
			boolean aux=false;
			if(Cliente.clienteExist(ced)) {
				Cliente cliente =Cliente.ClientePorCedula(ced);
				if(cliente.getReserva()!=null) { 
					System.out.println("Pago pendinte");
					System.out.println("--> Reserva: "+cliente.getReserva().getId()+" Valor: "+cliente.getReserva().getPago().getValor());
					System.out.println("Desea pagarlo?");
					System.out.println("S/N");
					boolean bien = false;
					while (!bien) {
						String res = sc.next();
						if (res.equals("s") || res.equals("S")) {
							cliente.getReserva().getPago().imprimeFactura(cliente.getReserva().getId());   // Imprime factura
							Pago.ingresoCaja(cliente.getReserva().getPago().getValor());					//Aumenta caja
							Reserva.getLstReserva().remove(cliente.getReserva());								//elimina reserva de lSrRESERVA
							Pago.lstPago.remove(cliente.getReserva().getPago());							//ELIMINA PAGO
							cliente.getReserva().setPago(null);												//Elimina pago reserva(opcional)
							cliente.setReserva(null);														//Elimina reserva cliente
							cliente.setPazYSalvo(true);
							System.out.println("Factura pagada exitosamente");
							bien = true;
						} else if (res.equals("n") || res.equals("N")) {
							System.out.println("Cancelado");
							bien = true;
						} else {
							System.out.println("Entrada inv�lida");
							System.out.print("�Desea volver a intentar? S/N ");
						}
					}
				}else {
					System.out.println("Este usuario no tiene pagos pendientes");
				}
				aux=true;
			}
			if(aux) {
				confirma=true;
				System.out.println("Presione '1' para regresar");
				sc.next();
				Pago.menuPago();
			}else {
				System.out.println("No se encuentra ningun cliente registrado con este numero");
				System.out.println("�Desea volver a intentar?");
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
						System.out.println("Entrada inv�lida");
						System.out.print("�Desea volver a intentar? S/N ");
					}
				}
			}
		}
		try {
			Thread.sleep(1200);
			Pago.menuPago();
		} catch (InterruptedException e) {
			Pago.menuPago();
		}
	}
	
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
				if(Empleado.getLstEmpleado().size()>0) {
					Pago.pagos=0;
					for(Empleado e:Empleado.getLstEmpleado()) {
						System.out.println("--> Empleado: "+e.getNombre()+"   pagado :D");
						Pago.pagos+=e.getSalario();
					}
					System.out.println();
					Pago.egreso+=Pago.pagos;
					System.out.println("Presione '1' para regresar");
					sc.next();
					Pago.menuPago();
				}else {
					System.out.println("No hay empleados en el sistema");
					try {
						Thread.sleep(1200);
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
					Thread.sleep(1200);
					Pago.menuPago();
				} catch (InterruptedException e) {
					Pago.menuPago();
				}
			} else {
				System.out.println("Entrada inv�lida");
				System.out.print("�Desea volver a intentar? S/N ");
			}
		}
		
	}

	public static void ActualizarReserva(Reserva rese) {
		for (Pago pago : lstPago) {
			if (pago.getReserva().getCliente().getCedula() == rese.getCliente().getCedula()) {
				pago.setReserva(rese);
			}
		}
		Pago.Guardar();
	}

	public static void mostrarPagosPendientes() {
		global globalService = new global();
		Scanner sc = new Scanner(System.in);
		globalService.clearScr();
		System.out.println("PAGOS PENDIENTES");
		System.out.println();
		boolean confirma=false;
		while(!confirma) {
			System.out.println("Ingrese la cedula del cliente: (Ex: 1001366265)");
			int ced = sc.nextInt();
			boolean aux=false;
			if(Cliente.clienteExist(ced)) {
				Cliente cliente =Cliente.ClientePorCedula(ced);
				if(cliente.getReserva()!=null) {
					System.out.println("--> Reserva: "+cliente.getReserva().getId()+" Valor: "+cliente.getReserva().getPago().getValor());
				}else {
					System.out.println("Este usuario no tiene pagos pendientes");
				}
				aux=true;
			}
			if(aux) {
				confirma=true;
				System.out.println("Presione '1' para regresar");
				sc.next();
				Pago.menuPago();
			}else {
				System.out.println("No se encuentra ningun cliente registrado con este numero");
				System.out.println("¿Desea volver a intentar?");
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
						System.out.println("Entrada inv�lida");
						System.out.print("�Desea volver a intentar? S/N ");
					}
				}
			}
		}
		try {
			Thread.sleep(1200);
			Pago.menuPago();
		} catch (InterruptedException e) {
			Pago.menuPago();
		}
	}
	
	public static void caja() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("CAJA");
		System.out.println();
		System.out.println("Estado del Hotel POOderoso");
		System.out.println("Egresos del dia: "+Pago.pagos);
		System.out.println();
		System.out.println("Ingresos obtenidos: "+Pago.caja);
		System.out.println("Egresos obtenidos: "+Pago.egreso);
		System.out.println();
		System.out.println("Presione '1' para regresar");
		sc.next();
		Pago.menuPago();
	}
	
	public static void ingresoCaja(double valor) {
		Pago.caja+=valor;
	}
	
	public void imprimeFactura(int num) {
		for (Pago p : Pago.lstPago) {
			if (p.getReserva().getId() == num) {
				System.out.println();
				System.out.println("    FACTURA");
				System.out.println(" HOTEL POODEROSO");
				System.out.println();
				System.out.println("Cliente: " + p.getReserva().getCliente().getNombre());
				System.out.println("Habitacion tipo: " + p.getReserva().getHabitacion().getTipo());
				System.out.println("Costo por noche: " + p.getReserva().getHabitacion().getPrecioDia());
				String tem = null;
				if (p.isTemporadaAlta()) {
					tem = "ALTA";
				} else {
					tem = "BAJA";
				}
				System.out.println("Temporada: " + tem);
				System.out.println("Valor total a pagar: " + p.getValor());
			}
		}		
	}	

	public void elimarPago(Reserva re) {
		for (Pago pe : Pago.lstPago) {
			if (pe == re.getPago()) {
				Pago.lstPago.remove(pe);
				System.out.println("Pago eliminado");
				break;
			}
		}
		globalServices.GuardarSesion();
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

}
