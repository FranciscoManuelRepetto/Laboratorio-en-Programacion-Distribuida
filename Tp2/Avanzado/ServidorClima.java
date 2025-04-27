import java.io.*;
import java.net.*;
import java.rmi.Naming;


public class ServidorClima {
    public static void main(String[] args) {

        //Crea el puerto con el argumento
        int puerto = Integer.parseInt(args[0]);
        
        try{
            System.setProperty("java.rmi.server.hostname", "localhost");
            java.rmi.registry.LocateRegistry.createRegistry(puerto);
            
            ServidorClima_UI_imp servidorC = new ServidorClima_UI_imp();
            Naming.rebind("//localhost:" + puerto + "/ServidorClima", servidorC);

            System.out.println("ServidorClima registrado en RMI Registry en puerto " + puerto);

            }
            catch (Exception e) {
                e.printStackTrace();
             }
    }
}
