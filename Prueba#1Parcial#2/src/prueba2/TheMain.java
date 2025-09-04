package prueba2;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TheMain {

    public static void main(String[] args) throws IOException {

        TheFile refefile = new TheFile();
        Scanner entrada = new Scanner(System.in).useDelimiter("\n");

        int opcion = 0;

        do {
            System.out.println("\nGESTOR DE TAREAS");
            System.out.println("====================");
            System.out.println("1. Agregar tarea");
            System.out.println("2. Mostrar tareas");
            System.out.println("3. Completar tarea");
            System.out.println("4. Salir\n");
            System.out.print("| Seleccione una opcion: ");

            try {
                opcion = entrada.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.print("\n| Ingresa la nueva tarea: ");
                        refefile.agregarTarea(entrada.next());
                        break;
                    case 2:
                        refefile.listarTareas();
                        break;
                    case 3:
                        
                        if(!refefile.existenTareas()){
                            break;
                        }

                        System.out.print("\n| Numero de tarea a completar: ");
                        refefile.completarTarea(entrada.nextInt());
                        break;
                    case 4:
                        System.out.println("\n| Que tenga lindo dias inge!");
                        break;
                }

            } catch (InputMismatchException e) {
                entrada.nextLine();
                System.out.println("\n| Elija una opcion valida!");
            }
        } while (opcion != 4);
    }
}
