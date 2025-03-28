import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.logging.*;


public class ServidorClima {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int idCliente = 0;
        
        try{
            serverSocket = new ServerSocket(20001);
            System.out.println("ServidorClima esperando conexiones en el puerto 20001...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Acepta nueva conexión
                System.out.println("Nuevo cliente conectado: " + idCliente);//Muestra que se conecto un cliente
                // Crea un hilo para manejar la comunicación con el cliente
                new ServidorClimaHilo(clientSocket, idCliente).start();
                idCliente++;
            }
        } catch (IOException e) {
            System.err.println("No se puede escuchar en puerto: 20001.");
            System.exit(1);
        }
    }
}
