import java.io.*;
import java.net.*;
public class Cliente {
    public static void main(String[] args) throws IOException {
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            echoSocket = new Socket("localhost", 20000); //Crea un socket en el puerto 200000
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

        while ((userInput = stdIn.readLine()) != null) { 
                    //mientras el cliente escriba algo
            out.println(userInput); //lo envia al server
            System.out.println("echo: " + in.readLine()); //cuando vuelve del server lo imprime con el prefijo "echo"
        }
        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}

