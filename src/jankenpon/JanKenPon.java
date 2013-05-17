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
		String resultado = jogadaServidor + "\nResultado: EMPATE";
		
		if(jogada == Jogada.PEDRA) {
			
			if(jogadaServidor == Jogada.TESOURA)
				resultado = jogadaServidor + "\nResultado: VITORIA";
			else
				if(jogadaServidor == Jogada.PAPEL)
					resultado = jogadaServidor + "\nResultado: DERROTA";			
		}
		else {
			
			if(jogada == Jogada.PAPEL) {
				
				if(jogadaServidor == Jogada.PEDRA)
					resultado = jogadaServidor + "\nResultado: VITORIA";
				else
					if(jogadaServidor == Jogada.TESOURA)
						resultado = jogadaServidor + "\nResultado: DERROTA";
			}
			else {
				
				if(jogadaServidor == Jogada.PAPEL)
					resultado = jogadaServidor + "\nResultado: VITORIA";
				else
					if(jogadaServidor == Jogada.PEDRA)
						resultado = jogadaServidor + "\nResultado: DERROTA";
			}
		}
		
		return resultado;
	}

}
