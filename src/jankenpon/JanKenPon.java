package jankenpon;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 
 * @author Ana Luiza Cunha
 *
 */
public class JanKenPon extends UnicastRemoteObject implements JanKenPonInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JanKenPon() throws RemoteException {
		super();
	}

	public String jogar(Jogada jogada) throws RemoteException {
		
		Jogada jogadaServidor = Jogada.randomJogada();
		String resultado = jogadaServidor + " - Empate";
		
		if(jogada == Jogada.PEDRA) {
			
			if(jogadaServidor == Jogada.TESOURA)
				resultado = jogadaServidor + " - Vitoria";
			else
				if(jogadaServidor == Jogada.PAPEL)
					resultado = jogadaServidor + " - Derrota";			
		}
		else {
			
			if(jogada == Jogada.PAPEL) {
				
				if(jogadaServidor == Jogada.PEDRA)
					resultado = jogadaServidor + " - Vitoria";
				else
					if(jogadaServidor == Jogada.TESOURA)
						resultado = jogadaServidor + " - Derrota";
			}
			else {
				
				if(jogadaServidor == Jogada.PAPEL)
					resultado = jogadaServidor + " - Vitoria";
				else
					if(jogadaServidor == Jogada.PEDRA)
						resultado = jogadaServidor + " - Derrota";
			}
		}
		
		return resultado;
	}

}
