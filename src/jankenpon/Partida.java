package jankenpon;

public class Partida {
	
	private Jogada jogadaPrimeiroJogador;
	private Jogada jogadaSegundoJogador;
	private Jogador primeiroJogador;
	private Jogador segundoJogador;
	private Resultado resultadoPrimeiroJogador;
	private Resultado resultadoSegundoJogador;
	private boolean empate;
	
	public Partida(Jogador primeiroJogador, Jogador segundoJogador) {
		this.primeiroJogador = primeiroJogador;
		this.segundoJogador = segundoJogador;
	}
	
	public Jogador getPrimeiroJogador() {
		return this.primeiroJogador;
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
		return String.format("Partida: %s x %s", primeiroJogador.getNome(), segundoJogador.getNome());
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
}
