import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.rmi.RemoteException;

public class ServidorClima_UI_imp extends UnicastRemoteObject implements ServidorClima_UI {

    /* Lista de posibles condiciones del clima */
    private static String[] climas = {
            "Está lloviendo",
            "Está nublado",
            "Está despejado",
            "ALERTA: Vientos fuertes",
            "Esta nevando",
            "Con probabilidad de granizo"
    };

    public ServidorClima_UI_imp() throws RemoteException {
        super();
    }

    /*
     * Método que genera un clima aleatorio si la fecha proporcionada es válida.
     * 
     * Recibe como parametro una fecha con el formato 'dd/mm/yyyy'
     * Devuelve un String con la condicion climatica (que se elige de manera
     * aleatoria)
     */
    @Override
    public String getClimaNeuquen(String fecha) {
        String respuesta;
        if (esFechaValida(fecha)) {
            Random rand = new Random();
            respuesta = climas[rand.nextInt(climas.length)];
        } else {
            respuesta = "No es una fecha valida"; // Mensaje de error si la fecha no tiene el formato correcto
        }
        return respuesta;
    }

    /*
     * Metodo para verificar si la fecha sigue el formato definido (dd/mm/aaaa).
     * 
     * Además, se verifica que la fecha sea una fecha real.
     * Recibe como parametro una fecha y devuelve true si es valida o false en caso
     * contrario
     */
    public static boolean esFechaValida(String fecha) {
        String[] partes = fecha.split("/");
        boolean valida = true;

        // La fecha debe estar separada en 3 partes y tener la estructura correcta
        if (partes.length != 3) // Si tiene menos de tres partes, no es valida
            valida = false;
        else {

            // Verificamos que la fecha sea real.
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int anio = Integer.parseInt(partes[2]);

            if (mes < 1 || mes > 12)
                valida = false;

            // Para guardar la cantidad máxima de días para el mes ingresado
            int diasEnMes = 0;

            // Segun el mes que se ingresó, obtenemos la cantidad de dias de ese mes.
            switch (mes) {

                // Para el caso de enero, marzo, mayo, julio, agosto, octubre y diciembre
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    diasEnMes = 31;
                    break;

                // Para el caso de abril, junio, septiembre y noviembre
                case 4:
                case 6:
                case 9:
                case 11:
                    diasEnMes = 30;
                    break;

                // Para el caso de febrero
                case 2:
                    // Este mes es un caso especial dado que depende de si el año es bisiesto.
                    // Para que un año sea bisiesto tiene que ser divisible por 4, pero no por 100.
                    // Y tambien si es divisible por 400.

                    boolean esBisiesto = ((anio % 4 == 0 && anio % 100 != 0) || anio % 400 == 0);
                    diasEnMes = esBisiesto ? 29 : 28; // Si es bisiesto, entonces seran 29 dias, caso contrario 28
                    break;
            }

            if (dia < 1 || dia > diasEnMes)
                valida = false;
        }
        return valida;

    }
}
