import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.rmi.RemoteException;


public class ServidorHoroscopo_UI_imp extends UnicastRemoteObject implements ServidorHoroscopo_UI {
    
    /*Lista de signos del zodiaco válidos*/
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

/*Lista de posibles mensajes del horoscopo*/
    private static String[] horoscopos = {
        "Hoy es un gran día para tomar la iniciativa en nuevos proyectos.",
        "La paciencia será clave para alcanzar tus objetivos.",
        "Conversaciones inesperadas traerán nuevas oportunidades.",
        "Confía en tu intuición para tomar decisiones importantes.",
        "Es un buen día para brillar y mostrar tu liderazgo."
    };

    public ServidorHoroscopo_UI_imp() throws RemoteException{
        super();
    }


    public String getHoroscopo(String signo) {
        String respuesta;
        if(signos.contains(signo)){
            Random rand = new Random();
            respuesta = horoscopos[rand.nextInt(horoscopos.length)];
        }else{
            respuesta ="No es un signo valido"; //Mensaje de error si el signo no tiene el formato correcto
        }
        return respuesta;
    }

}
