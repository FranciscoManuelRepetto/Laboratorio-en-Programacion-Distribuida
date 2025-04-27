import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServidorHoroscopo_UI extends Remote {
    String getHoroscopo (String signo) throws RemoteException;
    
}
