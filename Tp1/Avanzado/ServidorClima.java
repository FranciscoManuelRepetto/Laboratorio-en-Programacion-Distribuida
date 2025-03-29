import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.logging.*;


public class ServidorClima {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int idCliente = 0;

        //Crea el puerto con el argumento
        int puerto = Integer.parseInt(args[0]);
        
        try{
            //Escucha en un socket
            serverSocket = new ServerSocket(puerto);
            System.out.println("ServidorClima esperando conexiones en el puerto "+puerto);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Acepta nueva conexión
                System.out.println("Nuevo cliente conectado: " + idCliente);//Muestra que se conecto un cliente
                // Crea un hilo para manejar la comunicación con el cliente
                new ServidorClimaHilo(clientSocket, idCliente).start();
                idCliente++;
            }
        } catch (IOException e) {
            System.err.println("No se puede escuchar en puerto: "+puerto);
            System.exit(1);
        }
    }

    
}
