package jankenpon;

import java.util.ArrayList;

public class Campeonato {
	
	private int numeroJogadores;
	private ArrayList<Jogador> jogadores;
	private ArrayList<Partida> partidas;
	
	public Campeonato(int numeroJogadores) {
		this.numeroJogadores = numeroJogadores;
		jogadores = new ArrayList<Jogador>(numeroJogadores);
		partidas = new ArrayList<Partida>(numeroJogadores - 1);
	}
	
	public boolean addJogador(String nomeJogador) {
		// Nao adicona mais jogadores se o campeonato ja esta cheio
		if(jogadores.size() == numeroJogadores)
			return false;
		
		// Nao adiciona jogadores com mesmo nome
		for(Jogador j : jogadores)
			if(j.getNome().equalsIgnoreCase(nomeJogador))
				return false;
		
		jogadores.add(new Jogador(nomeJogador));
		System.out.println("Jogadores no campeonato: " + jogadores.size());
		System.out.println("Jogadores: " + jogadores.toString());
		
		if (jogadores.size() == numeroJogadores) {
			System.out.printf("\nCampeonato cheio com %d jogadores.", numeroJogadores);
			System.out.printf("\nIniciando o campeonato ... ", numeroJogadores);
			System.out.printf("\nCriando partidas ... ", numeroJogadores);
			criarPartidas();
		}
		
		return true;
	}

	public boolean criarPartidas() {
		if(jogadores.size() != numeroJogadores)
			return false;
		
		for(int i = 0; i < (numeroJogadores / 2); i++) {
			partidas.add(new Partida(jogadores.get(i*2), jogadores.get((i*2)+1)));
			System.out.printf("\nPartida %d - %s x %s", i+1, partidas.get(i).getPrimeiroJogador(), partidas.get(i).getSegundoJogador());
		}
		
		return true;
	}
	
	public ArrayList<Partida> getPartidas() {
		return this.partidas;
	}
	
	public boolean isOver() {
		return false;
	}
	
	public boolean isFull() {
		return (jogadores.size() == numeroJogadores) ? true : false;
	}
	
	public Partida getInfoProximaPartida(String nomeJogador) {
		for(Partida partida : partidas) {
			if(partida.getPrimeiroJogador().getNome().equals(nomeJogador) && partida.getVencedor() == null)
				return partida;
			else
				if(partida.getSegundoJogador().getNome().equals(nomeJogador) && partida.getVencedor() == null)
					return partida;			
		}
		return null;
	}
	
	public boolean recebeJogada(String nomeJogador, Jogada jogada) {
		for(Partida partida : partidas) {
			if(partida.getPrimeiroJogador().getNome().equals(nomeJogador) && !partida.jogadasFeitas()) {
				partida.setJogadaPrimeiroJogador(jogada);
				
				if(partida.getJogadaSegundoJogador() != null)
					partida.getVencedor();
				return true;
			}
			else
				if(partida.getSegundoJogador().getNome().equals(nomeJogador) && !partida.jogadasFeitas()) {
					partida.setJogadaSegundoJogador(jogada);
					if(partida.getJogadaPrimeiroJogador() != null)
						partida.getVencedor();
					return true;
				}
		}
		return false;
	}
	
	public Resultado getResultadoUltimaPartida(String nomeJogador) {
		for(Partida partida : partidas) {
			if(partida.getPrimeiroJogador().getNome().equals(nomeJogador) && partida.jogadasFeitas()) {
				return partida.getResultado(nomeJogador);
			}
			else
				if(partida.getSegundoJogador().getNome().equals(nomeJogador) && partida.jogadasFeitas()) {
					return partida.getResultado(nomeJogador);
				}
		}
		return null;
	}
}
