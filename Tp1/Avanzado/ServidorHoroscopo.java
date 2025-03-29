import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.logging.*;

public class ServidorHoroscopo {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        int idCliente = 0;
        int puerto = Integer.parseInt(args[0]);

        try {
            //Escucha en un socket
            serverSocket = new ServerSocket(puerto);
            System.out.println("ServidorHoroscopo esperando conexiones en el puerto "+puerto);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Acepta nueva conexión
                System.out.println("Nuevo cliente conectado: " + idCliente);

                // Crea un hilo para manejar la comunicación con el cliente
                new ServidorHoroscopoHilo(clientSocket, idCliente).start();
                idCliente++;
            }
        } catch (IOException e) {
            System.err.println("No se puede escuchar en puerto: "+puerto);
            System.exit(1);
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}




