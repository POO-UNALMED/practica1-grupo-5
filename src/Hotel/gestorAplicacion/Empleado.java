package gestorAplicacion;

import java.util.ArrayList;
import java.util.List;

public class Empleado extends Persona {
	private float salario;
	private boolean activo;
	private static List<Cliente> lstCliente = new ArrayList<>();

	public Empleado(int cedula, String nombre, String cargo, float salario) {
		super(cedula, nombre);
		this.salario = salario;
		this.activo = true;
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

	public List<Cliente> getLstCliente() {
		return lstCliente;
	}

	public static  void setLstCliente(List<Cliente> lstCliente) {
		Empleado.lstCliente = lstCliente;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public 	Cliente registrarCliente(int cedula, String nombre) {
		Cliente c= new Cliente(cedula,nombre,this); 
		lstCliente.add(c);
		return c;
	}
	public String darInfo(int opcion) {
		if(opcion==1) {
			return "Las habitaciones Sencillas cuenta con capacidad para dos personas(1 cama, 1 baño)";
		}
		else if(opcion==2) {
			return "Las habitaciones Familiares cuenta con capacidad para 6 personas(3 camas, 2 baño)";
		}
		else{
			return "Las habitaciones Suits cuenta con capacidad para 6 personas(3 camas, 2 baño, 1 salon, 1 jacuzzy)";
		}
	}
	
	public String asignarReserva(Cliente c, Date fecha,Habitacion h) {
		Reserva r= new Reserva(fecha,c,h);
	} 

}
