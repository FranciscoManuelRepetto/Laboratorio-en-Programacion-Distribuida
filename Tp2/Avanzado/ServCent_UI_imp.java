import java.rmi.server.UnicastRemoteObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;

public class ServCent_UI_imp extends UnicastRemoteObject implements ServCent_UI {
    private CacheServerCentral cache;
    private int pClima, pHoroscopo;

    public ServCent_UI_imp(int pClima, int pHoroscopo) throws RemoteException {
        super();
        this.cache = new CacheServerCentral();
        this.pClima = pClima;
        this.pHoroscopo = pHoroscopo;
    }

    public String procesarConsulta(String mensaje) throws RemoteException {
        // Procesar entrada del cliente
        String[] partes = procesarEntrada(mensaje);
        String respuesta;
        if (partes == null) {
            respuesta="Error: Formato incorrecto. Use 'signo;fecha'";
        }

        String signo = partes[0]; //Signo zodiacal
        String fecha = partes[1]; //Fecha indicada

        // Consultamos a la caché, o sino a los servidores de clima y horóscopo
        String respuestaClima = consultarMensaje(fecha, "localhost", pClima);
        String respuestaHoroscopo = consultarMensaje(signo, "localhost", pHoroscopo);

        //Unimos la respuesta para el cliente
        respuesta="Clima: " + respuestaClima + " | Horóscopo: " + respuestaHoroscopo;

        return respuesta;
    }

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
