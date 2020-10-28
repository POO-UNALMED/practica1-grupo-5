/*Esta clase es la clase padre de las clases empleado  y cliente, al ser abstracta, su función es
 obligar a implementar sus métodos abstractos a sus hijos y asignarle unos atributos heredados en común*/

package gestorAplicacion.Terceros;

import java.io.Serializable;

public abstract class Persona implements Serializable {

	private static final long serialVersionUID = 1L;
	protected int cedula;
	protected String nombre;

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

	public abstract void mostrarTotal();

}
