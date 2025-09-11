package prueba3;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmpleadoMain {
    
    public static void main(String[] args) throws IOException{
        
        Scanner entrada = new Scanner(System.in).useDelimiter("\n");
        EmpleadosManager manager = new EmpleadosManager();
        
        int opcion = 0;
        
        do{
            System.out.println("\n=== COMPANY ===\n");
            System.out.println("1. Agregar Empleado");
            System.out.println("2. Listar Empleados No Despedidos");
            System.out.println("3. Agregar Venta a Empleado");
            System.out.println("4. Pagar empleado");
            System.out.println("5. Despedir Empleado");
            System.out.println("6. Imprimir Empleado");
            System.out.println("7. Salir\n");
            System.out.print("Ingrese una opcion: ");
            
            try{
                
                opcion = entrada.nextInt();
                
                switch(opcion){
                    case 1:
                        System.out.println("\n=== AGREGAR A UN EMPLEADO ===");
                        System.out.print("\n| Ingrese el nombre del empleado: ");
                        String nombre = entrada.next();
                        System.out.print("\n| Ingrese el salario: ");
                        double salario = entrada.nextDouble();
                        manager.AddEmployee(nombre, salario);
                        System.out.println("\n| El empleado '"+nombre+"' se ha agregado con exito!");
                        break;
                    case 2:
                        System.out.println("\n=== Lista de Empleados No Despedidos ===\n");
                        manager.employeeList();
                        break;
                    case 3:
                        System.out.println("\n=== AGREGAR VENTA A UN EMPLEADO ===");
                        System.out.print("\n| Ingrese el codigo del empleado: ");
                        int code = entrada.nextInt();
                        System.out.print("\n| Ingresar el monto de la venta: ");
                        double monto = entrada.nextDouble();
                        manager.addSaleToEmployee(code, monto);
                        break;
                    case 4:
                        System.out.println("\n=== PAGAR EMPLEADO ===");
                        System.out.print("\n| Ingrese el codigo del empleado: ");
                        int code2 = entrada.nextInt();
                        manager.payEmployee(code2);
                        break;
                    case 5:
                        System.out.println("\n=== DESPEDIR EMPLEADO ===");
                        System.out.print("\n| Ingrese el codigo del empleado: ");
                        int code3 = entrada.nextInt();
                        manager.fireEmployee(code3);
                        break;
                    case 6:
                        System.out.println("`\n=== IMPRESION DE EMPLEADO ===");
                        System.out.print("\n| Ingrese el codigo del empleado: ");
                        int code4 = entrada.nextInt();
                        manager.printEmployee(code4);
                        break;
                    case 7:
                        System.out.println("\n| Que tenga lindo dia!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("\n| Esta opcion no existe!");
                        break;
                }
                
            }catch(InputMismatchException e) {
                entrada.nextLine();
                System.out.println("\n| Ingrese una opcion valida!");
            }
            
        }while(opcion != 7);
        
    }
    
    
}
