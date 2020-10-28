/*La clase empleado hereda de persona, es la encargada de ejecutar las funciones administrativas dentro del hotel.
 *  el empleado es quien crea las habitaciones y las reservas en el sistema, tambien tienen asociado un sueldo, el cual se les paga con las ganancias del hotel */
package gestorAplicacion.Terceros;

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
import java.util.List;
import java.util.Scanner;

import gestorAplicacion.Hotel.Habitacion;
import gestorAplicacion.Hotel.Reserva;
import uiMain.MenuController;
import uiMain.global;

public class Empleado extends Persona implements Serializable {

	private static final long serialVersionUID = 1L;
	private float salario;
	private static List<Empleado> lstEmpleado = new ArrayList<>();

	public Empleado(int cedula, String nombre, float salario) {
		super(cedula, nombre);
		this.salario = salario;
		lstEmpleado.add(this);
	}

	// Metodo que imprime la interfaz grafica de los empleados del hotel, permite
	// crear y modificar empleados.
	public static void menuEmpleado() {
		global globalServices = new global();
		globalServices.clearScr();
		System.out.println("Empleado   ");
		System.out.println("    digite el numero de la opcion que desee:");
		System.out.println("1- Crear Empleado");
		System.out.println("2- Buscar Empleado");
		System.out.println("3- Editar Empleado");
		System.out.println("4- Eliminar Empleado");
		System.out.println("5- Dar Informacion");
		System.out.println("6- Mostrar listado de empleados");
		System.out.println("7- Regresar");

		int aux = globalServices.validacionEntrada(7);

		switch (aux) {
		case 1:
			crearEmpleado();
			break;
		case 2:
			buscarEmpleado();
			break;
		case 3:
			editarEmpleado();
			break;
		case 4:
			eliminarEmpleado();
			break;
		case 5:
			darInfo();
			break;
		case 6:
			mostrarEmpleadosExistente();
			break;
		case 7:
			new MenuController();
			break;

		default:
			break;
		}
	}

