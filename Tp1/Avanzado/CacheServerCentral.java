import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class CacheServerCentral { 
    private Map<String, String> consultas;
    private Semaphore mutex; 
    public CacheServerCentral(){
        this.mutex= new Semaphore(1);
        this.consultas = new HashMap<String, String>();
    }

        public String getConsulta(String consulta){
        String res=null;
        try{
            mutex.acquire();
            res= consultas.get(consulta); //Si existe retorna la respuesta caso contrario retorna null
            mutex.release();
        }catch(InterruptedException e){}
        return res;
    }


    public void putRespuesta(String consultaKey, String respuestaVal) {
        try {
            mutex.acquire(); //Consultas protegido por mutex
            consultas.put(consultaKey, respuestaVal);
            mutex.release();
        }catch(InterruptedException e){}
    }
}