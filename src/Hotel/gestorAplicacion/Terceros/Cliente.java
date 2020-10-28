/*Esta clase hereda de persona, representa a los clientes que son creados/llamados por otras clases como lo son:
 * Empleado,Reserva,Habitacion,Pago. Su funcion es Ocupar las habitaciones reservadas y realizar los pagos de las mismas(deudas y multas).*/
package gestorAplicacion.Terceros;

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

import gestorAplicacion.Hotel.Reserva;
import uiMain.MenuController;
import uiMain.global;

public class Cliente extends Persona implements Serializable {

	private static final long serialVersionUID = 1L;
	private Reserva reserva;
	public static int numClientes;
	private boolean pazYSalvo = true;
	private Empleado empleado;
	private static List<Cliente> lstCliente = new ArrayList<>();

	// Constructor Cliente
	public Cliente(int cedula, String nombre, Empleado empleado) {
		super(cedula, nombre);
		this.empleado = empleado;
		numClientes++;
		lstCliente.add(this);
	}

	public Cliente(int cedula, String nombre) {
		this(cedula, nombre, null);
	}

	// Metodo que imprime en la interfaz de usuario.
	public static void menuCliente() {
		global globalServices = new global();
		globalServices.clearScr();
		System.out.println("Cliente   ");
		System.out.println("    digite el numero de la opcion que desee:");
		System.out.println("1- Crear Cliente");
		System.out.println("2- Buscar Cliente");
		System.out.println("3- Editar Cliente");
		System.out.println("4- Eliminar Cliente");
		System.out.println("5- Mostrar listado de clientes");
		System.out.println("6- Regresar");

		int aux = globalServices.validacionEntrada(6);

		switch (aux) {
		case 1:
			crearCliente();
			break;
		case 2:
			buscarCliente();
			break;
		case 3:
			editarCliente();
			break;
		case 4:
			eliminarCliente();
			break;
		case 5:
			mostrarClientesExistente();
			break;
		case 6:
			new MenuController();
			break;

		default:
			break;
		}
	}

	public static void ActualizarEmpleado(Empleado emp) {
		for (Cliente cliente : lstCliente) {
			if (cliente.getEmpleado() != null) {

				if (cliente.getEmpleado().getCedula() == emp.getCedula()) {
					cliente.setEmpleado(emp);
					Reserva.ActualizarCliente(cliente);
				}
			}
			Cliente.Guardar();
		}
	}

