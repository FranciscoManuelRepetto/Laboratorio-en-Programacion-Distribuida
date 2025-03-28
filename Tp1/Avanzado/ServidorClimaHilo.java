import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.logging.*;

class ServidorClimaHilo extends Thread {
    private Socket clientSocket;
    private int idSesion;
    private PrintWriter out;
    private BufferedReader in;
    private static String[] climas = {
            "Está lloviendo",
            "Está nublado",
            "Está despejado",
            "ALERTA: Vientos fuertes",
            "Esta nevando",
            "Con probabilidad de granizo"
    };

    public ServidorClimaHilo(Socket socket, int id) {
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

                out.println(getClimaNeuquen(inputLine));
            }

            // Cerrar conexiones
            in.close();
            out.close();
            clientSocket.close();
            System.out.println("Cliente " + idSesion + " desconectado.");
        } catch (IOException e) {
                Logger.getLogger(ServidorClimaHilo.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static String getClimaNeuquen(String fecha) {
        String respuesta;
        if(esFechaValida(fecha)){
            Random rand = new Random();
            respuesta= climas[rand.nextInt(climas.length)];
        }else{
            respuesta = "No es una fecha valida";
        }
        return respuesta;
    }

    public static boolean esFechaValida(String fecha) {
        String[] partes = fecha.split("/");
        boolean valida;
        if (partes.length != 3)
            valida = false;
        else
            valida = (partes[0].length() == 2 && partes[1].length() == 2 && partes[2].length() == 4);
        return valida;
    }
}
