package jankenpon;

/**
 * Representa uma Partida de JanKenPon com dois Jogadores.
 * 
 * @author Ana Luiza Cunha
 *
 */
public class Partida {
	
	private Jogada jogadaPrimeiroJogador;
	private Jogada jogadaSegundoJogador;
	private Jogador primeiroJogador;
	private Jogador segundoJogador;
	private Resultado resultadoPrimeiroJogador;
	private Resultado resultadoSegundoJogador;
	private boolean empate;
	private boolean done;
	
	public Partida(Jogador primeiroJogador, Jogador segundoJogador) {
		this.primeiroJogador = primeiroJogador;
		this.segundoJogador = segundoJogador;
	}
	
	public Partida() {}

	public boolean setPrimeiroJogador(Jogador primeiroJogador) {
		if(this.primeiroJogador == null) {
			this.primeiroJogador = primeiroJogador;
			return true;
		}
		else
			return false;
	}
	
	public Jogador getPrimeiroJogador() {
		return this.primeiroJogador;
	}
	
	public boolean setSegundoJogador(Jogador segundoJogador) {
		if(this.segundoJogador == null) {
			this.segundoJogador = segundoJogador;
			return true;
		}
		else
			return false;
	}
	
	public Jogador getSegundoJogador() {
		return this.segundoJogador;
	}
	
	public void setJogadaPrimeiroJogador(Jogada jogada) {
		empate = false;
		this.jogadaPrimeiroJogador = jogada;
	}
	
	public Jogada getJogadaPrimeiroJogador() {
		return jogadaPrimeiroJogador;
	}
	
	public void setJogadaSegundoJogador(Jogada jogada) {
		empate = false;
		this.jogadaSegundoJogador = jogada;
	}
	
	public Jogada getJogadaSegundoJogador() {
		return jogadaSegundoJogador;
	}
	
	public Jogador getVencedor() {
		
		if(jogadaPrimeiroJogador == null || jogadaSegundoJogador == null)
			return null;
		
		if(jogadaPrimeiroJogador == Jogada.PEDRA) {
			
			if(jogadaSegundoJogador == Jogada.TESOURA) {
				resultadoPrimeiroJogador = Resultado.VITORIA;
				resultadoSegundoJogador = Resultado.DERROTA;
				return primeiroJogador;
			}
				
			else
				if(jogadaSegundoJogador == Jogada.PAPEL) {
					resultadoPrimeiroJogador = Resultado.DERROTA;
					resultadoSegundoJogador = Resultado.VITORIA;
					return segundoJogador;			
				}
		}
		else {
			
			if(jogadaPrimeiroJogador == Jogada.PAPEL) {
				
				if(jogadaSegundoJogador == Jogada.PEDRA) {
					resultadoPrimeiroJogador = Resultado.VITORIA;
					resultadoSegundoJogador = Resultado.DERROTA;
					return primeiroJogador;
				}
				else
					if(jogadaSegundoJogador == Jogada.TESOURA) {
						resultadoPrimeiroJogador = Resultado.DERROTA;
						resultadoSegundoJogador = Resultado.VITORIA;
						return segundoJogador;
					}
			}
			else {
				
				if(jogadaSegundoJogador == Jogada.PAPEL) {
					resultadoPrimeiroJogador = Resultado.VITORIA;
					resultadoSegundoJogador = Resultado.DERROTA;
					return primeiroJogador;
				}
				else
					if(jogadaSegundoJogador == Jogada.PEDRA) {
						resultadoPrimeiroJogador = Resultado.DERROTA;
						resultadoSegundoJogador = Resultado.VITORIA;
						return segundoJogador;
					}
			}
		}
		
		// Empate
		resultadoPrimeiroJogador = Resultado.EMPATE;
		resultadoSegundoJogador = Resultado.EMPATE;
		return null;
	}
	
	public Resultado getResultado(String nomeJogador) {
		if(primeiroJogador.getNome().equals(nomeJogador))
			return resultadoPrimeiroJogador;
		else
			if(segundoJogador.getNome().equals(nomeJogador))
				return resultadoSegundoJogador;
		
		return null;
	}
	
	public String toString() {
		if(primeiroJogador == null && segundoJogador == null)
			return String.format("Indefinido x Indefinido");
		
		if(primeiroJogador == null)
			return String.format("Indefinido x %s", segundoJogador.getNome());
		if(segundoJogador == null)
			return String.format("%s x Indefindo", primeiroJogador.getNome());
		
		return String.format("%s x %s", primeiroJogador.getNome(), segundoJogador.getNome());
	}
	
	public boolean jogadasFeitas() {
		return (jogadaPrimeiroJogador != null && jogadaSegundoJogador != null) ? true : false;
	}
	
	public boolean isOver() {
		return (getVencedor() != null) ? true : false;
	}
	
	public boolean clearPartidaEmpatada(String nomeJogador) {
		if(primeiroJogador.getNome().equals(nomeJogador) && isEmpatada()) {
			jogadaPrimeiroJogador = null;
			jogadaSegundoJogador = null;
			empate = true;
			return true;
		}
		else
			if(segundoJogador.getNome().equals((nomeJogador)) && isEmpatada()) {
				jogadaPrimeiroJogador = null;
				jogadaSegundoJogador = null;
				empate = true;
				return true;
			}
		return false;
	}
	
	public boolean isEmpatada() {
		return (resultadoPrimeiroJogador == Resultado.EMPATE && resultadoSegundoJogador == Resultado.EMPATE) ? true : false;
	}
	
	public boolean isEmpate() {
		return empate;
	}
	
	public boolean isFull() {
		return (primeiroJogador != null && segundoJogador != null) ? true : false;
	}
	
	public boolean isDone() {
		return done;
	}
	
	public void markAsDone() {
		done = true;
	}
}
