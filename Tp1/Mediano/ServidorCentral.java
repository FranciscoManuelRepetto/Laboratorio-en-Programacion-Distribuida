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
                new ServidorHilo(clientSocket, idCliente).start();
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

class ServidorHilo extends Thread {
    private Socket clientSocket;
    private PrintWriter outServidorCentral;
    private BufferedReader inServidorCentral;
    private int idSesion;

    public ServidorHilo(Socket socket, int id) {
        this.clientSocket = socket;
        this.idSesion = id;
    }

    @Override
    public void run() {
        try {
            // Inicializar los streams de comunicación
            outServidorCentral = new PrintWriter(clientSocket.getOutputStream(), true);
            inServidorCentral = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Conexión a servidores externos
            Socket socketClima = null, socketHoroscopo = null;
            PrintWriter outClima = null, outHoroscopo = null;
            BufferedReader inClima = null, inHoroscopo = null;

            String inputLine, outputLine;

            while ((inputLine = inServidorCentral.readLine()) != null) {
                outputLine = inputLine;

                if (inputLine.equalsIgnoreCase("c")) { // Cliente quiere conectarse con el servidor del clima
                    try {
                        if (socketClima == null) { 
                            socketClima = new Socket("localhost", 20001);
                            outClima = new PrintWriter(socketClima.getOutputStream(), true);
                            inClima = new BufferedReader(new InputStreamReader(socketClima.getInputStream()));
                        }
                        outClima.println(outputLine); 
                        outputLine = inClima.readLine(); 
                    } catch (IOException e) {
                        System.err.println("No se puede conectar con el servidor del clima.");
                    }
                } 
                
                else if (inputLine.equalsIgnoreCase("h")) { // Cliente quiere conectarse con el servidor del horóscopo
                    try {
                        if (socketHoroscopo == null) { 
                            socketHoroscopo = new Socket("localhost", 20002);
                            outHoroscopo = new PrintWriter(socketHoroscopo.getOutputStream(), true);
                            inHoroscopo = new BufferedReader(new InputStreamReader(socketHoroscopo.getInputStream()));
                        }
                        outHoroscopo.println(outputLine); 
                        outputLine = inHoroscopo.readLine(); 
                    } catch (IOException e) {
                        System.err.println("No se puede conectar con el servidor del horóscopo.");
                    }
                }

                System.out.println("Cliente " + idSesion + " envió: " + inputLine);
                outServidorCentral.println("Devuelvo al cliente: " + outputLine);

                if (outputLine.equals("salir")) // Cierra la conexión si el mensaje recibido es "salir"
                    break;
            }

            // Cerrar conexiones
            if (outClima != null) outClima.close();
            if (inClima != null) inClima.close();
            if (socketClima != null) socketClima.close();

            if (outHoroscopo != null) outHoroscopo.close();
            if (inHoroscopo != null) inHoroscopo.close();
            if (socketHoroscopo != null) socketHoroscopo.close();

            inServidorCentral.close();
            outServidorCentral.close();
            clientSocket.close();
            System.out.println("Cliente " + idSesion + " desconectado.");

        } catch (IOException e) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, "Error en la conexión con el cliente " + idSesion, e);
        }
    }
}
