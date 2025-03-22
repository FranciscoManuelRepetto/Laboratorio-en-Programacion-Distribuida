import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.logging.*;

class ServidorClimaHilo extends Thread {
    private Socket socket;
    private int idSesion;
    private PrintWriter out;
    private BufferedReader in;

    public ServidorClimaHilo(Socket socket, int id) {
        this.socket = socket;
        this.idSesion = id;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            Logger.getLogger(ServidorClimaHilo.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Cliente " + idSesion + " conectado.");
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equalsIgnoreCase("salir")) {
                    out.println("Conexión cerrada.");
                    break;
                }
                out.println(getClimaNeuquen());
            }
        } catch (IOException e) {
            Logger.getLogger(ServidorClimaHilo.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                Logger.getLogger(ServidorClimaHilo.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public static String getClimaNeuquen() {
        Random rand = new Random();
        String[] pronosticos = {
            "Está lloviendo",
            "Está nublado",
            "Está despejado",
            "ALERTA: Vientos fuertes",
            "Easter egg",
            "Con probabilidad de granizo"
        };
        return pronosticos[rand.nextInt(pronosticos.length)];
    }
}

public class ServidorPronosticoClima {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(20001)) {
            System.out.println("Servidor en espera de conexión...");
            int idSesion = 0;
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ServidorClimaHilo(clientSocket, idSesion++).start();
            }
        } catch (IOException e) {
            System.err.println("No se puede escuchar en puerto: 20001.");
            e.printStackTrace();
        }
    }
}
