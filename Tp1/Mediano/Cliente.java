import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.ArrayList;

class Persona extends Thread {
    private int id;
    public Persona(int id) {
        this.id = id;
    }

    public void run(){
        //Crea un random que sea h o c
        Random random = new Random();
        String valor = random.nextBoolean() ? "h" : "c"; 

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
        
        try{
            System.out.println("Soy el hilo "+Thread.currentThread().getName()+" y envie " +valor);
            //mientras el cliente escriba algo
            out.println(valor); //lo envia al server
            System.out.println("Hilo "+Thread.currentThread().getName()+" echo: " + in.readLine()); //cuando vuelve del server lo imprime con el prefijo "echo"
            
            out.close();
            in.close();
            stdIn.close();
            echoSocket.close();
        }catch (IOException e) {
          System.err.println("Error");
            System.exit(1);  
        }

    }
}

public class Cliente {
    public static void main(String[] args) {
        ArrayList<Thread> clientes = new ArrayList<Thread>();
        for (int i = 0; i < 5; i++) {
            clientes.add(new Persona(i));
        }
        for (Thread thread : clientes) {
            thread.start();
        }
    }
}

