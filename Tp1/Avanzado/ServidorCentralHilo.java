import java.io.*;
import java.net.*;
import java.util.logging.*;

class ServidorCentralHilo extends Thread {
    private Socket clientSocket; // Socket del cliente conectado al servidor central
    private CacheServerCentral cache; // Caché para almacenar respuestas previas
    private int pClima; // Puerto del servidor de clima
    private int pHoroscopo; // Puerto del servidor de horóscopo
    private PrintWriter outServidorCentral; // Para enviar datos al cliente
    private BufferedReader inServidorCentral; // Para leer datos enviados por el cliente
    private int idSesion; //Identificador único del cliente
    

/*Creacion del hilo del servidorCentral*/
    public ServidorCentralHilo(Socket socket,int id,CacheServerCentral cache,int pClima,int pHoroscopo) {
        this.clientSocket = socket;
        this.idSesion = id;
        this.cache=cache;
        this.pClima=pClima;
        this.pHoroscopo=pHoroscopo;
    }


/*Manejando la comunicacion con el cliente*/
    @Override
public void run() {
    try {

        // Inicializar los streams de comunicación con el cliente
        outServidorCentral = new PrintWriter(clientSocket.getOutputStream(), true);
        inServidorCentral = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;

        // Bucle para recibir y procesar mensajes del cliente
        while ((inputLine = inServidorCentral.readLine()) != null) {
            System.out.println("Cliente " + idSesion + " envió: " + inputLine);

            // Procesar entrada del cliente
            String[] partes = procesarEntrada(inputLine);
            if (partes == null) {
                outServidorCentral.println("Error: Formato incorrecto. Use 'signo;fecha'");
                continue; //Saltamos a la siguiente iteracion
            }

            String signo = partes[0]; //Signo zodiacal
            String fecha = partes[1]; //Fecha indicada

            // Consultamos a la caché, o sino a los servidores de clima y horóscopo
            String respuestaClima = consultarMensaje(fecha, "localhost", pClima);
            String respuestaHoroscopo = consultarMensaje(signo, "localhost", pHoroscopo);

            //Unimos la respuesta para el cliente
            outServidorCentral.println("Clima: " + respuestaClima + " | Horóscopo: " + respuestaHoroscopo);
        }

       /*Cerramos conexiones*/
        inServidorCentral.close();
        outServidorCentral.close();
        clientSocket.close();
        System.out.println("Cliente " + idSesion + " desconectado.");

    } catch (IOException e) {
        Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, "Error en la conexión con el cliente " + idSesion, e);
    }
}



/*Método para procesar la entrada del cliente y verificar formato

Recibe como parametro el mensaje recibido del cliente (debe tener el formato 'signo;fecha')
y devuelve un array con las dos respuestas a cada consulta [signo, fecha], o null ante cualquier error
*/

private String[] procesarEntrada(String input) {
    //Procesa el mensaje y verifica formato
    String[] partes = input.split(";");
    if (partes.length != 2) {
        return null;
    }
    // Devuelve los valores limpios de espacios en blanco
    return new String[]{partes[0].trim(), partes[1].trim()};
}




/*Metodo para verifica si la consulta ya está en la caché; si no, consulta al servidor correspondiente

Recibe como parametro
mensaje:  el mensaje, que puede ser el signo o la fecha
host: La direccion del servidor, normalmente 'localhost'
puerto: puerto del servidor con el que debe conectarse en caso de que la pregunta no este en caché

Devuelve: Un string con la respuesta a la consulta
*/

private String consultarMensaje(String mensaje, String host, int puerto){
    String respuesta;
    respuesta = cache.getConsulta(mensaje); // Verifica si ya se consultó antes
    if(respuesta==null){ // Si no está en la caché, consulta al servidor correspondiente
        respuesta = consultarServidor(mensaje,host,puerto);
        cache.nuevaRespuesta(mensaje,respuesta); // Guarda la nueva respuesta en caché
    }else{
        //Esto unicamente sirve para verificar si se utiliza la cache
        System.out.println("Parte del mensaje obtenido de la cache");
    }
    return respuesta;
}




/*Metodo para con el servidor correspondiente para obtener la respuesta

Sus parametros son: 
- mensaje: el mensaje que enviareamos al servidor
- host: la direccion del servidor, es decir, 'localhost'
- puerto: el puerto correspondiente del servidor, ya sea el del clima o del horoscopo

Devuelve: Un string con la respuesta a la consulta
*/

private String consultarServidor(String mensaje, String host, int puerto) {
    String respuesta = null; 
    try (Socket socket = new Socket(host, puerto);
         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

        out.println(mensaje); // Envía el mensaje al servidor
        respuesta= in.readLine(); // Recibe la respuesta del servidor

    } catch (IOException e) {
        System.err.println("No se puede conectar con el servidor en " + host + ":" + puerto);
        respuesta= "Error en el servidor (" + host + ")";
    }
    return respuesta;
}

}