import java.io.*;
import java.net.*;
import java.util.logging.*;

public class ServidorCentral {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        int idCliente = 0;

        try {
            serverSocket = new ServerSocket(20000);
            System.out.println("ServidorCentral esperando conexiones en el puerto 20000...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Acepta nueva conexión
                System.out.println("Nuevo cliente conectado: " + idCliente);
                
                // Crea un hilo para manejar la comunicación con el cliente
                new ServidorCentralHilo(clientSocket, idCliente).start();
                idCliente++;
            }
        } catch (IOException e) {
            System.err.println("No se puede escuchar en puerto: 20000.");
            System.exit(1);
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}


