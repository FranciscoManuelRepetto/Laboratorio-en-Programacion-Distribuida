import java.io.*;
import java.rmi.Naming;

public class ServidorCentral {
    public static void main(String[] args) {
        try {
            int pCentral = Integer.parseInt(args[0]);
            int pClima = Integer.parseInt(args[1]);
            int pHoroscopo = Integer.parseInt(args[2]);

            System.setProperty("java.rmi.server.hostname", "localhost");
            java.rmi.registry.LocateRegistry.createRegistry(pCentral);

            ServCent_UI_imp servidor = new ServCent_UI_imp(pClima, pHoroscopo);
            Naming.rebind("//localhost:" + pCentral + "/ServidorCentral", servidor);

            System.out.println("ServidorCentral registrado en RMI Registry en puerto " + pCentral);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
