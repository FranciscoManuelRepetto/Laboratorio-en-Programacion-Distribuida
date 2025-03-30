import java.io.*;
import java.net.*;
import java.util.logging.*;

public class ServidorCentral {
    static private  CacheServerCentral cache; // Caché utilizada para verificar si una consulta ya ha sido respondida antes.


    public static void main(String[] args) throws IOException {

    /*Variables*/
        cache = new CacheServerCentral();  // Se inicializa la caché del servidor central.
        ServerSocket serverSocket = null;  // Socket del servidor central, encargado de aceptar conexiones de clientes
        int idCliente = 0; // Contador que identifica a cada cliente que se conecta.


    /*Guarda los puertos de los servidores asociados*/
        int pCentral,pClima,pHoroscopo;
        pCentral = Integer.parseInt(args[0]); //Para el servidor central, se designo el primer argumento
        pClima = Integer.parseInt(args[1]);   //Para el servidor del clima, se designo el segundo argumento
        pHoroscopo = Integer.parseInt(args[2]); //Para el servidor del horoscopo, se designo el tercer argumento.


/*Conectandonos*/
        try {
            serverSocket = new ServerSocket(pCentral); //Se crea el socket en el puerto indicado
            System.out.println("ServidorCentral esperando conexiones en el puerto "+pCentral);

            //Bucle infinito para aceptar conexiones de clientes
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Acepta nueva conexión
                System.out.println("Nuevo cliente conectado: " + idCliente);
                
               // Crea un nuevo hilo para manejar la comunicación con el cliente
                new ServidorCentralHilo(clientSocket, idCliente,cache,pClima,pHoroscopo).start();
                idCliente++; // Incrementa el contador de clientes conectados
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


