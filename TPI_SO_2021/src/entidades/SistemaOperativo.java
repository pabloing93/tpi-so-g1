package entidades;

import java.util.ArrayList;
import java.util.List;

import aux.Aux;

public class SistemaOperativo {
//Los valores que se definen son los correspondientes al enunciado del TPI
	private static Integer tamanioMemoria = 530;
	private static Integer tamanioParticionSO = 100;
	private Memoria memoria;
	private CPU cpu;
	// La cola LISTOS contiene los procesos que están cargados en memoria, esperando
	// por CPU
	private List<Proceso> listos = new ArrayList<Proceso>();
	// La cola SALIENTES contiene los procesos que terminaron su TI
	private List<Proceso> salientes = new ArrayList<Proceso>();
	// La cola NUEVOS contiene los procesos leidos de un archivo.
	private List<Proceso> nuevos = new ArrayList<Proceso>();
	// La cola LISTOSSUSPENDIDOS contiene los procesos que fueron admitidos pero NO
	// están cargados en memoria.
	private List<Proceso> listoSuspendido = new ArrayList<Proceso>();

	public SistemaOperativo(String file) {
		inicializarMemoria();
		inicializarcpu();
		cargarProcesos(file);
		System.out.println("\n\n\n----------------------------------------------------\n\n\n");
		this.run();
	}

	public void run() {
		// Mientras haya procesos en CPU o en alguna de las colas seguirá ejecutándose
		// los planificadores.
		while (!nuevos.isEmpty() || !listoSuspendido.isEmpty() || !listos.isEmpty() || !cpu.isEmpty()) {
			planificadorLargoPlazo();
			planificadorMedianoPlazo();
			planificadorCortoPlazo();

		}
	}

	// Mueve los procesos de la cola NUEVOS a ListosSuspendidos dependiendo del
	// tiempo de arribo.
	public void planificadorLargoPlazo() {
		List<Proceso> tmp = new ArrayList<Proceso>();
		for (Proceso proceso : nuevos) {
			if (proceso.getTiempoArribo() == cpu.getClock()) {
				tmp.add(proceso);
			}
		}
		nuevos.removeAll(tmp);
		listoSuspendido.addAll(tmp);
	}

	// Mueve los procesos de ListosSuspendidos a LISTOS dependiendo si hay
	// particiones libres.
	// Selecciona la partición que genere menor FI
	public void planificadorMedianoPlazo() {
		Aux.ordenarTI(listoSuspendido);
		if (memoria.isParticionEmpty()) {
			// particiones vacias
			Boolean flag = false;
			List<Particion> particiones = this.memoria.getParticiones();
			int ixLyS = 0;
			List<Proceso> tmp = new ArrayList<Proceso>();
			while (memoria.isParticionEmpty() && listoSuspendido.size() > ixLyS) {
				Proceso p = listoSuspendido.get(ixLyS);
				Integer minDif = Integer.MAX_VALUE;
				int partIX = -1;
				for (Particion part : particiones) {
					if (!part.isSo() && part.isEmpty()) {
						if ((part.getTamanio() >= p.getTamanio()) && (part.getTamanio() - p.getTamanio() < minDif)) {
							minDif = part.getTamanio() - p.getTamanio();
							partIX = particiones.indexOf(part);
						}
					}
				}
				if (partIX >= 0) {
					particiones.get(partIX).setProceso(p);
					tmp.add(p);
					flag = true;
				}
				ixLyS++;
			}
			if (flag) {
				listos.addAll(tmp);
				listoSuspendido.removeAll(tmp);
			} else {
				VerificarCpuListos(); 
			}
		} else {
			// todas las particiones llenas
			VerificarCpuListos();
		}
	}

