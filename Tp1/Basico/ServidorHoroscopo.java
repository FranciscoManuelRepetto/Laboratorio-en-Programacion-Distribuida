import java.io.*;
import java.net.*;
import java.util.Random;

public class ServidorHoroscopo {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(20002);
        } catch (IOException e) {
            System.err.println("No se puede escuchar en puerto:20000.");
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

            String respuesta = getHoroscopo();
            out.println(respuesta);
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static String getHoroscopo() {
        Random rand = new Random();
        String[] horoscoposCargados = {
            "Libra: Hoy encontrarás el equilibrio que buscabas.",
"Géminis: Tu curiosidad te llevará a nuevas oportunidades.",
	 "Acuario: Tu creatividad estará en su punto máximo hoy.Si tu nombre empieza con E serias mas feliz que nunca!",
        };

        return horoscoposCargados[rand.nextInt(horoscoposCargados.length)];
    }
}

