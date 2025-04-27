import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.logging.*;
import java.rmi.Naming;

public class ServidorHoroscopo {
    public static void main(String[] args) {
        
        int puerto = Integer.parseInt(args[0]);

        try {

            java.rmi.registry.LocateRegistry.createRegistry(puerto);
            
            ServidorHoroscopo_UI_imp servidorH = new ServidorHoroscopo_UI_imp();
            Naming.rebind("//localhost:" + puerto + "/ServidorHoroscopo", servidorH);

            System.out.println("ServidorHoroscopo registrado en RMI Registry en puerto " + puerto);
        }
        catch (Exception e) {
            e.printStackTrace();
         }
}
}