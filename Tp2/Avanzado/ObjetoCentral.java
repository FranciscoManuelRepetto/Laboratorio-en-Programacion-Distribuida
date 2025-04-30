import java.io.*;
import java.rmi.Naming;

public class ObjetoCentral {
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

            // Crear instancia del objeto pasando IP y puerto de Clima y Horóscopo
            ObjetoCentral_UI_imp servidor = new ObjetoCentral_UI_imp(ipClima, pClima, ipHoroscopo, pHoroscopo);

            // Registrar en el RMI Registry
            Naming.rebind("//" + ipCentral + ":" + pCentral + "/ObjetoCentral", servidor);

            System.out.println("ObjetoCentral registrado en RMI Registry en " + ipCentral + ":" + pCentral);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
