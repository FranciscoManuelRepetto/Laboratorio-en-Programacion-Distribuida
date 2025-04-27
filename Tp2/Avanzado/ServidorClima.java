import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.logging.*;


public class ServidorClima {
    public static void main(String[] args) {
        ServerSocket serverSocket = null; // Servidor que escucha conexiones
        int idCliente = 0; // Contador que identifica a cada cliente que se conecta.

        //Crea el puerto con el argumento
        int puerto = Integer.parseInt(args[0]);
        
        try{
            // Crea un ServerSocket para escuchar conexiones en el puerto especificado
            serverSocket = new ServerSocket(puerto);
            System.out.println("ServidorClima esperando conexiones en el puerto "+puerto);

            // Bucle infinito para aceptar múltiples conexiones de clientes
            while (true) {
                // Espera la conexión de un cliente
                Socket clientSocket = serverSocket.accept(); // Acepta nueva conexión
                System.out.println("Nuevo cliente conectado: " + idCliente);//Se muestra por consola que se conecto un cliente

                // Crea un nuevo hilo para manejar la comunicación con este cliente
                new ServidorClimaHilo(clientSocket, idCliente).start();

                // Incrementa el contador de clientes
                idCliente++;
            }
        } catch (IOException e) {
            System.err.println("No se puede escuchar en puerto: "+puerto);
            System.exit(1);// Finaliza el programa con error
        }
    }

    
}
