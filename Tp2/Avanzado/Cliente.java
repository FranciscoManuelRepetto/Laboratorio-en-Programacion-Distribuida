import java.io.*;
import java.net.*;
import java.rmi.Naming;

public class Cliente {
    public static void main(String[] args) {
        try {
            //Guarda la ip y el puerto de forma dinamica
            String ipServidor = args[0];
            int puerto = Integer.parseInt(args[1]);

            ObjetoCentral_UI servidor = (ObjetoCentral_UI) Naming.lookup("//" + ipServidor + ":" + puerto + "/ObjetoCentral");

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            System.out.println("Ingrese a continuación mensajes con formato signo;fecha. Ingrese 'salir' para salir.");

            while ((userInput = stdIn.readLine()) != null) {
                if (userInput.equalsIgnoreCase("salir")) {
                    System.out.println("Cerrando conexión...");
                    break;
                }
                String respuesta = servidor.procesarConsulta(userInput);
                System.out.println(respuesta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
