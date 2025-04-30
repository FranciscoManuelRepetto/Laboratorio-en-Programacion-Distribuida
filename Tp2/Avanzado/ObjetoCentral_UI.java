import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObjetoCentral_UI extends Remote {
    String procesarConsulta(String mensaje) throws RemoteException;
}
