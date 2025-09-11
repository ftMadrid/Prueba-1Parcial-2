package prueba3;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;

public class EmpleadosManager {

    private RandomAccessFile rcods, remps;

    public EmpleadosManager() {
        try {
            File mf = new File("Company");
            mf.mkdir();

            rcods = new RandomAccessFile("company/codigo.emp", "rw");
            remps = new RandomAccessFile("company/empleado.emp", "rw");

            initCodes();
        } catch (IOException e) {
            System.out.println("\n| No deberia de pasar esto!");
            System.out.println("| Error: " + e.getMessage());
        }
    }

    /* 
    Formato: rcods.emp
     */
    private void initCodes() throws IOException {

        if (rcods.length() == 0) {
            rcods.writeInt(1);
        }
    }

    private int getCode() throws IOException {

        rcods.seek(0);

        int code = rcods.readInt();

        rcods.seek(0);

        rcods.writeInt(code + 1);
        return code;

    }

    /* 
    Formato: remps.emp
    int code;
    String name;
    double salario;
    long fechaContrato;
    long fechaDespedido;
     */
    public void AddEmployee(String nombre, double salario) throws IOException {

        remps.seek(remps.length());
        int code = getCode();

        remps.writeInt(code);
        remps.writeUTF(nombre);
        remps.writeDouble(salario);
        remps.writeLong(Calendar.getInstance().getTimeInMillis());
        remps.writeLong(0);

        // Crear folder del empleado
        createEmployerFolders(code);

    }

    private String employerFolder(int code) {
        return "company/empleado" + code;
    }

    private void createEmployerFolders(int code) throws IOException {
        File edir = new File(employerFolder(code));
        edir.mkdir();

        // Crear ventas del empleado
    }

    private RandomAccessFile salesFileFor(int code) throws IOException {

        String dirPadre = employerFolder(code);
        int yearActual = Calendar.getInstance().get(Calendar.YEAR);
        String path = dirPadre + "/ventas" + yearActual + ".emp";

        return new RandomAccessFile(path, "rw");

    }

    /*
    Formato : VentasYear.emp
    Double venta
    boolean pagado
     */
    private void createYearSalesFileFor(int code) throws IOException {

        RandomAccessFile rven = salesFileFor(code);
        if (rven.length() == 0) {
            for (int mes = 0; mes < 12; mes++) {
                rven.writeDouble(0);
                rven.writeBoolean(false);
            }
        }
    }

    public void employeeList() throws IOException {

        remps.seek(0);

        while (remps.getFilePointer() < remps.length()) {

            int code = remps.readInt();
            String name = remps.readUTF();
            double salario = remps.readDouble();
            Date fecha = new Date(remps.readLong());

            if (remps.readLong() == 0) {
                System.out.println("\nCodigo: " + code
                        + "\nNombre: " + name
                        + String.format("\nSalario: Lps.%.2f", salario)
                        + "\nFecha de Contratacion: " + fecha.getTime());
            }
        }
    }

    private boolean isEmployeeActive(int code) throws IOException {

        remps.seek(0);

        while (remps.getFilePointer() < remps.length()) {

            int codeN = remps.readInt();

            long pos = remps.getFilePointer();
            remps.readUTF();
            remps.skipBytes(16);

            if (remps.readLong() == 0 && codeN == code) {
                remps.seek(pos);
                return true;
            }
        }
        return false;
    }

    public boolean fireEmployee(int code) throws IOException {

        if (isEmployeeActive(code)) {
            String name = remps.readUTF();
            remps.skipBytes(16);
            remps.writeLong(code);
            System.out.println("\n| Despidiendo a: " + name);
            return true;
        }
        return false;
    }

