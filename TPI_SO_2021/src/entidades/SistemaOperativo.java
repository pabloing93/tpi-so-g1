package entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class SistemaOperativo {

	private static Integer tamanioMemoria = 530;
	private static Integer tamanioParticionSO = 100;

	private Memoria memoria;
	private Procesador procesador;
	private List<Proceso> listos = new ArrayList<Proceso>();
	private List<Proceso> terminados = new ArrayList<Proceso>();
	private List<Proceso> entrada = new ArrayList<Proceso>();
	// la cola de listos se tiene que cargar de un archivo.

	public SistemaOperativo() {
		inicializarMemoria();
		inicializarProcesador();
		cargarProcesos();
	}

	public void run() {
		//mientras hay procesos en la lista de entrada los mueve a la cola de listos dependiendo del clock. 
		//Este if se ejecuta para tener algo en la cola de listos
		if (entrada.size() > 0) {
			verLlegada();
		}
		while (listos.size() > 0 || null != procesador.getProceso()) {
			if (entrada.size() > 0) {
				verLlegada();
			}
			ordenarListos();
			terminarProceso();
			if (listos.size() > 0) {
				Proceso victima = listos.remove((int) 0);
				if (null == procesador.getProceso()) {
					// procesador Vacio
					procesador.setProceso(victima);
				} else {
					// procesador con proceso
					if (procesador.getProceso().getTiempoIrrupcion() > victima.getTiempoIrrupcion()) {
						listos.add(procesador.getProceso());
						procesador.setProceso(victima);
					} else {
						listos.add(victima);
					}
				}
			}
			imprimir();
			procesador.incrementarClock();
		}
	}

	private void imprimir() {
		System.out.println(procesador);
		System.out.println(listos);
		System.out.println(terminados);

	}

	// termina el proceso actual y lo manda a la cola de terminados
	public void terminarProceso() {
		if (null != procesador.getProceso()) {
			if (procesador.getProceso().getTiempoIrrupcion() == 0) {
				terminados.add(procesador.getProceso());
				procesador.setProceso(null);
			}
		}
	}

	public void verLlegada() {
		List<Proceso> tmp = new ArrayList<Proceso>();
		for (Proceso proceso : entrada) {
			if (proceso.getTiempoArribo() == procesador.getClock()) {
				tmp.add(proceso);
			}
		}
		listos.addAll(tmp);
		entrada.removeAll(tmp);
	}

	private void inicializarMemoria() {
		System.out.print("Iniciando memoria..........");
		this.memoria = new Memoria(tamanioMemoria, tamanioParticionSO);
		this.memoria.nuevaParticion(250);
		this.memoria.nuevaParticion(120);
		this.memoria.nuevaParticion(60);
		System.out.println("OK");
		System.out.println(this.memoria.getParticiones());
	}

	private void inicializarProcesador() {
		System.out.print("Iniciando Procesador..........");
		this.procesador = new Procesador();
		System.out.println("OK");
		System.out.println(this.procesador);
	}

	private void cargarProcesos() {
		System.out.print("CargandoProcesos..........");
		// nombre tiempoArribo tiempoIrupcion tamañoMemoriaKB
		this.entrada.add(new Proceso("proceso 1", 0, 5, 50));
		this.entrada.add(new Proceso("proceso 2", 1, 2, 30));
		this.entrada.add(new Proceso("proceso 3", 0, 7, 30));
		this.entrada.add(new Proceso("proceso 4", 2, 5, 200));
		this.entrada.add(new Proceso("proceso 5", 2, 4, 200));
		this.entrada.add(new Proceso("proceso 6", 5, 10, 100));
		ordenarEntrada();
		System.out.println("OK");
		System.out.println(entrada);
	}

	// ordena la cola de entrada por tiempo de arribo.
	private void ordenarEntrada() {
		Collections.sort(entrada, new Comparator<Proceso>() {
			@SuppressWarnings("deprecation")
			@Override
			public int compare(Proceso p1, Proceso p2) {
				return new Integer(p1.getTiempoArribo()).compareTo(new Integer(p2.getTiempoArribo()));
			}
		});
	}

	// ordena la cola de listos por tiempo de irrupcion
	private void ordenarListos() {
		Collections.sort(listos, new Comparator<Proceso>() {
			@SuppressWarnings("deprecation")
			@Override
			public int compare(Proceso p1, Proceso p2) {
				return new Integer(p1.getTiempoIrrupcion()).compareTo(new Integer(p2.getTiempoIrrupcion()));
			}
		});
	}
}
