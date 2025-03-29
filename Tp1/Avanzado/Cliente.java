import java.io.*;
import java.net.*;
public class Cliente {
    public static void main(String[] args) throws IOException {
        
        //Crea la variables que va a utilizar
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int puerto = Integer.parseInt(args[0]);
        
        try {
            echoSocket = new Socket("localhost", puerto); //Crea un socket en puerto
            out = new PrintWriter(echoSocket.getOutputStream(), true); // Se crea un PrintWriter para enviar datos al cliente a través del socket.
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream())); //Se crea un BufferedReader para leer los datos que el cliente envía al servidor.
        } catch (UnknownHostException e) {
            System.err.println("Host desconocido");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No se puede conectar a localhost"); //esto es cuando el servidor no esta conectado
            System.exit(1);
        }
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        procesarEntradaUsuario(stdIn, out, in);

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }


    private static void procesarEntradaUsuario(BufferedReader stdIn, PrintWriter out, BufferedReader in) throws IOException {
        String userInput;
        System.out.println("Ingrese a continuacion mensajes con formato signo-fecha");
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("echo: " + in.readLine()); //cuando vuelve del server lo imprime
        }
    }
}

