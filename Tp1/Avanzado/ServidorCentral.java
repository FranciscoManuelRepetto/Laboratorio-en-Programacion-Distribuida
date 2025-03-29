import java.io.*;
import java.net.*;
import java.util.logging.*;

public class ServidorCentral {
    static private  CacheServerCentral cache;


    public static void main(String[] args) throws IOException {

        //Inicializa variables que voy a utlizar
        cache = new CacheServerCentral();
        ServerSocket serverSocket = null;
        int idCliente = 0;

        //Guarda los puertos
        int pCentral,pClima,pHoroscopo;
        pCentral = Integer.parseInt(args[0]);
        pClima = Integer.parseInt(args[1]);
        pHoroscopo = Integer.parseInt(args[2]);

        try {
            serverSocket = new ServerSocket(pCentral);
            System.out.println("ServidorCentral esperando conexiones en el puerto "+pCentral);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Acepta nueva conexión
                System.out.println("Nuevo cliente conectado: " + idCliente);
                
                // Crea un hilo para manejar la comunicación con el cliente
                new ServidorCentralHilo(clientSocket, idCliente,cache,pClima,pHoroscopo).start();
                idCliente++;
            }
        } catch (IOException e) {
            System.err.println("No se puede escuchar en puerto: "+pCentral);
            System.exit(1);
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}


