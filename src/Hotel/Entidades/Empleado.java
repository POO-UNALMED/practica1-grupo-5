package Entidades;

import java.util.ArrayList;
import java.util.List;

public class Empleado extends Persona {
	private String cargo;
	private float salario;
	private boolean activo;
	private List<Cliente> lstCliente = new ArrayList<>();

	public Empleado(int cedula, String nombre, String cargo, float salario, boolean activo, List<Cliente> lstCliente) {
		super(cedula, nombre);
		this.cargo = cargo;
		this.salario = salario;
		this.activo = activo;
		this.lstCliente = lstCliente;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
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

	public void setLstCliente(List<Cliente> lstCliente) {
		this.lstCliente = lstCliente;
	}

}
