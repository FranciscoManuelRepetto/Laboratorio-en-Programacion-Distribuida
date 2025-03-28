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




