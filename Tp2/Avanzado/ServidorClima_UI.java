import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServidorClima_UI extends Remote {
    String getClimaNeuquen (String fecha) throws RemoteException;
    
}
