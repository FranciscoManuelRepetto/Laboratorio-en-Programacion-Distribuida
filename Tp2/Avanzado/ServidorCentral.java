import java.io.*;
import java.rmi.Naming;

public class ServidorCentral {
    public static void main(String[] args) {
        try {
            String ipCentral = args[0];
            int pCentral = Integer.parseInt(args[1]);
            String ipClima = args[2];
            int pClima = Integer.parseInt(args[3]);
            String ipHoroscopo = args[4];
            int pHoroscopo = Integer.parseInt(args[5]);

            // Setea la IP para RMI
            System.setProperty("java.rmi.server.hostname", ipCentral);
            java.rmi.registry.LocateRegistry.createRegistry(pCentral);

            // Crear instancia del servidor pasando IP y puerto de Clima y Horóscopo
            ServCent_UI_imp servidor = new ServCent_UI_imp(ipClima, pClima, ipHoroscopo, pHoroscopo);

            // Registrar en el RMI Registry
            Naming.rebind("//" + ipCentral + ":" + pCentral + "/ServidorCentral", servidor);

            System.out.println("ServidorCentral registrado en RMI Registry en " + ipCentral + ":" + pCentral);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
