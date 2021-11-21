package main;

import entidades.SistemaOperativo;

public class Main {
	public static void main(String[] args) {
		if(args.length<1)
				throw new RuntimeException("ERROR: debe ingresar el nombre del archivo de procesos");
		new SistemaOperativo(args[0]);
	}
}