    public void addSaleToEmployee(int code, double monto) throws IOException {

        if (!isEmployeeActive(code)) {
            System.out.println("\n| Este empleado no esta activo!");
            return;
        }

        RandomAccessFile rven = salesFileFor(code);
        createYearSalesFileFor(code);

        int mes = Calendar.getInstance().get(Calendar.MONTH);
        long pos = mes * (9);
        rven.seek(pos);

        double actual = rven.readDouble();
        rven.seek(pos);
        rven.writeDouble(actual + monto);
        System.out.println(String.format("\n| Venta agregada al empleado: Lps.%.2f", monto));
    }

    public void payEmployee(int code) throws IOException {
        if (!isEmployeeActive(code)) {
            System.out.println("\n| Este empleado no esta activo!");
            return;
        }

        remps.seek(0);
        String name = null;
        double salario = 0;
        while (remps.getFilePointer() < remps.length()) {
            int codeN = remps.readInt();
            name = remps.readUTF();
            salario = remps.readDouble();
            remps.readLong();
            long desp = remps.readLong();
            if (codeN == code && desp == 0) {
                break;
            }
        }

        RandomAccessFile ventas = salesFileFor(code);
        int mes = Calendar.getInstance().get(Calendar.MONTH);
        long pos = mes * (9);
        ventas.seek(pos);

        double montoVentas = ventas.readDouble();
        boolean pagado = ventas.readBoolean();

        if (pagado) {
            System.out.println("\n| Ya se le pago este mes al empleado!");
            return;
        }

        double comision = montoVentas * 0.10;
        double sueldoBase = salario + comision;
        double deduccion = sueldoBase * 0.035;
        double sueldoNeto = sueldoBase - deduccion;

        RandomAccessFile recibos = new RandomAccessFile(employerFolder(code) + "/recibos.emp", "rw");
        recibos.seek(recibos.length());
        recibos.writeLong(System.currentTimeMillis());
        recibos.writeDouble(comision);
        recibos.writeDouble(salario);
        recibos.writeDouble(deduccion);
        recibos.writeDouble(sueldoNeto);
        recibos.writeInt(Calendar.getInstance().get(Calendar.YEAR));
        recibos.writeInt(mes);

        ventas.seek(pos + 8);
        ventas.writeBoolean(true);

        System.out.printf(String.format("\n| Se le pago al empleado: Lps.%.2f", sueldoNeto));
    }

    public void printEmployee(int code) throws IOException {
        remps.seek(0);
        String name = null;
        double salario = 0;
        Date fechaContrato = null;
        boolean existe = false;

        while (remps.getFilePointer() < remps.length()) {
            int codeN = remps.readInt();
            name = remps.readUTF();
            salario = remps.readDouble();
            fechaContrato = new Date(remps.readLong());
            long fechaDesp = remps.readLong();

            if (codeN == code && fechaDesp == 0) {
                existe = true;
                break;
            }
        }

        if (!existe) {
            System.out.println("\n| Este empleado no esta activo!");
        }

        System.out.println("\nCodigo: " + code
                + "\nNombre: " + name
                + String.format("\nSalario: Lps. %.2f", salario)
                + "\nFecha de Contratación: " + fechaContrato);

        RandomAccessFile rven = salesFileFor(code);
        createYearSalesFileFor(code);

        double totalVentas = 0;
        System.out.println("\n=== Ventas del Año ===");
        for (int mes = 0; mes < 12; mes++) {
            long pos = mes * (9);
            rven.seek(pos);
            double monto = rven.readDouble();
            boolean pagado = rven.readBoolean();
            totalVentas += monto;
            System.out.println("\n| Mes: "+(mes+1)+"\n| Lps: "+monto+"\n| Estado: "+(pagado ? "[Pagado]" : "[NO PAGADO]"));
        }
        System.out.println(String.format("\n| Total ventas anuales: Lps.%.2f", totalVentas));

        RandomAccessFile recibos = new RandomAccessFile(employerFolder(code) + "/recibos.emp", "rw");
        int recibosCount = (int) (recibos.length() / (8 + 8 + 8 + 8 + 8 + 4 + 4));
        System.out.println("| Recibos registrados: " + recibosCount);
    }
}
