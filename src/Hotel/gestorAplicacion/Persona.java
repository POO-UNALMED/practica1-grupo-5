package gestorAplicacion;

import java.io.Serializable;

import uiMain.global;

public abstract class Persona implements Serializable {

	private static final long serialVersionUID = 1L;
	private int cedula;
	private String nombre;

	public abstract String toString();

	public Persona(int cedula, String nombre) {
		this.cedula = cedula;
		this.nombre = nombre;
	}

	public int getCedula() {
		return cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public abstract int cantidadTotal();

}
