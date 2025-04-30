import java.rmi.Naming;

public class ObjetoClima {
    public static void main(String[] args) {
        String ip = args[0];
        int puerto = Integer.parseInt(args[1]);
        
        try {
            System.setProperty("java.rmi.server.hostname", ip);
            java.rmi.registry.LocateRegistry.createRegistry(puerto);

            ObjetoClima_UI_imp objetoC = new ObjetoClima_UI_imp();
            Naming.rebind("//" + ip + ":" + puerto + "/ObjetoClima", objetoC);

            System.out.println("ObjetoClima registrado en RMI Registry en " + ip + " puerto " + puerto);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
