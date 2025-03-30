import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.logging.*;

class ServidorClimaHilo extends Thread {
    private Socket clientSocket; // Socket del cliente conectado al servidor clima
    private int idSesion; // Identificador unico del cliente
    private PrintWriter out; // Para enviar datos al cliente
    private BufferedReader in; // Para leer datos enviados por el cliente

/* Lista de posibles condiciones del clima*/
    private static String[] climas = {
            "Está lloviendo",
            "Está nublado",
            "Está despejado",
            "ALERTA: Vientos fuertes",
            "Esta nevando",
            "Con probabilidad de granizo"
    };


// Constructor que inicializa el socket y los flujos de comunicación
    public ServidorClimaHilo(Socket socket, int id) {
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

                // Si el cliente envía "salir", se cierra la conexión
                if (inputLine.equalsIgnoreCase("salir")) {
                    out.println("Conexión cerrada.");
                    break;
                }

                // Enviar la respuesta del clima según la fecha recibida
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




/*Método que genera un clima aleatorio para Neuquén si la fecha proporcionada es válida.

Recibe como parametro una fecha con el formato 'dd/mm/yyyy'
Devuelve un String con la condicion climatica (que se elige de manera aleatoria)
*/

    public static String getClimaNeuquen(String fecha) {
        String respuesta;
        if(esFechaValida(fecha)){
            Random rand = new Random();
            respuesta= climas[rand.nextInt(climas.length)];
        }else{
            respuesta = "No es una fecha valida"; // Mensaje de error si la fecha no tiene el formato correcto
        }
        return respuesta;
    }




/*Metodo para verificar si la fecha sigue el formato definido (dd/mm/aaaa)

Recibe como parametro una fecha y devuelve true si es valida o false en caso contrario
*/
    public static boolean esFechaValida(String fecha) {
        String[] partes = fecha.split("/");
        boolean valida;

        // La fecha debe estar separada en 3 partes y tener la estructura correcta
        if (partes.length != 3) //Si tiene menos de tres partes, no es valida
            valida = false;
        else
            valida = (partes[0].length() == 2 && partes[1].length() == 2 && partes[2].length() == 4); //verifica la cantidad de digitos para el dia, el mes y el año
        return valida;
    }
}
