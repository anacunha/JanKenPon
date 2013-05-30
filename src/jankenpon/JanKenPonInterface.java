package jankenpon;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author Ana Luiza Cunha
 *
 */
public interface JanKenPonInterface extends Remote {
	
	public boolean addJogador(String nomeJogador) throws RemoteException;
	public boolean clearPartidaEmpatada(String nomeJogador) throws RemoteException;
	public boolean isCampeonatoFull() throws RemoteException;
	public boolean isCampeonatoOver() throws RemoteException;
	public boolean isPartidaAtualOver(String nomeJogador) throws RemoteException;
	public boolean recebeJogada(String nomeJogador, String stringJogada) throws RemoteException;
	public String getInfoProximaPartida(String nomeJogador) throws RemoteException;
	public Resultado getResultadoUltimaPartida(String nomeJogador) throws RemoteException;
}
