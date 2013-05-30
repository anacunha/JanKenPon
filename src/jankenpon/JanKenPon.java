package jankenpon;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 
 * @author Ana Luiza Cunha
 *
 */
public class JanKenPon extends UnicastRemoteObject implements JanKenPonInterface {

	private static final long serialVersionUID = 1L;
	private Campeonato campeonato;
	
	public JanKenPon(int numeroJogadores) throws RemoteException {
		super();
		campeonato = new Campeonato(numeroJogadores);
	}

	public boolean addJogador(String nomeJogador) throws RemoteException {
		return campeonato.addJogador(nomeJogador);
	}

	public boolean clearPartidaEmpatada(String nomeJogador) throws RemoteException {
		return campeonato.clearPartidaEmpatada(nomeJogador);
	}
	
	public boolean isCampeonatoFull() throws RemoteException {
		return campeonato.isFull();
	}

	public boolean isCampeonatoOver() throws RemoteException {
		return campeonato.isOver();
	}
	
	public boolean isPartidaAtualOver(String nomeJogador) throws RemoteException {
		return campeonato.isPartidaAtualOver(nomeJogador);
	}
	
	public boolean recebeJogada(String nomeJogador, String stringJogada) throws RemoteException {
		try {
			Jogada jogada = Jogada.valueOf(stringJogada);
			return campeonato.recebeJogada(nomeJogador, jogada);
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public String getInfoProximaPartida(String nomeJogador) throws RemoteException {
		if(campeonato.getInfoProximaPartida(nomeJogador) != null)
			return campeonato.getInfoProximaPartida(nomeJogador).toString();
		else
			return null;
	}

	public Resultado getResultadoUltimaPartida(String nomeJogador) throws RemoteException {
		return campeonato.getResultadoUltimaPartida(nomeJogador);
	}
}
