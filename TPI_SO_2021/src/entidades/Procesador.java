package entidades;

public class Procesador {

	private Proceso proceso;
	private Integer clock;

	@Override
	public String toString() {
		return "Procesador [proceso=" + proceso + ", clock=" + clock + "]";
	}

	public Procesador() {
		this.clock = 0;
	}

	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public void incrementarClock() {
		this.clock++;
		if(null!=this.proceso) {
			this.proceso.decrementarTiempoIrupcion();
		}
	}

	public Integer getClock() {
		return clock;
	}

}
