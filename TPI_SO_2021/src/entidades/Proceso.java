package entidades;

import java.util.Objects;

public class Proceso {
	private static Integer ultimoId = 1;
	private Integer id;
	private Integer tamanio;
	private String nombre;
	// ver que onda el estado
	private Integer tiempoArribo;
	private Integer tiempoIrrupcion;

	public Proceso(String nombre, Integer tiempoArribo, Integer tiempoIrrupcion, Integer tamanioKB) {
		this.id = Proceso.ultimoId;
		Proceso.ultimoId++;
		this.nombre = nombre;
		this.tiempoArribo = tiempoArribo;
		this.tiempoIrrupcion = tiempoIrrupcion;
		this.tamanio = tamanioKB;
	}

	public void decrementarTiempoIrupcion() {
		this.tiempoIrrupcion--;
	}
	@Override
	public String toString() {
		return "Proceso [id=" + id + ", tamanio=" + tamanio + ", nombre=" + nombre + ", tiempoArribo=" + tiempoArribo
				+ ", tiempoIrrupcion=" + tiempoIrrupcion + "]\n";
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Proceso other = (Proceso) obj;
		return Objects.equals(id, other.id);
	}


	public Integer getTamanio() {
		return tamanio;
	}

	public void setTamanio(Integer tamanio) {
		this.tamanio = tamanio;
	}


	public Integer getId() {
		return id;
	}


	public String getNombre() {
		return nombre;
	}


	public Integer getTiempoArribo() {
		return tiempoArribo;
	}


	public Integer getTiempoIrrupcion() {
		return tiempoIrrupcion;
	}

}
