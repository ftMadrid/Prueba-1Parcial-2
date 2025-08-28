package prueba;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFile {

    private File file = null;

    public void setFile(String dir) {

        file = new File(dir);

    }

    public void info() {

        if (file.exists()) {
            System.out.println("\n| El archivo si existe! |");
            System.out.println("\n| Nombre: " + file.getName());
            System.out.println("\n| Path: " + file.getPath());
            System.out.println("\n| Absolute: " + file.getAbsolutePath());
            System.out.println("\n| Padre: " + file.getAbsoluteFile().getParentFile());
            System.out.println("\n| Bytes: " + file.length());

            if (file.isFile()) {
                System.out.println("\n| Es un archivo!");
            } else if (file.isDirectory()) {
                System.out.println("\n| Es un directorio!");
            }

            System.out.println("\n| Ultima modificacion: " + new Date(file.lastModified()));

        } else {
            System.out.println("\n| El archivo no existe!\n");
        }

    }

    void crearArchivo() throws IOException {

        if (file.createNewFile()) {
            System.out.println("\n| El archivo ha sido creado con exito!");

        } else {
            System.out.println("\n| No se pudo crear el archivo!");
        }

    }

    void crearFolder() throws IOException {

        if (file.mkdir()) {
            System.out.println("\n| El folder ha sido creado con exito!");

        } else {
            System.out.println("\n| No se pudo crear el folder!");
        }

    }

    void borrarArchivo() throws IOException {

        if (file.delete()) {
            System.out.println("\n| El archivo se borro con exito!");
        } else {
            System.out.println("\n| No se pudo borrar el archivo!");
        }
    }

    void borrarArchivos(File file) throws IOException {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                if (f != null) {
                    borrarArchivos(f);
                }
            }
        } else {
            System.out.println("\n| Esto no es un directorio!");
        }

        if (file.delete()) {
            System.out.println("\n| Se borro: " + file.getAbsolutePath());
        } else {
            System.out.println("\n| No se borro: " + file.getAbsolutePath());
        }

    }

    public File getFile() {
        return file;
    }

    private void tree(File dir, String tab) {
        if (dir.isDirectory()) {
            System.out.println(tab + dir.getName());
            for (File f : dir.listFiles()) {
                if (!f.isHidden()) {
                    tree(f, tab + "--");
                }
            }
        }
    }

    void tree() {
        tree(file, "-");
    }

    int archivos = 0;
    int directorios = 0;
    int converbytes = 0;

    void MostrarDir(File dir) {
        
        SimpleDateFormat plantilla = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                String fecha = plantilla.format(f.lastModified());
                String tipo = f.isFile() ? "FILE" : "<DIR>";
                String tam = f.isFile() ? (f.length() / 1024) + " KB" : "-";

                System.out.println(fecha + "\t" + tipo + "\t" + tam + "\t" + f.getName());

                if (f.isFile()) {
                    archivos++;
                    converbytes += (int) f.length();
                } else {
                    directorios++;
                    MostrarDir(f);
                }
            }
        }
    }

    void MostrarDir() {
        if (file.isDirectory()) {
            System.out.println("\n| Directorio de: " + file.getAbsolutePath() + "\n");
            System.out.println("Ultima Modificacion\tTipo\tTamaño\tNombre");

            archivos = 0;
            directorios = 0;
            converbytes = 0;

            MostrarDir(file);

            System.out.println();
            System.out.println("| "+archivos + " archivos " + (converbytes / 1024) + " KB");
            System.out.println("| "+directorios + " directorios "
                    + (file.getFreeSpace() / (1024 * 1024 * 1024)) + " GB libres");
        } else {
            System.out.println("\n| La direccion no puede ser un archivo!");
        }
    }
}
