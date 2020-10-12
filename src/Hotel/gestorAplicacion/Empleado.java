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

}
