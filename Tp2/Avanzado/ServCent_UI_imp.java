import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServCent_UI_imp extends UnicastRemoteObject implements ServCent_UI {
    private CacheServerCentral cache; //para la cache
    private int pClima, pHoroscopo; //para el servidor del clima y del horoscopo

    public ServCent_UI_imp(int pClima, int pHoroscopo) throws RemoteException {
        super();
        this.cache = new CacheServerCentral();
        this.pClima = pClima;
        this.pHoroscopo = pHoroscopo;
    }

    @Override
    public String procesarConsulta(String mensaje) throws RemoteException {
      //procesar entrada del cliente
        String[] partes = procesarEntrada(mensaje);
        String respuesta;
        
        if (partes==null) {
            respuesta="Error: Formato incorrecto. Use 'signo;fecha'";
        }

        String signo = partes[0]; //parte zodiacal
        String fecha = partes[1]; //parte clima

        //consultamos primero a la cache si tiene la respuesta a las peticiones
        String respuestaHoro = cache.getConsulta(signo);
        String respuestaClima = cache.getConsulta(fecha);

        //si la cache no tiene la respuesta, vamos al servidor correspondiente
        if (respuestaHoro == null) {
            respuestaHoro = consultarServidor(signo, "localhost", pHoroscopo, "ServidorHoroscopo");
            cache.nuevaRespuesta(signo, respuestaHoro); // Guardamos en caché
        } else {
            System.out.println("Horóscopo obtenido desde caché.");
        }

//se aplica la misma logica con lo del clima
        if (respuestaClima == null) {
            respuestaClima = consultarServidor(fecha, "localhost", pClima, "ServidorClima");
            cache.nuevaRespuesta(fecha, respuestaClima); // Guardamos en caché
        } else {
            System.out.println("Clima obtenido desde caché.");
        }

        respuesta="Horóscopo: " + respuestaHoro + " | Clima: " + respuestaClima;
        
        return respuesta;
    }

    
    private String consultarServidor(String mensaje, String host, int puerto, String nombreServidor) {
        String respuesta = "Error al conectar con " + nombreServidor;
        try {
            if (nombreServidor.equals("ServidorHoroscopo")) {
                ServidorHoroscopo_UI horoscopo = (ServidorHoroscopo_UI) Naming.lookup("//" + host + ":" + puerto + "/" + nombreServidor);
                respuesta = horoscopo.getHoroscopo(mensaje);
            } else if (nombreServidor.equals("ServidorClima")) {
                ServidorClima_UI clima = (ServidorClima_UI) Naming.lookup("//" + host + ":" + puerto + "/" + nombreServidor);
                respuesta = clima.getClimaNeuquen(mensaje);
            }
        } catch (Exception e) {
            System.err.println("Error al conectar con " + nombreServidor);
            e.printStackTrace();
        }
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
}
