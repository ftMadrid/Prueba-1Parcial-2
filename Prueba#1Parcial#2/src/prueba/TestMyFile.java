package prueba;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TestMyFile {
    
    static MyFile mf = new MyFile();
    static Scanner lea = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        int opcion = 0;
        
        do{
            
            System.out.println("\nMENU\n");
            System.out.println("1. Set el archivo/folder");
            System.out.println("2. Ver informacion");
            System.out.println("3. Crear archivo");
            System.out.println("4. Crear folder");
            System.out.println("5. Eliminar archivo");
            System.out.println("6. Eliminar archivos");
            System.out.println("7. Arbol de Archivos");
            System.out.println("8. Mostrar Dir");
            System.out.println("9. Salir\n");
            System.out.print("Elija una opcion: ");
            
            try{
                opcion = lea.nextInt();
                
                switch(opcion){
                    case 1:
                        set();
                        break;
                    case 2:
                        mf.info();
                        break;
                    case 3:
                        mf.crearArchivo();
                        break;
                    case 4:
                        mf.crearFolder();
                        break;
                    case 5:
                        mf.borrarArchivo();
                        break;
                    case 6:
                        mf.borrarArchivos(mf.getFile());
                        break;
                    case 7:
                        mf.tree();
                        break;
                    case 8:
                        mf.MostrarDir();
                        break;
                    case 9:
                        System.out.println("\nQue tenga lindo dia :D!");
                        System.exit(0);
                        break;
                }
                
            }catch(InputMismatchException e){
                lea.nextLine();
                System.out.println("\nPorfavor ingrese una opcion correcta!");
            }catch(NullPointerException e){
                System.out.println("\nDebes de seleccionar la opcion 1, por lo menos una vez.");
            }catch(IOException e){
                System.out.println("Error en Disco: "+e.getMessage());
            }
            
            
        }while(opcion != 9);
        
    }
    
    private static void set(){
        System.out.print("\nDireccion: ");
        mf.setFile(lea.next());
    }
    
    
}
