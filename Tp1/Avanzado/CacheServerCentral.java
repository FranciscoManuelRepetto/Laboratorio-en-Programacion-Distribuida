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
            res= consultas.get(consulta);//Busca la respuesta en el hashmap 
            mutex.release();
        }catch(InterruptedException e){}
        return res;
    }


    public void nuevaRespuesta(String consultaKey, String respuestaVal) {
        try {
            mutex.acquire(); //Protejemos la concurrencia con Semaforos
            consultas.put(consultaKey, respuestaVal);
            mutex.release();
        }catch(InterruptedException e){}
    }
}