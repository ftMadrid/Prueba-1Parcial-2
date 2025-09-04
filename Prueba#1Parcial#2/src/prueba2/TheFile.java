package prueba2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TheFile {

    private File file = null;

    public TheFile() {
        file = new File("tareas.txt");
    }

    void agregarTarea(String tarea) throws IOException {
        FileWriter fr = new FileWriter(file, true);
        fr.write("[ ] " + tarea + "\n");
        fr.close();
        System.out.println("\n✓ Tarea agregada: " + tarea);
    }

    void listarTareas() throws IOException {
        
        if (!file.exists() || file.length() == 0) {
            System.out.println("\n| No hay tareas para mostrar!");
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        String linea;
        int num = 1;

        System.out.println("\nLISTA DE TAREAS");
        System.out.println("====================");

        while ((linea = br.readLine()) != null) {
            System.out.println(num + ". " + linea);
            num++;
        }
        br.close();
    }

    void completarTarea(int numero) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(file));
        String linea;
        String contenido = "";
        int numeroLinea = 1;
        boolean tareaEncontrada = false;
        boolean tareaYaCompletada = false;

        while ((linea = br.readLine()) != null) {
            if (numeroLinea == numero) {
                tareaEncontrada = true;
                if (linea.startsWith("[ ]")) {
                    linea = linea.replace("[ ]", "[✓]");
                    System.out.println("\n✓ Tarea #" + numero + " completada: " + linea.substring(4));
                } else {
                    tareaYaCompletada = true;
                    System.out.println("\n| Esa tarea ya esta marcada como completada!");
                }
            }
            contenido += linea + "\n";
            numeroLinea++;
        }
        br.close();

        if (!tareaEncontrada) {
            System.out.println("\n| La tarea numero #" + numero + " no existe!");
            return;
        }

        if (!tareaYaCompletada) {
            FileWriter fw = new FileWriter(file);
            fw.write(contenido);
            fw.close();
        }
    }
    
    // esto solo es para evitar que escriba en completar tareas si no hay tareas para completar :D!
    boolean existenTareas(){
        if (!file.exists() || file.length() == 0) {
            System.out.println("\n| No hay tareas para completar!");
            return false;
        }else{
            return true;
        }
    }
}
