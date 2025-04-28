import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServCent_UI_imp extends UnicastRemoteObject implements ServCent_UI {
    private CacheServerCentral cache;
    private String ipClima, ipHoroscopo;
    private int pClima, pHoroscopo;

    public ServCent_UI_imp(String ipClima, int pClima, String ipHoroscopo, int pHoroscopo) throws RemoteException {
        super();
        this.cache = new CacheServerCentral();
        this.ipClima = ipClima;
        this.pClima = pClima;
        this.ipHoroscopo = ipHoroscopo;
        this.pHoroscopo = pHoroscopo;
    }

    @Override
    public String procesarConsulta(String mensaje) throws RemoteException {
        String[] partes = procesarEntrada(mensaje);
        String respuesta;
        
        if (partes == null) {
            respuesta= "Error: Formato incorrecto. Use 'signo;fecha'";
        }

        String signo = partes[0];
        String fecha = partes[1];

        String respuestaHoro = cache.getConsulta(signo);
        String respuestaClima = cache.getConsulta(fecha);

        if (respuestaHoro == null) {
            respuestaHoro = consultarServidor(signo, ipHoroscopo, pHoroscopo, "ServidorHoroscopo");
            cache.nuevaRespuesta(signo, respuestaHoro);
        } else {
            System.out.println("Horóscopo obtenido desde caché.");
        }

        if (respuestaClima == null) {
            respuestaClima = consultarServidor(fecha, ipClima, pClima, "ServidorClima");
            cache.nuevaRespuesta(fecha, respuestaClima);
        } else {
            System.out.println("Clima obtenido desde caché.");
        }

        respuesta = "Horóscopo: " + respuestaHoro + " | Clima: " + respuestaClima;
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
        String[] partes = input.split(";");
        if (partes.length != 2) {
            return null;
        }
        return new String[]{partes[0].trim(), partes[1].trim()};
    }
}
