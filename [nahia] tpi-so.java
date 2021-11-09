import java.util.*;

class Memoria {
  // Atributos
  float tamMemoriaSO;
  float tamMemoriaTrabGran;
  float tamMemoriaTrabMed;
  float tamMemoriaTrabPeq;

  float tamanoDispTG;
  float tamanoDispTM;
  float tamanoDispTP;

  public float gettamMemoriaSO() {
    return this.tamMemoriaSO;
  }

  public float gettamMemoriaTrabGran() {
    return this.tamMemoriaTrabGran;
  }

  public float gettamMemoriaTrabMed() {
    return this.tamMemoriaTrabMed;
  }

  public float gettamMemoriaTrabPeq() {
    return this.tamMemoriaTrabPeq;
  }

  // Métodos
  public class Procesos {
    public void main(String[] args) {
      Memoria memTG = new Memoria(n);
      Memoria memTM = new Memoria(n);
      Memoria memTP = new Memoria(n);
      Memoria memSO = new Memoria(100);

      Scanner scan = new Scanner(System.in);

      ArrayList idproceso = new ArrayList();
      ArrayList TA = new ArrayList();
      ArrayList TI = new ArrayList();
      ArrayList tamano = new ArrayList();

      int n;
      int idproc;
      int tiempoArribo;
      int tiempoInterrupcion;
      int tamanoProceso;

      int posicionInicio;
      int posicionFin;

      int fragInternaTG;
      int fragInternaTM;
      int fragInternaTP;

      do {
        System.out.println("Ingresar procesos, pueden ser hasta 10. 0 para terminar: ");
        n = scan.nextInt();
        System.out.println("Ingrese id del proceso, no deben ser iguales");
        idproc = scan.nextInt();
        idproceso.add(idproc);

        System.out.println("Ingrese TA del proceso: ");
        tiempoArribo = scan.nextInt();
        TA.add(tiempoArribo);

        System.out.println("Ingrese TI del proceso: ");
        tiempoInterrupcion = scan.nextInt();
        TI.add(tiempoInterrupcion);

        System.out.println("Ingrese tamaño del proceso: ");
        tamanoProceso = scan.nextInt();
        tamano.add(tamanoProceso);

      } while (n != 0 ^ n <= 10);

      for (int i = 0; i < idproceso.size(); i++) {
        if (tamano.get(i) < memTP.gettamMemoriaTrabPeq(60)) {// 35 < 60
          fragInternaTP = memTP.tamMemoriaTrabPeque - tamano.get(i);// fragmentacion interna 25

        } else if (tamano.get(i) < memTM.gettamMemoriaTrabMed(120)) {
          fragInternaTM = memTM.tamMemoriaTrabMediano - tamano.get(i);

        } else if (tamano.get(i) < memTG.gettamMemoriaTrabGran(250)) {
          fragInternaTG = memTG.tamMemoriaTrabGrande - tamano.get(i);
        } else {
          System.out.println("error, tamaño del proceso supero el limite de la memoria");
        }
      }

    }
  }
}

public class PCB {
  public void main(String[] args) {
    // Atributos
    ArrayList<Integer> registroIndice = new ArrayList<Integer>();
    ArrayList<Integer> contadorDePrograma = new ArrayList<Integer>();

    registroIndice.add(10);
    registroIndice.add(12);
    registroIndice.add(13);
    registroIndice.add(15);
    registroIndice.add(16);
    registroIndice.add(18);
    registroIndice.add(25);
    registroIndice.add(26);
    registroIndice.add(28);
    registroIndice.add(30);

    contadorDePrograma.add(1);
    contadorDePrograma.add(2);
    contadorDePrograma.add(3);
    contadorDePrograma.add(4);
    contadorDePrograma.add(5);
    contadorDePrograma.add(6);
    contadorDePrograma.add(7);
    contadorDePrograma.add(8);
    contadorDePrograma.add(9);
    contadorDePrograma.add(10);

  }
}