import java.io.*;
import java.net.*;
import java.rmi.Naming;

public class Cliente {
    public static void main(String[] args) {
        try {
            int puerto = Integer.parseInt(args[0]);
            ServCent_UI servidor = (ServCent_UI) Naming.lookup("//localhost:" + puerto + "/ServidorCentral");

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            System.out.println("Ingrese a continuacion mensajes con formato signo;fecha. Ingrese 'salir' para salir");

            while ((userInput = stdIn.readLine()) != null) {
                if (userInput.equalsIgnoreCase("salir")) {
                    System.out.println("Cerrando conexión...");
                    break;
                }
                String respuesta = servidor.procesarConsulta(userInput);
                System.out.println("Respuesta del servidor: " + respuesta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

