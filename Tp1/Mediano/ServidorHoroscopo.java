import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.logging.*;

public class ServidorHoroscopo {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        int idCliente = 0;

        try {
            serverSocket = new ServerSocket(20002);
            System.out.println("ServidorHoroscopo esperando conexiones en el puerto 20002...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Acepta nueva conexión
                System.out.println("Nuevo cliente conectado: " + idCliente);

                // Crea un hilo para manejar la comunicación con el cliente
                new ServidorHoroscopoHilo(clientSocket, idCliente).start();
                idCliente++;
            }
        } catch (IOException e) {
            System.err.println("No se puede escuchar en puerto: 20002.");
            System.exit(1);
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}

class ServidorHoroscopoHilo extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private int idSesion;

    public ServidorHoroscopoHilo(Socket socket, int id) {
        this.clientSocket = socket;
        this.idSesion = id;
    }

    @Override
    public void run() {
        try {
            // Inicializar streams de comunicación
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Cliente " + idSesion + " envió: " + inputLine);

                if (inputLine.equalsIgnoreCase("salir")) {
                    out.println("Conexión cerrada.");
                    break;
                }

                String respuesta = getHoroscopo();
                out.println(respuesta);
            }

            // Cerrar conexiones
            in.close();
            out.close();
            clientSocket.close();
            System.out.println("Cliente " + idSesion + " desconectado.");

        } catch (IOException e) {
            Logger.getLogger(ServidorHoroscopoHilo.class.getName()).log(Level.SEVERE, "Error en la conexión con el cliente " + idSesion, e);
        }
    }

    public static String getHoroscopo() {
        Random rand = new Random();
        String[] horoscoposCargados = {
            "Libra: Hoy encontrarás el equilibrio que buscabas.",
            "Géminis: Tu curiosidad te llevará a nuevas oportunidades.",
            "Acuario: Tu creatividad estará en su punto máximo hoy. Si tu nombre empieza con E, serás más feliz que nunca!"
        };

        return horoscoposCargados[rand.nextInt(horoscoposCargados.length)];
    }
}


