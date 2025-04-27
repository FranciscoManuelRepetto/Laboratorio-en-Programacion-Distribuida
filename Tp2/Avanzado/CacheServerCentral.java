import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class CacheServerCentral { 
    private Map<String, String> consultas; //Map para almacenar las consultas
    private Semaphore mutex; //Semaforo para mantener la integridad de los datos


    public CacheServerCentral(){
        this.mutex= new Semaphore(1); 
        this.consultas = new HashMap<String, String>();
    }

//Para verificar si la consulta ya se encuentra en la caché
        public String getConsulta(String consulta){
        String res=null; //Inicialmente es null
        try{
            mutex.acquire(); //se pide el permiso
            res= consultas.get(consulta);//Busca la respuesta en el hashmap, sino se encuentra la respuesta es null
        }catch(InterruptedException e){}
        finally{
            mutex.release();//finalmente, se libera el permiso
        }
        return res;
    }


//Cuando la respuesta no se encontraba en la caché, se la agrega
    public void nuevaRespuesta(String consultaKey, String respuestaVal) {
        try {
            mutex.acquire(); //se pide el permiso
            consultas.put(consultaKey, respuestaVal); //se agrega en el hashmap
        }catch(InterruptedException e){}
        finally{
            mutex.release(); //finalmente, se libera el permiso
        }
    }
}