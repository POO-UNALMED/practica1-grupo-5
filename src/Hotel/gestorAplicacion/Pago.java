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

public class Pago implements Serializable {

	private static final long serialVersionUID = 1L;
	private double valor;
	private boolean temporadaAlta;
	private Reserva reserva;
	private final static int demanda = 12;
	public static List<Pago> lstPago = new ArrayList<>();

	public Pago(double valor, boolean temporadaAlta, Reserva reserva) {
		this.valor = valor;
		this.temporadaAlta = temporadaAlta;
		this.reserva = reserva;
		Pago.lstPago.add(this);
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
			System.out.println("Error al intentar leer Pagos\n    -> Error: " + e.getMessage());
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

	public String generarRecibo() {
		if (temporadaAlta) {
			this.pazYSalvo();
			return "   Hotel POOderoso   \nNumero de Reserva: " + reserva.getId() + "\nCosto de la habitacion: " + valor
					+ "$\nValor por Temporada Alta: " + demanda + "\nValor Total a Pagar: " + (valor + demanda)
					+ "&\n   \nGracias por elegirnos \nVuelva pronto";
		} else {
			this.pazYSalvo();
			return "   Hotel POOderoso   \nNumero de Reserva: " + reserva.getId() + "\nCosto de la habitacion: " + valor
					+ "$\nValor por Temporada Alta: 0$" + "\nValor Total a Pagar: " + valor
					+ "$\n   \nGracias por elegirnos \nVuelva pronto";
		}
	}
}
