import java.io.*;
import java.net.*;
import java.util.Random;

public class ServidorPronosticoClima {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(20001);
        } catch (IOException e) {
            System.err.println("No se puede escuchar en puerto: 20001.");
            System.exit(1);
        }
        System.out.println("Servidor en espera de conexión...");

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado.");
        } catch (IOException e) {
            System.err.println("Falla conexión");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.equalsIgnoreCase("salir")) {
                out.println("Conexión cerrada.");
                break;
            }

            String respuesta = getClimaNeuquen();
            out.println(respuesta);
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static String getClimaNeuquen() {
        Random rand = new Random();
        String[] pronosticos = {
            "Está lloviendo",
            "Está nublado",
            "Está despejado",
            "ALERTA: Vientos fuertes",
            "Easter egg",
            "Con probabilidad de granizo",
        };

        return pronosticos[rand.nextInt(pronosticos.length)];
    }
}
