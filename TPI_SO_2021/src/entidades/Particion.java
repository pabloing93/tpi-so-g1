package entidades;

public class Particion {
	private static Integer ultimoId = 1;
	private Integer id;
	private Integer tamanio;
	private Integer posicionInicio;
	private Integer posicionFin;
	private Integer fragmentacionInterna;
	private Proceso proceso;

	public Particion(Integer tamanio, Integer posicionInicio, Integer posicionFin) {
		this.id = Particion.ultimoId;
		Particion.ultimoId++;
		this.tamanio = tamanio;
		this.posicionInicio = posicionInicio;
		this.posicionFin = posicionFin;
		this.proceso = null;
		this.fragmentacionInterna = 0;
	}

	
	@Override
	public String toString() {
		return "Particion [id=" + id + ", tamanio=" + tamanio + ", posicionInicio=" + posicionInicio + ", posicionFin="
				+ posicionFin + ", fragmentacionInterna=" + fragmentacionInterna + ", proceso=" + proceso + "]\n";
	}


	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
		this.calcularFragmentacionInterna();
	}

	public Integer getId() {
		return id;
	}

	public Integer getTamanio() {
		return tamanio;
	}

	public Integer getPosicionInicio() {
		return posicionInicio;
	}

	public Integer getPosicionFin() {
		return posicionFin;
	}

	public Integer getFragmentacionInterna() {
		return fragmentacionInterna;
	}

	private void calcularFragmentacionInterna() {
		this.fragmentacionInterna = 0;
		if (null != this.proceso) {
			this.fragmentacionInterna = this.tamanio - this.proceso.getTamanio();
		}
	}
}
