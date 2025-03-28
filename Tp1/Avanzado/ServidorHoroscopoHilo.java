import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.logging.*;
import java.util.List;
import java.util.Arrays;


class ServidorHoroscopoHilo extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private int idSesion;
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
    private static String[] horoscopos = {
        "Hoy es un gran día para tomar la iniciativa en nuevos proyectos.",
        "La paciencia será clave para alcanzar tus objetivos.",
        "Conversaciones inesperadas traerán nuevas oportunidades.",
        "Confía en tu intuición para tomar decisiones importantes.",
        "Es un buen día para brillar y mostrar tu liderazgo."
    };
        

    public ServidorHoroscopoHilo(Socket socket, int id) {
        this.clientSocket = socket;
        this.idSesion = id;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            Logger.getLogger(ServidorClimaHilo.class.getName()).log(Level.SEVERE, null, e);
        }   
    }

    @Override
    public void run() {
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Cliente " + idSesion + " envió: " + inputLine);
                if (inputLine.equalsIgnoreCase("salir")) {
                    out.println("Conexión cerrada.");
                    break;
                }

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


    public static String getHoroscopo(String signo) {
        String respuesta;
        if(signos.contains(signo)){
            Random rand = new Random();
            respuesta = horoscopos[rand.nextInt(horoscopos.length)];
        }else{
            respuesta ="No es un signo valido";
        }
        return respuesta;
    }
}