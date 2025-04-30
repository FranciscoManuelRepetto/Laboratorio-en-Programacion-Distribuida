import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObjetoCentral_UI_imp extends UnicastRemoteObject implements ObjetoCentral_UI {
    private CacheServerCentral cache;
    private String ipClima, ipHoroscopo;
    private int pClima, pHoroscopo;

    public ObjetoCentral_UI_imp(String ipClima, int pClima, String ipHoroscopo, int pHoroscopo) throws RemoteException {
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
            return "Error: Formato incorrecto. Use 'signo;fecha'"; //se agrega return para que corte con la ejecucion
        }

        String signo = partes[0];
        String fecha = partes[1];

        String respuestaHoro = cache.getConsulta(signo);
        String respuestaClima = cache.getConsulta(fecha);

        if (respuestaHoro == null) {
            respuestaHoro = consultarObjeto(signo, ipHoroscopo, pHoroscopo, "ObjetoHoroscopo");
            cache.nuevaRespuesta(signo, respuestaHoro);
        } else {
            System.out.println("Horóscopo obtenido desde caché.");
        }

        if (respuestaClima == null) {
            respuestaClima = consultarObjeto(fecha, ipClima, pClima, "ObjetoClima");
            cache.nuevaRespuesta(fecha, respuestaClima);
        } else {
            System.out.println("Clima obtenido desde caché.");
        }

        respuesta = "Horóscopo: " + respuestaHoro + " | Clima: " + respuestaClima;
        return respuesta;
    }

    private String consultarObjeto(String mensaje, String host, int puerto, String nombreObjeto) {
        String respuesta = "Error al conectar con " + nombreObjeto;
        try {
            if (nombreObjeto.equals("ObjetoHoroscopo")) {
                ObjetoHoroscopo_UI horoscopo = (ObjetoHoroscopo_UI) Naming.lookup("//" + host + ":" + puerto + "/" + nombreObjeto);
                respuesta = horoscopo.getHoroscopo(mensaje);
            } else if (nombreObjeto.equals("ObjetoClima")) {
                ObjetoClima_UI clima = (ObjetoClima_UI) Naming.lookup("//" + host + ":" + puerto + "/" + nombreObjeto);
                respuesta = clima.getClimaNeuquen(mensaje);
            }
        } catch (Exception e) {
            System.err.println("Error al conectar con " + nombreObjeto);
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