	// Crea el objeto empleado en el sistema y lo guarda en la base de datos
	// verificando su previa existencia.
	// le atribuye nombre,cedula y salario.
	@SuppressWarnings("resource")
	public static void crearEmpleado() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		Scanner scn = new Scanner(System.in);
		scn.useDelimiter("\n");
		globalServices.clearScr();
		System.out.println("     CREACION EMPLEADO");
		System.out.println("Ingrese nombre del Empleado:");
		String nom = scn.next();
		boolean empleadoisCorrect = false;
		while (!empleadoisCorrect) {
			System.out.println("Ingrese cedula del Empleado: (Ex: 1001366265)");
			int ced = sc.nextInt();
			if (!Empleado.EmpleadoExist(ced)) {
				System.out.println("Ingrese salario del Empleado:");
				int sal = globalServices.valiEntrada();
				new Empleado(ced, nom, sal);
				System.out.println("Creacion exitosa");
				System.out.println("Feliz dia");
				empleadoisCorrect = true;
			} else {
				System.out.println("Ya existe un empleado registrado con este numero de cedula");
				System.out.println("Desea volver a intentar?");
				System.out.print("S/N ");
				boolean bien = false;
				while (!bien) {
					String res = sc.next();
					if (res.equals("s") || res.equals("S")) {
						bien = true;
					} else if (res.equals("n") || res.equals("N")) {
						System.out.println("Creacion de empleado cancelada");
						bien = true;
						empleadoisCorrect = true;
					} else {
						System.out.println("Entrada invalida");
						System.out.print("Desea crearlo? S/N ");
					}
				}
			}

		}
		globalServices.GuardarSesion();
		try {
			Thread.sleep(3000);
			menuEmpleado();
		} catch (InterruptedException e) {
			menuEmpleado();
		}

	}

	// Busca los empleados previamente creados en el sistema.
	@SuppressWarnings("resource")
	public static void buscarEmpleado() {
		DecimalFormat moneda = new DecimalFormat("###,###");
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("     BUSQUEDAD EMPLEADO");
		boolean confirma = false;
		if (Empleado.lstEmpleado.size() > 0) {
			while (!confirma) {
				System.out.println("     Ingrese la cedula del Empleado:");
				int aux = globalServices.valiEntrada();
				boolean aux1 = false;
				for (Empleado e : Empleado.lstEmpleado) {
					if (e.getCedula() == aux) {
						System.out.println("Datos del Empleado: ");
						System.out.println();
						System.out.println("Nombre: " + e.getNombre());
						System.out.println("Cedula: " + e.getCedula());
						System.out.println("Salario: " + moneda.format(e.getSalario()));
						aux1 = true;
						break;
					}
				}
				if (!aux1) {
					System.out.println("No se encuentra ningun empleado registrado con esta cedula");
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
				Empleado.menuEmpleado();
			} catch (InterruptedException e) {
				Empleado.menuEmpleado();
			}
		} else {
			System.out.println("No hay empleados registrados");
			try {
				Thread.sleep(3000);
				Empleado.menuEmpleado();
			} catch (InterruptedException e) {
				Empleado.menuEmpleado();
			}
		}
	}

	// Metodo que permite modificar los atributos de los empleados creados en el
	// sistema.
	@SuppressWarnings("resource")
	public static void editarEmpleado() {
		DecimalFormat moneda = new DecimalFormat("###,###");
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		Scanner scn = new Scanner(System.in);
		scn.useDelimiter("\n");
		globalServices.clearScr();
		System.out.println("     EDICION EMPLEADO");
		boolean confirma = false;
		if (Empleado.lstEmpleado.size() > 0) {
			while (!confirma) {
				System.out.println("     Ingrese la cedula del empleado a editar:");
				int aux = globalServices.valiEntrada();
				boolean aux1 = false;
				for (Empleado e : Empleado.lstEmpleado) {
					if (e.getCedula() == aux) {
						System.out.println("Datos del empleado");
						System.out.println("Nombre: " + e.getNombre());
						System.out.println("Cedula: " + e.getCedula());
						System.out.println("Salario: $ " + moneda.format(e.getSalario()));
						System.out.println();
						System.out.println("Que edicion desea realizar?");
						System.out.println("1- Editar nombre");
						System.out.println("2- Editar salario");
						int aux2 = globalServices.validacionEntrada(2);
						switch (aux2) {
						case 1:
							System.out.println("Ingrese el nuevo nombre del Empleado:");
							String nom = scn.next();
							e.setNombre(nom);
							System.out.println("Cambio de nombre exitoso");
							break;
						case 2:
							System.out.println("Ingrese el nuevo salario del Empleado:");
							int sal = sc.nextInt();
							e.setSalario(sal);
							System.out.println("Cambio de salario exitoso");
							break;
						default:
							break;
						}
						aux1 = true;
						Cliente.ActualizarEmpleado(e);
						globalServices.GuardarSesion();
						break;
					}
				}
				if (!aux1) {
					System.out.println("No se encuentra ningun empleado registrado con esta cedula");
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
				Empleado.menuEmpleado();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				Empleado.menuEmpleado();
			}
		} else {
			System.out.println("No hay empleados registrados");
			try {
				Thread.sleep(3000);
				globalServices.GuardarSesion();
				Empleado.menuEmpleado();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				Empleado.menuEmpleado();
			}
		}
	}

	// Borra de la base de datos un empleado previamente existente.
	@SuppressWarnings("resource")
	public static void eliminarEmpleado() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("     ELIMINAR EMPLEADO");
		boolean confirma = false;
		if (Empleado.lstEmpleado.size() > 0) {
			while (!confirma) {
				System.out.println("     Ingrese el numero de la cedula del empleado:");
				int aux = globalServices.valiEntrada();
				boolean aux1 = false;
				for (Empleado e : Empleado.lstEmpleado) {
					if (e.getCedula() == aux) {
						System.out.println("Datos del Empleado: ");
						System.out.println();
						System.out.println("Nombre: " + e.getNombre());
						System.out.println("Cedula: " + e.getCedula());
						System.out.println("Salario: " + e.getSalario());
						System.out.println("Esta Seguro que desea eliminar el empleado?");
						System.out.println("S/N");
						boolean bien = false;
						while (!bien) {
							String res = sc.next();
							if (res.equals("s") || res.equals("S")) {
								eliminarEmpleado(e);
								bien = true;
								System.out.println("Eliminacion del empleado exitosa");
							} else if (res.equals("n") || res.equals("N")) {
								System.out.println("Eliminacion del empleado cancelada");
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
					System.out.println("No se encuentra ningun empleado registrado con esta cedula");
					System.out.println("Desea volver a intentar?");
					System.out.println("S/N");
					boolean bien = false;
					while (!bien) {
						String res = sc.next();
						if (res.equals("s") || res.equals("S")) {
							bien = true;
						} else if (res.equals("n") || res.equals("N")) {
							System.out.println("Eliminacion del empleado cancelada");
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
				Empleado.menuEmpleado();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				Empleado.menuEmpleado();
			}
		} else {
			System.out.println("No hay empleados registrados");
			try {
				Thread.sleep(3000);
				globalServices.GuardarSesion();
				Empleado.menuEmpleado();
			} catch (InterruptedException e) {
				globalServices.GuardarSesion();
				Empleado.menuEmpleado();
			}
		}

	}

	// Sobrecarga del metodo borrar.
	public static void eliminarEmpleado(Empleado e) {
		for (int i = 0; i < lstEmpleado.size(); i++) {
			if (lstEmpleado.get(i).getCedula() == e.getCedula()) {
				lstEmpleado.remove(i);
				break;
			}
		}
		Cliente.EliminarEmpleado(e);
	}

	// Metodo que imprime la informacion que le brindan los empleados a los clientes
	// que preguntan por las habitaciones del hotel
	@SuppressWarnings("resource")
	public static void darInfo() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		globalServices.clearScr();
		System.out.println("    INFORMACION");
		System.out.println("Que tipo de informacion desea pedir?");
		System.out.println("1- Informacion de las habitaciones sencillas");
		System.out.println("2- Informacion de las habitaciones familiares");
		System.out.println("3- Informacion de las habitaciones suits");
		int tipo = globalServices.validacionEntrada(3);
		switch (tipo) {
		case 1:
			System.out.println("Las habitaciones Sencillas cuenta con capacidad para dos personas(1 cama, 1 bano)");
			System.out.println("Poseen un costo de: $55.000");
			break;
		case 2:
			System.out.println("Las habitaciones Familiares cuenta con capacidad para 6 personas(3 camas, 2 bano)");
			System.out.println("Poseen un costo de: $110.000");
			break;
		case 3:
			System.out.println(
					"Las habitaciones Suits cuenta con capacidad para 6 personas(3 camas, 2 bano, 1 salon, 1 jacuzzy)");
			System.out.println("Poseen un costo de: $250.000");
			break;
		default:
			break;
		}
		System.out.println();
		System.out.println("Presione '1' para regresar");
		sc.next();
		Empleado.menuEmpleado();
	}

	// Recorre la lista de empleados del sistema e imprime sus atributos.
	@SuppressWarnings("resource")
	public static void mostrarEmpleadosExistente() {
		DecimalFormat moneda = new DecimalFormat("###,###");
		global globalService = new global();
		Scanner sc = new Scanner(System.in);
		globalService.clearScr();
		System.out.println("    EMPLEADOS EXISTENTES ACTUALMENTE \n");
		if (Empleado.lstEmpleado.size() > 0) {
			int n = 1;
			for (Empleado e : Empleado.lstEmpleado) {
				System.out.println(n + "- Nombre: " + e.getNombre() + "\n   Cedula: " + e.getCedula() + " Salario: $ "
						+ moneda.format(e.getSalario()) + "\n");
				n++;
			}
			System.out.println("Total de empleados: " + Empleado.lstEmpleado.size());
			System.out.println("Presione '1' para regresar");
			sc.next();
			Empleado.menuEmpleado();

		} else {
			System.out.println("No hay empleados existentes por el momento.");
			try {
				Thread.sleep(3000);
				Empleado.menuEmpleado();
			} catch (InterruptedException e) {
				Empleado.menuEmpleado();
			}
		}
	}

	@SuppressWarnings("resource")
	public static Empleado newEmpleado(int cedula) {
		Scanner sc = new Scanner(System.in);
		Scanner scf = new Scanner(System.in);
		scf.useDelimiter("\n");
		System.out.println("Ingrese el nombre del empleado: ");
		String nombre = scf.next();
		System.out.println("Ingrese el salario de " + nombre);
		int salario = sc.nextInt();

		return new Empleado(cedula, nombre, salario);
	}

	// Verifica que el empleado exista en la base de datos, en caso de que no lo
	// este permite crearlo.
	@SuppressWarnings("resource")
	public static Empleado EmpleadoExist() {
		global globalService = new global();
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese la cedula del empleado: (Ex: 1001366265)");
		boolean EmpleadoisCorrect = false;
		Empleado employee = null;
		while (!EmpleadoisCorrect) {
			int ced = sc.nextInt();
			if (Empleado.EmpleadoExist(ced)) {
				employee = Empleado.EmpleadoPorCedula(ced);
				EmpleadoisCorrect = true;
			} else {
				System.out.println("El empleado no esta registrado, Desea crearlo?");
				System.out.print("S/N ");
				boolean bien = false;
				while (!bien) {
					String res = sc.next();
					if (res.equals("s") || res.equals("S")) {
						employee = newEmpleado(ced);
						bien = true;
						EmpleadoisCorrect = true;
						globalService.clearScr();
					} else if (res.equals("n") || res.equals("N")) {
						System.out.println("Creacion de empleado cancelada");
						bien = true;
						EmpleadoisCorrect = true;
					} else {
						System.out.println("Entrada invalida");
						System.out.print("Desea crearlo? S/N ");
					}
				}
			}

		}
		return employee;
	}

	// Guarda la informacion ingresada por consola en la base de datos, en el
	// archivo binario Empleado
	public static boolean Guardar() {
		ObjectOutputStream oos;
		boolean error = true;
		File EmpleadoFile = new File("src/Hotel/BaseDatos/Empleado");
		try {
			oos = new ObjectOutputStream(new FileOutputStream(EmpleadoFile));
			oos.writeObject(Empleado.lstEmpleado);
			oos.close();
			error = false;
		} catch (IOException e) {
			System.out.println("Error al intentar guardar Clientes\n    -> Error: " + e.getMessage());
			error = true;
		}
		return !error;
	}

	// Carga la informacion desde la base de datos de los empleados creados en
	// previas sesiones.
	@SuppressWarnings("unchecked")
	public static boolean Cargar() {
		ObjectInputStream ois;
		boolean error = true;
		File EmpleadoFile = new File("src/Hotel/BaseDatos/Empleado");

		try {
			ois = new ObjectInputStream(new FileInputStream(EmpleadoFile));
			Empleado.lstEmpleado = (List<Empleado>) ois.readObject();
			error = false;
		} catch (IOException e) {
			System.out.println("No hay empleados guardados\n    -> Error: " + e.getMessage());
			error = true;
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Error al intentar leer Empleados\n    -> Error: " + ae.getMessage());
			error = true;
		} catch (ClassNotFoundException ce) {
			System.out.println("Error al intentar leer Empleados\n    -> Error: " + ce.getMessage());
			error = true;
		}
		return !error;
	}

	// Verifica que un empleado exista ingresando su numero de cedula
	public static boolean EmpleadoExist(int cedula) {
		boolean exist = false;
		if (Empleado.lstEmpleado.size() > 0) {
			for (Empleado employee : lstEmpleado) {
				if (employee.getCedula() == cedula) {
					exist = true;
					break;
				}
			}
			return exist;
		} else {
			return exist;
		}
	}

	public static Empleado EmpleadoPorCedula(int cedula) {
		Empleado Empleado = null;
		for (Empleado empleado : lstEmpleado) {
			if (empleado.getCedula() == cedula) {
				Empleado = empleado;
				break;
			}
		}
		return Empleado;
	}

	public int cantidadTotal() {
		return Empleado.lstEmpleado.size();
	}

	public void mostrarTotal() {
		DecimalFormat moneda = new DecimalFormat("###,###");
		for (Empleado e : Empleado.lstEmpleado) {
			System.out.println("---> Nombre: " + e.getNombre() + "\n   Cedula: " + e.getCedula() + " Salario: $ "
					+ moneda.format(e.getSalario()) + "\n");
		}
	}

	public static List<Empleado> getLstEmpleado() {
		return lstEmpleado;
	}

	public static void setLstEmpleado(List<Empleado> lstEmpleado) {
		Empleado.lstEmpleado = lstEmpleado;
	}

	public float getSalario() {
		return salario;
	}

	public void setSalario(float salario) {
		this.salario = salario;
	}

	// Metodo que se encarga de imprimir la informacion del hotel, incluyedo # de
	// clientes, empleados habitaciones y reservas
	@SuppressWarnings("resource")
	public static void informacionHotel() {
		DecimalFormat moneda = new DecimalFormat("###,###");
		global globalService = new global();
		Scanner sc = new Scanner(System.in);
		globalService.clearScr();

		// En este metodo se usa de ligadura dinamica en 2 ocaciones para hacer uso de
		// los metodos de la clase Cliente
		// y Empleado, que heredan de la clase abstract Persona.

		Persona p = Empleado.getLstEmpleado().get(0);
		System.out.println("El Hotel POOderoso cuenta altualmente con:\n");
		System.out.println("Total de empleados: " + p.cantidadTotal());
		System.out.println("Listado:\n");
		p.mostrarTotal();
		if (Cliente.getLstCliente().size() > 0) {
			p = Cliente.getLstCliente().get(0);
			System.out.println("Total de clientes: " + p.cantidadTotal());
			System.out.println("Listado:\n");
			p.mostrarTotal();
		} else {
			System.out.println("Total de clientes: 0");
		}
		if (Habitacion.getLstHabitacion().size() > 0) {
			System.out.println("\nTotal de habitaciones: " + Habitacion.getLstHabitacion().size());
			System.out.println("Listado:\n");
			for (Habitacion h : Habitacion.getLstHabitacion()) {
				System.out.println("---> Numero de habitacion: " + h.getNumeroHabitacion() + " Descripcion: "
						+ h.getDescripcion() + "\n   Tipo: " + h.getTipo() + "  Precio por dia : $ "
						+ moneda.format(h.getPrecioDia()));
			}
		} else {
			System.out.println("\nTotal de habitaciones: 0");
		}
		if (Reserva.getLstReserva().size() > 0) {
			System.out.println();
			System.out.println("Total de reservas: " + Reserva.getLstReserva().size());
			System.out.println("Listado:\n");
			for (Reserva r : Reserva.getLstReserva()) {
				// Se le da formato a las fechas para que imprima dd/mm/yyyy
				Calendar fechaIniAux = Calendar.getInstance();
				fechaIniAux.setTime(r.getFechaInicio());
				Calendar fechaFinAux = Calendar.getInstance();
				fechaFinAux.setTime(r.getFechaFin());
				String string1 = fechaIniAux.get(Calendar.DATE) + "/" + (fechaIniAux.get(Calendar.MONTH) + 1) + "/"
						+ fechaIniAux.get(Calendar.YEAR);
				String string2 = fechaFinAux.get(Calendar.DATE) + "/" + (fechaFinAux.get(Calendar.MONTH) + 1) + "/"
						+ fechaFinAux.get(Calendar.YEAR);

				System.out.println("---> Numero de reserva: " + r.getId() + " Cliente: " + r.getCliente().getNombre()
						+ "\n    Fecha de reserva: Desde: " + string1 + " Hasta: " + string2);
			}
		} else {
			System.out.println();
			System.out.println("Total de reservas: 0");
		}
		System.out.println();
		System.out.println("Presione '1' para regresar");
		sc.next();
		new MenuController();
	}

}
