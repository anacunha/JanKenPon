package jankenpon;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Representa um Campeonato de JanKenPon, com os Jogadores cadastrados
 * no Campeonato e as Partidas realizadas.
 * 
 * @author Ana Luiza Cunha
 *
 */
public class Campeonato extends UnicastRemoteObject implements CampeonatoInterface {
	
	private static final long serialVersionUID = 1L;
	private int numeroJogadores;
	private ArrayList<Jogador> jogadores;
	private ArrayList<Partida> partidas;
	
	public Campeonato(int numeroJogadores) throws RemoteException {
		super();
		this.numeroJogadores = numeroJogadores;
		jogadores = new ArrayList<Jogador>(numeroJogadores);
		partidas = new ArrayList<Partida>(numeroJogadores - 1);
	}
	
	public boolean addJogador(String nomeJogador) throws RemoteException {
		// Nao adicona mais jogadores se o campeonato ja esta cheio
		if(jogadores.size() == numeroJogadores)
			return false;
		
		// Nao adiciona jogadores com mesmo nome
		for(Jogador jogador : jogadores)
			if(jogador.getNome().equalsIgnoreCase(nomeJogador))
				return false;
		
		jogadores.add(new Jogador(nomeJogador));
		System.out.printf("\n- %s entra no campeonato\n", nomeJogador);
		System.out.printf("\nJogadores no campeonato: %d", jogadores.size());
		System.out.printf("\nJogadores: %s\n", jogadores.toString());
		
		// Quando Campeonato esta completo, inicia as Partidas
		if (jogadores.size() == numeroJogadores) {
			System.out.printf("\nCampeonato completo com %d jogadores.", numeroJogadores);
			System.out.println("\nIniciando o campeonato ... ");
			System.out.println("Criando partidas ... ");
			
			criarPartidas();
		}
		return true;
	}
	
	private boolean criarPartidas() throws RemoteException {
		
		// Se o Campeonato ainda nao esta cheio, nao criamos as Partidas
		if(!isFull())
			return false;

		// Cria as Partidas do primeiro round do Campeonato
		for(int i = 0; i < (numeroJogadores / 2); i++) {
			partidas.add(new Partida(jogadores.get(i*2), jogadores.get((i*2)+1)));
			System.out.printf("\nPartida %d - %s", i+1, partidas.get(i).toString());
		}
		
		// Cria as Partidas dos rounds posteriores, ainda sem Jogadores definidos
		for(int i = numeroJogadores/2; i < numeroJogadores - 1; i++) {
			partidas.add(new Partida());
			System.out.printf("\nPartida %d - %s", i+1, partidas.get(i).toString());
		}
		System.out.printf("\nNumero total de partidas: %d\n", partidas.size());
		return true;
	}
	
	public boolean isOver() throws RemoteException {
		// O Campeonato esta finalizado quando todas suas partidas terminaram
		for(Partida partida : partidas) {
			if(!partida.isOver())
				return false;
		}
		return true;
	}
	
	public boolean isFull() throws RemoteException {
		return (jogadores.size() == numeroJogadores) ? true : false;
	}
	
	/**
	 * Retorna a proxima Partida disponivel para determinado Jogador.
	 */
	public String getInfoProximaPartida(String nomeJogador) throws RemoteException {
		for(Partida partida : partidas) {
			if(partida.isFull()) {
				if(partida.getPrimeiroJogador().getNome().equals(nomeJogador) && partida.getVencedor() == null)
					return partida.toString();
				else
					if(partida.getSegundoJogador().getNome().equals(nomeJogador) && partida.getVencedor() == null)
						return partida.toString();
			}
		}
		return null;
	}
	
	/**
	 * Recebe a Jogada para a Partida atual de determinado Jogador.
	 */
	public boolean recebeJogada(String nomeJogador, String stringJogada) throws RemoteException {
		try {
			Jogada jogada = Jogada.valueOf(stringJogada);
			// Procura a partida em andamento do qual jogador faz parte
			for(Partida partida : partidas) {
				if(partida.getPrimeiroJogador().getNome().equals(nomeJogador) && !partida.jogadasFeitas()) {
					partida.setJogadaPrimeiroJogador(jogada);
					System.out.printf("\n- %s jogou %s\n", nomeJogador, jogada);
					
					// Se o adversario ja jogou, calcula resultado da partida
					if(partida.getJogadaSegundoJogador() != null) {
						partida.getVencedor();
						System.out.printf("\n%s x %s - Vencedor: %s", partida.getPrimeiroJogador(), partida.getSegundoJogador(), partida.getVencedor());
					}
					return true;
				}
				else {
					if(partida.getSegundoJogador().getNome().equals(nomeJogador) && !partida.jogadasFeitas()) {
						partida.setJogadaSegundoJogador(jogada);
						System.out.printf("\n- %s jogou %s\n", nomeJogador, jogada);
						
						// Se o adversario ja jogou, calcula resultado da partida
						if(partida.getJogadaPrimeiroJogador() != null) {
							partida.getVencedor();
							System.out.printf("\n\n%s x %s - Vencedor: %s\n\n", partida.getPrimeiroJogador(), partida.getSegundoJogador(), partida.getVencedor());
						}
						return true;
					}
				}
			}
		}
		catch(Exception e){}
		return false;
	}
	
