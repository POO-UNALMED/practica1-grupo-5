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

public class Empleado extends Persona implements Serializable {

	private static final long serialVersionUID = 1L;
	private float salario;
	private boolean activo;
	private static List<Empleado> lstEmpleado = new ArrayList<>();

	public Empleado(int cedula, String nombre, float salario) {
		super(cedula, nombre);
		this.salario = salario;
		this.activo = true;
		lstEmpleado.add(this);
	}

	public static void menuEmpleado() {
		global globalServices = new global();
		globalServices.clearScr();
		System.out.println("Empleado   ");
		System.out.println("    digite el n�mero de la opci�n que desee:");
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
						System.out.println("Creaci�n de empleado cancelada");
						bien = true;
						empleadoisCorrect = true;
					} else {
						System.out.println("Entrada inv�lida");
						System.out.print("�Desea crearlo? S/N ");
					}
				}
			}

		}
		globalServices.GuardarSesion();
		try {
			Thread.sleep(1000);
			new MenuController();
		} catch (InterruptedException e) {
			new MenuController();
		}

	}

	@SuppressWarnings("resource")
	public static void buscarEmpleado() {
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
						System.out.println("Salario: " + e.getSalario());
						aux1 = true;
						break;
					}
				}
				if (!aux1) {
					System.out.println("No se encuentra ningun empleado registrado con esta cedula");
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
			System.out.println("No hay empleados registrados");
			try {
				Thread.sleep(1200);
				new MenuController();
			} catch (InterruptedException e) {
				new MenuController();
			}
		}
	}

	@SuppressWarnings("resource")
	public static void editarEmpleado() {
		global globalServices = new global();
		Scanner sc = new Scanner(System.in);
		Scanner scn = new Scanner(System.in);
		scn.useDelimiter("\n");
		globalServices.clearScr();
		System.out.println("     EDICI�N EMPLEADO");
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
						System.out.println("Salario: " + e.getSalario());
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
						break;
					}
				}
				if (!aux1) {
					System.out.println("No se encuentra ningun empleado registrado con esta cedula");
					System.out.println("�Desea volver a intentar?");
					System.out.println("S/N");
					boolean bien = false;
					while (!bien) {
						String res = sc.next();
						if (res.equals("s") || res.equals("S")) {
							bien = true;
						} else if (res.equals("n") || res.equals("N")) {
							System.out.println("Edici�n cancelada");
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
			System.out.println("No hay empleados registrados");
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
						System.out.println("�Esta Seguro que desea eliminar el empleado?");
						System.out.println("S/N");
						boolean bien = false;
						while (!bien) {
							String res = sc.next();
							if (res.equals("s") || res.equals("S")) {
								for (Cliente c : Cliente.getLstCliente()) {
									if (c.getEmpleado().getNombre() == e.getNombre()) {
										c.getEmpleado().setNombre(null);
									}
								}
								bien = true;
								Empleado.lstEmpleado.remove(e);
								System.out.println("Eliminacion del empleado exitosa");
							} else if (res.equals("n") || res.equals("N")) {
								System.out.println("Eliminacion del empleado cancelada");
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
					System.out.println("No se encuentra ningun empleado registrado con esta cedula");
					System.out.println("�Desea volver a intentar?");
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
			System.out.println("No empleados registrados");
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

	public static void darInfo() {
		global globalServices = new global();
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
			System.out.println("Poseen un costo de: 55.000$");
			break;
		case 2:
			System.out.println("Las habitaciones Familiares cuenta con capacidad para 6 personas(3 camas, 2 bano)");
			System.out.println("Poseen un costo de: 110.000$");
			break;
		case 3:
			System.out.println(
					"Las habitaciones Suits cuenta con capacidad para 6 personas(3 camas, 2 bano, 1 salon, 1 jacuzzy)");
			System.out.println("Poseen un costo de: 250.000$");
			break;
		default:
			break;
		}
		try {
			Thread.sleep(1200);
			Empleado.menuEmpleado();
		} catch (InterruptedException e) {
			Empleado.menuEmpleado();
		}
	}

	@SuppressWarnings("resource")
	public static void mostrarEmpleadosExistente() {
		global globalService = new global();
		Scanner sc = new Scanner(System.in);
		globalService.clearScr();
		System.out.println("    EMPLEADOS EXISTENTES ACTUALMENTE");
		if (Empleado.lstEmpleado.size() > 0) {
			int n = 1;
			for (Empleado e : Empleado.lstEmpleado) {
				System.out.println(n + "- Nombre: " + e.getNombre() + "\n   Cedula: " + e.getCedula() + " Salario: "
						+ e.getSalario());
				n++;
			}
			System.out.println();
			System.out.println("Total de empleados: " + Empleado.lstEmpleado.size());
			System.out.println("Presione '1' para regresar");
			sc.next();
			Empleado.menuEmpleado();

		} else {
			System.out.println("No hay empleados existentes por el momento.");
			try {
				Thread.sleep(1200);
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

	@SuppressWarnings("resource")
	public static Empleado EmpleadoExist() {
		global globalService = new global();
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese la c�dula del empleado: (Ex: 1001366265)");
		boolean EmpleadoisCorrect = false;
		Empleado employee = null;
		while (!EmpleadoisCorrect) {
			int ced = sc.nextInt();
			if (Empleado.EmpleadoExist(ced)) {
				employee = Empleado.EmpleadoPorCedula(ced);
				EmpleadoisCorrect = true;
			} else {
				System.out.println("El empleado no est� registrado, �Desea crearlo?");
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
						System.out.println("Creaci�n de empleado cancelada");
						bien = true;
						EmpleadoisCorrect = true;
					} else {
						System.out.println("Entrada inv�lida");
						System.out.print("�Desea crearlo? S/N ");
					}
				}
			}

		}
		return employee;
	}

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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@SuppressWarnings("resource")
	public static void informacionHotel() {
		global globalService = new global();
		Scanner sc = new Scanner(System.in);
		globalService.clearScr();
		Persona p = Empleado.getLstEmpleado().get(0);
		System.out.println("El Hotel POOderoso cuenta altualmente con:");
		System.out.println("Total de empleados: " + p.cantidadTotal());
		if (Cliente.getLstCliente().size() > 0) {
			p = Cliente.getLstCliente().get(0);
			System.out.println("Total de clientes: " + p.cantidadTotal());
		} else {
			System.out.println("Total de empleados: 0");
		}
		System.out.println("Total de habitaciones: " + Habitacion.getLstHabitacion().size());
		System.out.println("Total de reservas: " + Reserva.getLstReserva().size());
		System.out.println();
		System.out.println("Presione '1' para regresar");
		sc.next();
		new MenuController();
	}

	@Override
	public String toString() {
		return null;
	}

}
