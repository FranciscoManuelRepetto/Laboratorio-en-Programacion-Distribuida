import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.logging.*;
import java.util.List;
import java.util.Arrays;


class ServidorHoroscopoHilo extends Thread {
    private Socket clientSocket; // Socket del cliente conectado al servidor horoscopo
    private int idSesion; // Identificador unico del cliente
    private PrintWriter out; // Para enviar datos al cliente
    private BufferedReader in; // Para leer datos enviados por el cliente


/*Lista de signos del zodiaco válidos*/
    private static List<String> signos= Arrays.asList(           
            "acuario",
            "piscis",
            "aries",
            "tauro",
            "géminis",
            "cáncer",
            "leo",
            "virgo",
            "libra",
            "escorpio",
            "sagitario",
            "capricornio"
    );

/*Lista de posibles mensajes del horoscopo*/
    private static String[] horoscopos = {
        "Hoy es un gran día para tomar la iniciativa en nuevos proyectos.",
        "La paciencia será clave para alcanzar tus objetivos.",
        "Conversaciones inesperadas traerán nuevas oportunidades.",
        "Confía en tu intuición para tomar decisiones importantes.",
        "Es un buen día para brillar y mostrar tu liderazgo."
    };
        

//Consturctor que inicializa el socket y los flujos de comunicación       
    public ServidorHoroscopoHilo(Socket socket, int id) {
        this.clientSocket = socket;
        this.idSesion = id;

        try {
            // Inicializar los streams de comunicación con el cliente 
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } 
        catch (IOException e) {
            Logger.getLogger(ServidorClimaHilo.class.getName()).log(Level.SEVERE, null, e);
        }   
    }



    @Override
    public void run() {
        try {
            String inputLine;

            // Bucle para recibir y procesar mensajes del cliente
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Cliente " + idSesion + " envió: " + inputLine);

                // Enviar la respuesta del horoscopo segun el signo indicado
                out.println(getHoroscopo(inputLine));
            }

            // Cerrar conexiones
            in.close();
            out.close();
            clientSocket.close();
            System.out.println("Cliente " + idSesion + " desconectado.");

        } catch (IOException e) {
            Logger.getLogger(ServidorHoroscopoHilo.class.getName()).log(Level.SEVERE, "Error en la conexión con el cliente " + idSesion, e);
        }
    }




/*Método que genera un horoscopo aleatorio si el signo proporcionado es válida.

Recibe como parametro un signo con el formato correcto (en minusculas y con tildes)
Devuelve un String con la prediccion astrologica (que se elige de manera aleatoria)
*/

    public static String getHoroscopo(String signo) {
        String respuesta;
        if(signos.contains(signo)){
            Random rand = new Random();
            respuesta = horoscopos[rand.nextInt(horoscopos.length)];
        }else{
            respuesta ="No es un signo valido"; //Mensaje de error si el signo no tiene el formato correcto
        }
        return respuesta;
    }
}