	/**
	 * Retorna o Resultado da ultima Partida de determinado Jogador.
	 */
	public Resultado getResultadoUltimaPartida(String nomeJogador) throws RemoteException {
		// Busca a ultima Partida ainda nao finalizada do Jogador
		for(Partida partida : partidas) {
			if(partida.isFull() && !partida.isDone()) {
				if(partida.getPrimeiroJogador().getNome().equals(nomeJogador) && (partida.jogadasFeitas() || partida.isEmpate())) {
					return partida.getResultado(nomeJogador);
				}
				else {
					if(partida.getSegundoJogador().getNome().equals(nomeJogador) && (partida.jogadasFeitas() || partida.isEmpate())) {
						return partida.getResultado(nomeJogador);
					}
				}
			}
		}
		return null;
	}
	
	public boolean clearPartidaEmpatada(String nomeJogador) throws RemoteException {	
		// Procura a partida empatada do qual jogador faz parte
		for(Partida partida : partidas) {
			if(partida.getPrimeiroJogador().getNome().equals(nomeJogador)  && partida.isEmpatada()) {
				// Limpa os dados das Jogadas para aquela partida empatada
				return partida.clearPartidaEmpatada(nomeJogador);
			}
			else {
				if(partida.getSegundoJogador().getNome().equals(nomeJogador) && partida.isEmpatada()) {
					// Limpa os dados das Jogadas para aquela partida empatada
					return partida.clearPartidaEmpatada(nomeJogador);
				}
			}
		}
		return false;
	}
	
	public boolean isPartidaAtualOver(String nomeJogador) throws RemoteException {				
		// Procura a Partida atual do qual jogador faz parte
		for(int i = partidas.size() - 1; i >= 0; i--) {
			if(partidas.get(i).isFull()) {
				if(partidas.get(i).getPrimeiroJogador().getNome().equals(nomeJogador)) {
					return partidas.get(i).isOver();
				}
				else {
					if(partidas.get(i).getSegundoJogador().getNome().equals(nomeJogador)) {
						return partidas.get(i).isOver();
					}
				}
			}
		}
		return false;			
	}
	
	/**
	 * Remove o Jogador que perdeu do Campeonato.
	 */
	public boolean removeJogador(String nomeJogador) throws RemoteException {
		for(Jogador jogador : jogadores) {
			jogador.getNome().equals(nomeJogador);
			jogadores.remove(jogador);
			System.out.printf("\n- %s foi eliminado.", nomeJogador);
			return true;
		}
		return false;
	}
	
	/**
	 * Adiciona o Jogador que ganhou para o proximo Round do Campeonato.
	 */
	public boolean addJogadorToNextRound(String nomeJogador) throws RemoteException {
		if(this.isOver())
			return false;
		
		for(Partida partida : partidas)
			if(!partida.isFull()) {
				if(partida.getPrimeiroJogador() == null) {
					System.out.printf("\n\n- %s passou para proximo round.\n", nomeJogador);
					partida.setPrimeiroJogador(new Jogador(nomeJogador));
					return true;
				}
				else
					if(partida.getSegundoJogador() == null) {
						System.out.printf("\n\n- %s passou para proximo round.\n", nomeJogador);
						partida.setSegundoJogador(new Jogador(nomeJogador));
						if(partida.isFull())
							System.out.println("\nNova Partida: " + partida.toString());
						return true;
					}
			}		
		return false;
	}
	
	/**
	 * Retorna o vencedor do Campeonato.
	 */
	public String getVencedor() throws RemoteException {
		if(this.isOver()) {
			System.out.println("\nCampeonato finalizado!");
			System.out.println("\nVencedor: " + partidas.get(partidas.size() -1).getVencedor());
			return partidas.get(partidas.size() -1).getVencedor().toString();
		}
		else
			return null;
	}
	
	/**
	 * Marca uma Partida como finalizada.
	 */
	public boolean markLastPartidaAsDone(String nomeJogador) throws RemoteException {
		for(Partida partida : partidas)
			if(partida.isFull() && !partida.isDone()) {
				if(partida.getPrimeiroJogador().getNome().equals(nomeJogador)) {
					System.out.printf("\nFinalizada a partida %s\n", partida.toString());
					partida.markAsDone();
					return true;
				}
				else
					if(partida.getSegundoJogador().getNome().equals(nomeJogador)) {
						System.out.printf("\nFinalizada a partida %s\n", partida.toString());
						partida.markAsDone();
						return true;
					}
			}
		return false;
	}
}