	// Metodo que se encarga de ingresar a la base de datos los datos del cliente.
	// las llamadas a global services, se encargan de hacer la validacion de los
	// datos ingresados.
	@SuppressWarnings("resource")
	public static void crearCliente() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		Scanner scn = new Scanner(System.in);
		scn.useDelimiter("\n");
		globalServices.clearScr();
		System.out.println("     CREACION DE CLIENTE");
		System.out.println("Ingrese nombre del cliente:");
		String nom = scn.next();
		boolean clienteisCorrect = false;
		while (!clienteisCorrect) {
			System.out.println("Ingrese cedula del cliente: (Ex: 1001366265)");
			int ced = sc.nextInt();
			if (!clienteExist(ced)) {
				System.out.println("Desea ingresar el empleado que lo atiende?");
				System.out.println("S/N");
				boolean bien1 = false;
				while (!bien1) {
					String res = sc.next();
					if (res.equals("s") || res.equals("S")) {
						System.out.println("Ingrese la cedula del empleado que lo atiende:");
						int cedu = globalServices.valiEntrada();
						if (Empleado.EmpleadoExist(cedu)) {
							Empleado p = Empleado.EmpleadoPorCedula(cedu);
							new Cliente(ced, nom, p);
							System.out.println("Creacion exitosa");
							clienteisCorrect = true;
							bien1 = true;
						} else {
							System.out.println("No existe empleado con esa cedula");
							bien1 = true;
							clienteisCorrect = true;
						}
					} else if (res.equals("n") || res.equals("N")) {
						new Cliente(ced, nom);
						System.out.println("Creacion exitosa");
						bien1 = true;
						clienteisCorrect = true;
					} else {
						System.out.println("Entrada invalida");
						System.out.print("Desea volver a intentar? S/N ");
					}
				}
			} else {
				System.out.println("Ya existe un cliente con esa cedula");
				System.out.println("Desea volver a intentar?");
				System.out.println("S/N");
				boolean bien = false;
				while (!bien) {
					String res = sc.next();
					if (res.equals("s") || res.equals("S")) {
						bien = true;
					} else if (res.equals("n") || res.equals("N")) {
						System.out.println("Creacion de cliente cancelada");
						bien = true;
						clienteisCorrect = true;
					} else {
						System.out.println("Entrada invalida");
						System.out.print("�Desea volver a intentar? S/N ");
					}
				}
			}
		}
		try {
			Thread.sleep(3000);
			globalServices.GuardarSesion();
			Cliente.menuCliente();
		} catch (InterruptedException e) {
			globalServices.GuardarSesion();
			Cliente.menuCliente();
		}

	}

	// Busca y verifica los clientes ingresados en la base de datos.
	@SuppressWarnings("resource")
	public static void buscarCliente() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("     BUSQUEDAD CLIENTE");
		boolean confirma = false;
		if (Cliente.lstCliente.size() > 0) {
			while (!confirma) {
				System.out.println("     Ingrese la cedula del cliente:");
				int aux = globalServices.valiEntrada();
				boolean aux1 = false;
				for (Cliente c : Cliente.lstCliente) {
					if (c.getCedula() == aux) {
						System.out.println("Datos del Cliente: ");
						System.out.println();
						System.out.println("Nombre: " + c.getNombre());
						System.out.println("Cedula: " + c.getCedula());
						System.out.println("Empleado a cargo: " + c.getEmpleado().getNombre());
						aux1 = true;
						break;
					}
				}
				if (!aux1) {
					System.out.println("No se encuentra ningun cliente registrado con esta cedula");
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
				Cliente.menuCliente();
			} catch (InterruptedException e) {
				Cliente.menuCliente();
			}
		} else {
			System.out.println("No hay clientes registrados");
			try {
				Thread.sleep(3000);
				Cliente.menuCliente();
			} catch (InterruptedException e) {
				Cliente.menuCliente();
			}
		}
	}

	// Modifica la informacion (Nombre,Cedula) de un cliente creado previamente.
	@SuppressWarnings("resource")
	public static void editarCliente() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		Scanner scn = new Scanner(System.in);
		scn.useDelimiter("\n");
		globalServices.clearScr();
		System.out.println("     EDICION CLIENTE");
		boolean confirma = false;
		if (Cliente.lstCliente.size() > 0) {
			while (!confirma) {
				System.out.println("     Ingrese la cedula del cliente a editar:");
				int aux = globalServices.valiEntrada();
				boolean aux1 = false;
				for (Cliente c : Cliente.lstCliente) {
					if (c.getCedula() == aux) {
						System.out.println("Datos del Cliente: ");
						System.out.println("Nombre: " + c.getNombre());
						System.out.println("Cedula: " + c.getCedula());
						System.out.println("Empleado a cargo: " + c.getEmpleado().getNombre());
						System.out.println();
						System.out.println("Que edicion desea realizar?");
						System.out.println("1- Editar nombre");
						System.out.println("2- Editar empleado a cargo");
						int aux2 = globalServices.validacionEntrada(2);
						switch (aux2) {
						case 1:
							System.out.println("Ingrese el nuevo nombre del Cliente:");
							String nom = scn.next();
							c.setNombre(nom);
							System.out.println("Cambio de nombre exitoso");
							break;
						case 2:
							boolean con = false;
							while (!con) {
								System.out.println("Ingrese la cedula del nuevo empleado a cargo:");
								int cedu = sc.nextInt();
								if (Empleado.EmpleadoExist(cedu)) {
									Empleado p = Empleado.EmpleadoPorCedula(cedu);
									c.setEmpleado(p);
									System.out.println("Cambio de empleado exitoso");
									con = true;
								} else {
									System.out.println("No se encuentra ningun empleado con esta cedula");
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
											con = true;
										} else {
											System.out.println("Entrada invalida");
											System.out.print("Desea volver a intentar? S/N ");
										}
									}
								}
							}
							break;
						default:
							break;
						}
						aux1 = true;
						Reserva.ActualizarCliente(c);
						break;
					}
				}
				if (!aux1) {
					System.out.println("No se encuentra ningun cliente registrado con esta cedula");
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
				Cliente.menuCliente();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				Cliente.menuCliente();
			}
		} else {
			System.out.println("No hay clientes registrados");
			try {
				Thread.sleep(3000);
				globalServices.GuardarSesion();
				Cliente.menuCliente();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				Cliente.menuCliente();
			}
		}
	}

	// Borra el cliente de la base de datos siempre y cuando exista.
	@SuppressWarnings("resource")
	public static void eliminarCliente() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("     ELIMINAR Cliente");
		if (Cliente.lstCliente.size() > 0) {
			boolean confirma = false;
			while (!confirma) {
				System.out.println("     Ingrese el numero de la cedula del cliente:");
				int aux = globalServices.valiEntrada();
				boolean aux1 = false;
				for (Cliente c : Cliente.lstCliente) {
					if (c.getCedula() == aux) {
						System.out.println("Datos del Cliente: ");
						System.out.println();
						System.out.println("Nombre: " + c.getNombre());
						System.out.println("Cedula: " + c.getCedula());
						System.out.println("Empleado a cargo: " + c.getEmpleado().getNombre());
						System.out.println("Esta Seguro que desea eliminar el cliente?");
						System.out.println("S/N");
						boolean bien = false;
						while (!bien) {
							String res = sc.next();
							if (res.equals("s") || res.equals("S")) {
								bien = true;
								if (c.isPazYSalvo()) {
									eliminarCliente(c);
									System.out.println("Eliminacion del cliente exitosa");
								} else {
									System.out.println("No se puede eliminar el cliente ya que tiene una deuda");
								}

							} else if (res.equals("n") || res.equals("N")) {
								System.out.println("Eliminacion del cliente cancelada");
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
					System.out.println("No se encuentra ningun cliente registrado con esta cedula");
					System.out.println("Desea volver a intentar?");
					System.out.println("S/N");
					boolean bien = false;
					while (!bien) {
						String res = sc.next();
						if (res.equals("s") || res.equals("S")) {
							bien = true;
						} else if (res.equals("n") || res.equals("N")) {
							System.out.println("Eliminacion del cliente cancelada");
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
				Cliente.menuCliente();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				Cliente.menuCliente();
			}
		} else {
			System.out.println("No hay clientes registrados");
			try {
				Thread.sleep(3000);
				globalServices.GuardarSesion();
				Cliente.menuCliente();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				Cliente.menuCliente();
			}
		}

	}

	// Sobrecarga de metodo eliminar.
	private static void eliminarCliente(Cliente c) {
		for (int i = 0; i < lstCliente.size(); i++) {
			if (lstCliente.get(i).getCedula() == c.getCedula()) {
				lstCliente.remove(i);
				break;
			}
		}
		Reserva.EliminarCliente(c);
	}

	public static void EliminarEmpleado(Empleado e) {
		for (int j = 0; j < lstCliente.size(); j++) {
			if (lstCliente.get(j).getEmpleado().getCedula() == e.getCedula()) {
				lstCliente.get(j).setEmpleado(null);
				Reserva.ActualizarCliente(lstCliente.get(j));
			}
		}
		Cliente.Guardar();
	}

	// Recorre la lista de clientes que se encuentra en la base de datos.
	@SuppressWarnings("resource")
	public static void mostrarClientesExistente() {
		global globalService = new global();
		Scanner sc = new Scanner(System.in);
		globalService.clearScr();
		System.out.println("    CLIENTES EXISTENTES ACTUALMENTE\n");
		if (Cliente.lstCliente.size() > 0) {
			int n = 1;
			for (Cliente c : Cliente.lstCliente) {
				if (c.getEmpleado() != null) {
					System.out.println(n + "- Nombre: " + c.getNombre() + "\n   Cedula: " + c.getCedula()
							+ " Empleado a Cargo: " + c.getEmpleado().getNombre());
					n++;

				} else {
					System.out.println(n + "- Nombre: " + c.getNombre() + "\n   Cedula: " + c.getCedula() + "\n");
					n++;
				}
			}
			System.out.println("Presione '1' para regresar");
			sc.next();
			Cliente.menuCliente();

		} else {
			System.out.println("No hay clientes existentes por el momento.");
			try {
				Thread.sleep(3000);
				Cliente.menuCliente();
			} catch (InterruptedException e) {
				Cliente.menuCliente();
			}
		}
	}

	// Verifica que el cliente exista, y que sus atributos sean validos.
	@SuppressWarnings("resource")
	public static Cliente ClienteExist() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese la cedula del cliente: (Ex: 1001366265)");
		boolean ClienteisCorrect = false;
		Cliente cliente = null;
		while (!ClienteisCorrect) {
			int ced = sc.nextInt();
			if (Cliente.clienteExist(ced)) {
				cliente = Cliente.ClientePorCedula(ced);
				ClienteisCorrect = true;
			} else {
				System.out.println("El cliente no esta registrado, Desea crearlo?");
				System.out.print("S/N ");
				boolean bien = false;
				while (!bien) {
					String res = sc.next();
					if (res.equals("s") || res.equals("S")) {
						cliente = newCliente(ced);
						bien = true;
						ClienteisCorrect = true;
					} else if (res.equals("n") || res.equals("N")) {
						System.out.println("Creacin de cliente cancelada");
						bien = true;
						ClienteisCorrect = true;
					} else {
						System.out.println("Entrada invlida");
						System.out.print("Desea crearlo? S/N ");
					}
				}
			}

		}
		return cliente;
	}

	public static boolean clienteExist(int cedula) {
		boolean exist = false;
		if (Cliente.lstCliente.size() > 0) {
			for (Cliente c : Cliente.lstCliente) {
				if (c.getCedula() == cedula) {
					exist = true;
					break;
				}
			}
			return exist;
		} else {
			return exist;
		}
	}

	// Guarda en el archivo binario llamado Clientes la informacion ingresada por
	// consola.
	public static boolean Guardar() {
		ObjectOutputStream oos;
		boolean error = true;
		File ClientesFile = new File("src/Hotel/BaseDatos/Clientes");
		try {
			oos = new ObjectOutputStream(new FileOutputStream(ClientesFile));
			oos.writeObject(Cliente.lstCliente);
			oos.close();
			error = false;
		} catch (IOException e) {
			System.out.println("Error al intentar guardar Clientes\n    -> Error: " + e.getMessage());
			error = true;
		}
		return !error;
	}

	// Lee y carga los datos binarios del archivo Clientes ingresados en sesiones
	// anteriores.
	@SuppressWarnings({ "unchecked" })
	public static boolean Cargar() {
		ObjectInputStream ois;
		boolean error = true;
		File ClientesFile = new File("src/Hotel/BaseDatos/Clientes");

		try {
			ois = new ObjectInputStream(new FileInputStream(ClientesFile));
			Cliente.lstCliente = (List<Cliente>) ois.readObject();
			error = false;
		} catch (IOException e) {
			System.out.println("No hay clientes guardados\n    -> Error: " + e.getMessage());
			error = true;
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Error al intentar leer Clientes\n    -> Error: " + ae.getMessage());
			error = true;
		} catch (ClassNotFoundException ce) {
			System.out.println("Error al intentar leer Clientes\n    -> Error: " + ce.getMessage());
			error = true;
		}
		if (!error) {
			Cliente.numClientes = Cliente.lstCliente.size();
		}
		return !error;
	}

	@SuppressWarnings({ "resource" })
	private static Cliente newCliente(int cedula) {
		Scanner scf = new Scanner(System.in);
		scf.useDelimiter("\n");
		System.out.println("Ingrese el nombre del cliente: ");
		String nombre = scf.next();
		Empleado employee = Empleado.EmpleadoExist();
		if (employee == null) {
			return null;
		} else {
			return new Cliente(cedula, nombre, employee);
		}
	}

	public static Cliente ClientePorCedula(int cedula) {
		Cliente Cliente = null;
		for (Cliente cliente : lstCliente) {
			if (cliente.getCedula() == cedula) {
				Cliente = cliente;
				break;
			}
		}
		return Cliente;
	}

	public int cantidadTotal() {
		return Cliente.lstCliente.size();
	}

	public void mostrarTotal() {
		for (Cliente c : Cliente.lstCliente) {
			if (c.getEmpleado() != null) {
				System.out.println("---> Nombre: " + c.getNombre() + "\n   Cedula: " + c.getCedula()
						+ " Empleado a Cargo: " + c.getEmpleado().getNombre());

			} else {
				System.out.println("---> Nombre: " + c.getNombre() + "\n   Cedula: " + c.getCedula());
			}
		}
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public boolean isPazYSalvo() {
		return pazYSalvo;
	}

	public void setPazYSalvo(boolean pazYSalvo) {
		this.pazYSalvo = pazYSalvo;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public static List<Cliente> getLstCliente() {
		return lstCliente;
	}

	public static void setLstCliente(List<Cliente> lstCliente) {
		Cliente.lstCliente = lstCliente;
	}

	public static int getNumClientes() {
		return numClientes;
	}

	public static void setNumClientes(int numClientes) {
		Cliente.numClientes = numClientes;
	}

}
