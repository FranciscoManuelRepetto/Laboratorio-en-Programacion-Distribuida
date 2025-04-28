import java.rmi.Naming;

public class ServidorClima {
    public static void main(String[] args) {
        String ip = args[0];
        int puerto = Integer.parseInt(args[1]);
        
        try {
            System.setProperty("java.rmi.server.hostname", ip);
            java.rmi.registry.LocateRegistry.createRegistry(puerto);

            ServidorClima_UI_imp servidorC = new ServidorClima_UI_imp();
            Naming.rebind("//" + ip + ":" + puerto + "/ServidorClima", servidorC);

            System.out.println("ServidorClima registrado en RMI Registry en " + ip + " puerto " + puerto);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
