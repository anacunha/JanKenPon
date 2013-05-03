package jankenpon;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author Ana Luiza Cunha
 *
 */
public interface JanKenPonInterface extends Remote {
	
	public String jogar(Jogada jogada) throws RemoteException;
}
