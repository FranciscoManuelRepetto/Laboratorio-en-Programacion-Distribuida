import java.io.*;
import java.net.*;
import java.util.logging.*;

class ServidorCentralHilo extends Thread {
    private Socket clientSocket;
    private CacheServerCentral cache;
    private int pClima;
    private int pHoroscopo;
    private PrintWriter outServidorCentral;
    private BufferedReader inServidorCentral;
    private int idSesion;
    

    public ServidorCentralHilo(Socket socket,int id,CacheServerCentral cache,int pClima,int pHoroscopo) {
        this.clientSocket = socket;
        this.idSesion = id;
        this.cache=cache;
        this.pClima=pClima;
        this.pHoroscopo=pHoroscopo;
    }

    @Override
public void run() {
    try {
        // Inicializar los streams de comunicación
        outServidorCentral = new PrintWriter(clientSocket.getOutputStream(), true);
        inServidorCentral = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;

        while ((inputLine = inServidorCentral.readLine()) != null) {
            System.out.println("Cliente " + idSesion + " envió: " + inputLine);

            // Procesar entrada
            String[] partes = procesarEntrada(inputLine);
            if (partes == null) {
                outServidorCentral.println("Error: Formato incorrecto. Use 'signo-fecha'");
                continue;
            }

            String signo = partes[0];
            String fecha = partes[1];

            // Consultar servidores
            String respuestaClima = consultarMensaje(fecha, "localhost", pClima);
            String respuestaHoroscopo = consultarMensaje(signo, "localhost", pHoroscopo);

            // Enviar respuesta combinada al cliente
            outServidorCentral.println("Clima " + respuestaClima + " | Horóscopo " + respuestaHoroscopo);

            if (inputLine.equalsIgnoreCase("salir"))
                break;
        }

        // Cerrar conexiones
        inServidorCentral.close();
        outServidorCentral.close();
        clientSocket.close();
        System.out.println("Cliente " + idSesion + " desconectado.");

    } catch (IOException e) {
        Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, "Error en la conexión con el cliente " + idSesion, e);
    }
}

private String[] procesarEntrada(String input) {
    //Procesa el mensaje y verifica formato
    String[] partes = input.split("-");
    if (partes.length != 2) {
        return null;
    }
    return new String[]{partes[0].trim(), partes[1].trim()};
}

private String consultarMensaje(String mensaje, String host, int puerto){
    //Verifica si el dato esta en la cache y sino le pregunta al servidor
    String respuesta;
    respuesta = cache.getConsulta(mensaje);
    if(respuesta==null){
        respuesta = consultarServidor(mensaje,host,puerto);
        cache.nuevaRespuesta(mensaje,respuesta);
    }else{
        //Esto unicamente sirve para verificar si se utiliza la cache
        System.out.println("Parte del mensaje obtenido de la cache");
    }
    return respuesta;
}


private String consultarServidor(String mensaje, String host, int puerto) {
    //Le manda al servidor el mensaje
    try (Socket socket = new Socket(host, puerto);
         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

        out.println(mensaje);
        return in.readLine();
    } catch (IOException e) {
        System.err.println("No se puede conectar con el servidor en " + host + ":" + puerto);
        return "Error en el servidor (" + host + ")";
    }
}

}