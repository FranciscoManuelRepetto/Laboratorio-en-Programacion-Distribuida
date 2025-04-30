import java.rmi.Naming;

public class ObjetoHoroscopo {
    public static void main(String[] args) {
        String ip = args[0];
        int puerto = Integer.parseInt(args[1]);

        try {
            System.setProperty("java.rmi.server.hostname", ip);
            java.rmi.registry.LocateRegistry.createRegistry(puerto);

            ObjetoHoroscopo_UI_imp servidorH = new ObjetoHoroscopo_UI_imp();
            Naming.rebind("//" + ip + ":" + puerto + "/ObjetoHoroscopo", servidorH);

            System.out.println("ObjetorHoroscopo registrado en RMI Registry en " + ip + " puerto " + puerto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
