package gestorAplicacion;

public class Pago {
	private float valor;
	private boolean temporadaAlta;
	private Reserva reserva;
	private final static int demanda = 12;

	public Pago(float valor, boolean temporadaAlta, Reserva reserva) {
		this.valor = valor;
		this.temporadaAlta = temporadaAlta;
		this.reserva = reserva;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
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
		Cliente cl = this.reserva.getCliente();
		cl.setPazYSalvo(true);

	}

	public String generarRecibo() {
		if (temporadaAlta) {
			this.pazYSalvo();
			return "   Hotel POOderoso   \nCosto de la habitacion: " + reserva.getCosto()
					+ "$\nValor por Temporada Alta: " + demanda + "\nValor Total a Pagar: "
					+ (reserva.getCosto() + demanda) + "&\n   \nGracias por elegirnos \nVuelva pronto";
		} else {
			this.pazYSalvo();
			return "   Hotel POOderoso   \nCosto de la habitacion: " + reserva.getCosto()
					+ "$\nValor por Temporada Alta: 0$" + "\nValor Total a Pagar: " + reserva.getCosto()
					+ "$\n   \nGracias por elegirnos \nVuelva pronto";
		}
	}
}
