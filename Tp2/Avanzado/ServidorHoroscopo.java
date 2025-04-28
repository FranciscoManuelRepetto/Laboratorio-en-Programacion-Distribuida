import java.rmi.Naming;

public class ServidorHoroscopo {
    public static void main(String[] args) {
        String ip = args[0];
        int puerto = Integer.parseInt(args[1]);

        try {
            System.setProperty("java.rmi.server.hostname", ip);
            java.rmi.registry.LocateRegistry.createRegistry(puerto);

            ServidorHoroscopo_UI_imp servidorH = new ServidorHoroscopo_UI_imp();
            Naming.rebind("//" + ip + ":" + puerto + "/ServidorHoroscopo", servidorH);

            System.out.println("ServidorHoroscopo registrado en RMI Registry en " + ip + " puerto " + puerto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
