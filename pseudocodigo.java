// GRUPO 1 - SISTEMAS OPERATIVOS
// Archivo del pseudocódigo

class Memoria () {
  // Atributos
  + tamaño: Real
  + tamañoDisponible: Real
  + procesos: List
  + ultimaPosicion: Real
  + primeraPosicion: Real

  // Métodos
  + actualizar_tamaño_disponible(tamaño): Boolean {
    memoria.tamaño_disponible = memoria.tamaño_disponible - tamaño;
  }
  + actualizar_ultima_posicion(tamaño): Boolean {
    memoria.ultima_posicion = memoria.ultima_posicion + tamaño;
  }
  + cargar_proceso(proceso): Boolean {
    particion.setear_fragmentacionInterna(proceso.tamaño);
    memoria.procesos.push(proceso);
	  memoria.actualizar_tamaño_disponible(proceso.tamaño);
	  memoria.actualizar_ultima_posicion(proceso.tamaño);
  }
  + crear_particion(proceso) {
    particion = new Particiones({
      // id = proceso.id;
      // tamaño = proceso.tamaño;
      // posicionInicio = memoria.ultima_posicion;
      // memoria.actualizar_ultima_posicion(proceso.tamaño - 1);
      // posicionFin = proceso.tamaño - 1;
    });
  }
}

// memoria = new Memoria(530);
// const sistema_operativo = new SistemaOperativo({
//   id: 1,
//   tamaño: 100k,
// })
// memoria.crear_particion(sistema_operativo);


class Particiones () {
  // Atributos
  + id: Integer
  + tamaño: Real
  + posicionInicio: Real
  + posicionFin: Real
  + fragmentacionInterna: Real
  + procesos: Object

  // Métodos
  constructor(id, tamaño, posicionInicio, posicionFin) {
    particion.id = id;
    particion.tamaño = tamaño;
    particion.posicionInicio = posicionInicio;
    particion.posicionFin = posicionFin;
    particion.fragmentacionInterna = 0;
    particion.procesos = null;
  }
  + setear_fragmentacionInterna(tamañoProceso) {
    particion.fragmentacionInterna = particion.tamaño - tamañoProceso;
  }
}

interface <Procesos> {
  // Atributos
  + id: Integer
  + tamaño: Real
  
  // Métodos
  constructor() {
    proceso.id = id;
    proceso.tamaño = tamaño;
  }
}

class PCB extends <Procesos> () {
  // Atributos
  + registroIndice: Integer
  + contadorDePrograma: Integer
  + codigoDeCondicionamiento: Integer
  + biestableDeEstado: Integer

  // Metodos
}

class Procesos extends <Procesos> () {
  // Atributos
  + estado: Enum('new', 'ready/suspend', 'ready', 'blocked', 'blocked/suspend', 'running', 'exit');
  + tiempoDeArribo: Real
  + tiempoDeIrrupción: Real
  + prioridad: Integer

  // Métodos
  constructor() {
    proceso.estado = estado;
    proceso.tiempoDeArribo = tiempoDeArribo;
    proceso.tiempoDeIrrupción = tiempoDeIrrupción;
    proceso.prioridad = prioridad;
  }
  + actualizar_estado(estado): Boolean {
    proceso.estado = estado;
  }
  + actualizar_prioridad(prioridad): Boolean {
    proceso.prioridad = prioridad;
  }
}

class SistemaOperativo extends <Procesos> () {
  // Atributos
  + procesos: List
  + procesosPequeños: Real
  + procesosMedianos: Real
  + procesosGrandes: Real

  // Métodos
  constructor(id, tamaño) {
    sistemaOperativo.id = id;
    sistemaOperativo.tamaño = tamaño;
    sistemaOperativo.procesos = [];
    sistemaOperativo.procesosPequeños = 60;
    sistemaOperativo.procesosMedianos = 120;
    sistemaOperativo.procesosGrandes = 250;
  }

  + asignar_procesos(proceso): Boolean {
    si (proceso.tamaño < memoria.tamaño_disponible) {
      memoria.cargar_proceso(proceso);
    }
  }
  + gestionar_procesos(evento): Boolean {}
  + terminar_procesos(proceso): Boolean {}
}

class Eventos () {
  // Atributos
  + id: Integer
  + nombre: String

  // Métodos
  + disparar_evento(evento): Boolean {

  }
}


Accion TPI_SISTEMA-OPERATIVO {

}