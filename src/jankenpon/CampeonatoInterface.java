package jankenpon;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author Ana Luiza Cunha
 *
 */
public interface CampeonatoInterface extends Remote {
	
	public boolean addJogador(String nomeJogador) throws RemoteException;
	public boolean addJogadorToNextRound(String nomeJogador) throws RemoteException;
	public boolean clearPartidaEmpatada(String nomeJogador) throws RemoteException;
	public boolean isFull() throws RemoteException;
	public boolean isOver() throws RemoteException;
	public boolean isPartidaAtualOver(String nomeJogador) throws RemoteException;
	public boolean markLastPartidaAsDone(String nomeJogador) throws RemoteException;
	public boolean recebeJogada(String nomeJogador, String stringJogada) throws RemoteException;
	public boolean removeJogador(String nomeJogador) throws RemoteException;
	public String getInfoProximaPartida(String nomeJogador) throws RemoteException;
	public String getVencedor() throws RemoteException;
	public Resultado getResultadoUltimaPartida(String nomeJogador) throws RemoteException;
}
