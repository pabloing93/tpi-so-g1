package entidades;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
	private Integer tamanioMemoria;
	private List<Proceso> procesos = new ArrayList<Proceso>();
	private List<Particion> particiones = new ArrayList<Particion>();
	
	
	public Memoria(Integer tamanioMemoria, Integer tamanioSO) {
		this.tamanioMemoria = tamanioMemoria;
		Integer posicionInicio = 0;
		Integer posicionFin = tamanioSO - 1;
		Particion particionSO = new Particion(tamanioSO, posicionInicio, posicionFin);
		this.particiones.add(particionSO);
	}
	
	public void nuevaParticion(Integer tamañoKB) {
		Integer posicionInicio = particiones.get(0).getPosicionFin()+1;
		Integer posicionFin = posicionInicio + tamañoKB - 1;
		Particion particion = new Particion(tamañoKB, posicionInicio, posicionFin);
		//Inserta el elemento particionSO en la posición 0 en esta lista
		particiones.add(0,particion);
	}

	public Integer getTamanioMemoria() {
		return tamanioMemoria;
	}

	public List<Proceso> getProcesos() {
		return procesos;
	}

	public List<Particion> getParticiones() {
		return particiones;
	}
	
	
}
