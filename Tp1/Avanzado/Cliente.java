import java.io.*;
import java.net.*;
public class Cliente {
    public static void main(String[] args) throws IOException {
        
        /*Variables*/
        Socket echoSocket = null;   // Socket para poder conectarse con el server cental
        PrintWriter out = null;     // Para enviar datos al cliente a través del socket
        BufferedReader in = null;   // Para leer los datos que el cliente envía al servidor
        int puerto = Integer.parseInt(args[0]); //Para poder crear el socket en un puerto, que se ingresa por consola
        

        /*Conectandonos*/
        try {
            echoSocket = new Socket("localhost", puerto); 
            out = new PrintWriter(echoSocket.getOutputStream(), true); 
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Host desconocido");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No se puede conectar a localhost"); //esto es cuando el servidor no esta conectado
            System.exit(1);
        }

        /*Lectura de entrada de usuario*/
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        procesarEntradaUsuario(stdIn, out, in);

        /*Cierre de recursos*/
        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }

    /*Procesamiento de la entrada del usuario*/
    private static void procesarEntradaUsuario(BufferedReader stdIn, PrintWriter out, BufferedReader in) throws IOException {
    String userInput;
    System.out.println("Ingrese a continuacion mensajes con formato signo;fecha. Ingrese 'salir' para salir");

    while ((userInput = stdIn.readLine()) != null) {
        if (userInput.equalsIgnoreCase("salir")) { //Si se ingreso salir
            System.out.println("Cerrando conexión...");
            break;
        }
        out.println(userInput);
        System.out.println("echo: " + in.readLine()); // cuando vuelve del server, se muestra por consola
    }
}
}

