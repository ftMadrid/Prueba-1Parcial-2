package prueba2;

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

        FileReader fr = new FileReader(file);
        String contenido = "";
        int caracter;

        while ((caracter = fr.read()) != -1) {
            contenido += (char) caracter;
        }
        fr.close();

        String[] lineas = contenido.split("\n");
        int num = 1;

        System.out.println("\nLISTA DE TAREAS");
        System.out.println("====================");

        for (String linea : lineas) {
            if (!linea.trim().equals("")) {
                System.out.println(num + ". " + linea);
                num++;
            }
        }
    }

    void completarTarea(int numero) throws IOException {
        FileReader fr = new FileReader(file);
        String contenido = "";
        int caracter;

        while ((caracter = fr.read()) != -1) {
            contenido += (char) caracter;
        }
        fr.close();

        String[] lineas = contenido.split("\n");
        boolean tareaEncontrada = false;
        boolean tareaYaCompletada = false;

        for (int i = 0; i < lineas.length; i++) {
            if (i + 1 == numero && !lineas[i].trim().equals("")) {
                tareaEncontrada = true;
                if (lineas[i].startsWith("[ ]")) {
                    lineas[i] = lineas[i].replace("[ ]", "[✓]");
                    System.out.println("\n✓ Tarea #" + numero + " completada: " + lineas[i].substring(4));
                } else {
                    tareaYaCompletada = true;
                    System.out.println("\n| Esa tarea ya esta marcada como completada!");
                }
                break;
            }
        }

        if (!tareaEncontrada) {
            System.out.println("\n| La tarea numero #" + numero + " no existe!");
            return;
        }

        if (!tareaYaCompletada) {
            FileWriter fw = new FileWriter(file);
            for (int i = 0; i < lineas.length; i++) {
                fw.write(lineas[i]);
                if (i < lineas.length - 1) {
                    fw.write("\n");
                }
            }
            fw.close();
        }
    }

    // esto solo es para evitar que escriba en completar tareas si no hay tareas para completar :D!
    boolean existenTareas() {
        if (!file.exists() || file.length() == 0) {
            System.out.println("\n| No hay tareas para completar!");
            return false;
        } else {
            return true;
        }
    }
}