	public void VerificarCpuListos() {
		if (listoSuspendido.size() > 0) {
			// Aux.ordenarTI(listoSuspendido);
			Proceso plYs = listoSuspendido.get(0);
			// expropiando CPU
			if (null != cpu.getProceso() && plYs.getTiempoIrrupcion() < cpu.getProceso().getTiempoIrrupcion()) {
				Aux.ordenarTI(listos);
				Integer n = listos.size() - 1;
				Integer nEncontrado = -1;
				// buscar posicion de memoria en Listos
				while (n > -1 && nEncontrado < 0) {
					Proceso pn = listos.get(n);
					if (plYs.getTamanio() <= pn.getParticion().getTamanio()) {
						nEncontrado = n;
					}
					n--;
				}
				// Se encontro una particion en listos
				if (nEncontrado > -1) {
					Proceso saliente = listos.remove((int) nEncontrado);
					Particion part = saliente.getParticion();
					saliente.setEmptyParticion();
					listoSuspendido.add(saliente);
					part.setProceso(plYs);
					listoSuspendido.remove(plYs);
					listos.add(plYs);
				} else {
					// comprobar si el proceso de cpu tiene una particion igual o mayor al tamño de
					// plYs
					if (plYs.getTamanio() <= cpu.getProceso().getParticion().getTamanio()) {
						Proceso saliente = cpu.getProceso();
						cpu.setProceso(null);
						Particion part = saliente.getParticion();
						saliente.setEmptyParticion();
						listoSuspendido.add(saliente);
						part.setProceso(plYs); 
						listoSuspendido.remove(plYs);
						listos.add(plYs);
					}

				}
			} else {
				// buscar un lugar en la cola de listos
				Aux.ordenarTI(listos);
				Integer n = listos.size() - 1;
				Integer nEncontrado = -1;
				while (n > -1 && nEncontrado < 0) {
					Proceso pp = listos.get(n);
					if (plYs.getTiempoIrrupcion() < pp.getTiempoIrrupcion()) {
						if (plYs.getTamanio() <= pp.getParticion().getTamanio()) {
							nEncontrado = n;
						}
					}
					n--;
				}
				if (nEncontrado > -1) {
					Proceso saliente = listos.remove((int) nEncontrado);
					Particion part = saliente.getParticion();
					saliente.setEmptyParticion();
					listoSuspendido.add(saliente);
					part.setProceso(plYs);
					listoSuspendido.remove(plYs);
					listos.add(plYs);
				}
			}
		}
	}

	// Selecciona el proceso a ejecutarse. Comparando los Tiempos de Irrupción.
	// (Aplicando SRTF)
	public void planificadorCortoPlazo() {
		Aux.ordenarTI(listos);
		this.terminarProceso();
		if (listos.size() > 0) {
			Proceso victima = listos.remove((int) 0);
			if (cpu.isEmpty()) {
				// procesador Vacio
				cpu.setProceso(victima);
			} else {
				// procesador con proceso
				if (cpu.getProceso().getTiempoIrrupcion() > victima.getTiempoIrrupcion()) {
					this.listos.add(cpu.getProceso());
					cpu.setProceso(victima);
				} else {
					listos.add(victima);
				}
			}
		}
		Aux.debug(this);
		cpu.incrementarClock();
	}

	// Termina el proceso actual moviéndolo a la cola SALIENTES y libera la
	// partición que ocupaba.
	public void terminarProceso() {
		if (null != cpu.getProceso()) {
			if (cpu.getProceso().getTiempoIrrupcion() == 0) {
				Aux.imprimir(this);
				salientes.add(cpu.getProceso());
				cpu.getProceso().getParticion().setProceso(null);
				cpu.getProceso().setEmptyParticion();
				cpu.setProceso(null);
				planificadorMedianoPlazo();
				Aux.ordenarTI(listos);
			}
		}
	}

//Crea las particiones fijas en memoria
	private void inicializarMemoria() {
		System.out.print("Iniciando memoria..........");
		this.memoria = new Memoria(tamanioMemoria, tamanioParticionSO);
		this.memoria.nuevaParticion(250);
		this.memoria.nuevaParticion(120);
		this.memoria.nuevaParticion(60);
		System.out.println("OK");
		System.out.println(this.memoria.getParticiones());
	}

	private void inicializarcpu() {
		System.out.print("Iniciando cpu..........");
		this.cpu = new CPU();
		System.out.println("OK");
		System.out.println(this.cpu);
	}

//Lee los procesos desde un archivo
	private void cargarProcesos(String file) {
		System.out.print("CargandoProcesos..........");
		// La lista de procesos obtenidas del archivo se mueven a la cola NUEVOS
		this.nuevos = Aux.LoadFile(file);
		Aux.ordenarTA(nuevos);
		System.out.println("OK");
		System.out.println(nuevos);
	}

	public CPU getCPU() {
		return this.cpu;
	}

	public Memoria getMemoria() {
		return this.memoria;
	}

	public List<Proceso> getListos() {
		return this.listos;
	}

	public List<Proceso> getListosSuspendidos() {
		return this.listoSuspendido;
	}

	public List<Proceso> getNuevos() {
		return this.nuevos;
	}

	public List<Proceso> getSalientes() {
		return this.salientes;
	}

}
