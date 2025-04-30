import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObjetoClima_UI extends Remote {
    String getClimaNeuquen (String fecha) throws RemoteException;
    
}
