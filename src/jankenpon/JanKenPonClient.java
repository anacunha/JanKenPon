package jankenpon;

import java.rmi.Naming;

/**
 * 
 * @author Ana Luiza Cunha
 *
 */
public class JanKenPonClient {
	
	public static void main(String args[]) {
		
		if(args.length != 1) {
			System.out.println("Uso: java JanKenPon <nome>");
			System.exit(1); 
		}
		
		try {
			CampeonatoInterface campeonato = (CampeonatoInterface) Naming.lookup("//localhost/Campeonato");
			String nomeJogador = args[0].trim(); 
					
			// Adiciona Jogador ao Campeonato
			if(campeonato.addJogador(nomeJogador)) {
				System.out.println("Comecando campeonato ...\n");
				
				// Enquanto Campeonato nao esta cheio, aguarda novos jogadores
				while(!campeonato.isFull()) {
					System.out.println("Aguardando outros jogadores ...");
					Thread.sleep(5000);
				}
				
				// Quando todos jogadores necessarios se registraram, inicia o Campeonato
				while(!campeonato.isOver()) {
					
					String infoProximaPartida = campeonato.getInfoProximaPartida(nomeJogador);
					
					// Aguarda informacoes sobre a proxima partida do Jogador
					while(infoProximaPartida == null) {
						System.out.println("Aguardando proxima partida ...");
						Thread.sleep(3000);
						infoProximaPartida = campeonato.getInfoProximaPartida(nomeJogador);
					}
					System.out.println("\n" + campeonato.getInfoProximaPartida(nomeJogador));
					
					// Enquanto Partida atual ainda nao terminou
					while(!campeonato.isPartidaAtualOver(nomeJogador)) {
					
						// Recebe a Jogada do Jogador
						System.out.print("Informe sua jogada (PEDRA, PAPEL ou TESOURA): ");
						String stringJogada = System.console().readLine().toUpperCase();
						 
						// Verifica a validade da Jogada
						while(!campeonato.recebeJogada(nomeJogador, stringJogada)) {
							
							if(stringJogada.isEmpty())
								System.out.println("Escolha sua jogada entre: PEDRA, PAPEL ou TESOURA.");
							else {
								System.out.printf("\nVoce jogou %s\n", stringJogada);
								System.out.println("Escolha sua jogada entre: PEDRA, PAPEL ou TESOURA.");
							}
							stringJogada = System.console().readLine().toUpperCase();
						}
						System.out.printf("\nVoce jogou %s\n", stringJogada);
						
						// Aguarda a Jogada do adversario
						while(campeonato.getResultadoUltimaPartida(nomeJogador) == null) {
							System.out.println("Aguardando jogada do adversario ...");
							Thread.sleep(3000);
						}
						
						// Mostra resultados da Partida
						switch (campeonato.getResultadoUltimaPartida(nomeJogador)) {
							case DERROTA:
								System.out.println("\nVoce perdeu ...");
								
								// Caso tenha perdido, sai do Campeonato
								campeonato.removeJogador(nomeJogador);
								System.exit(1);
								break;
							case EMPATE:
								System.out.println("\nEmpate!\n\nJogue novamente: ");
								
								// Caso ocorra empate, reseta os dados da Partida para jogar novamente
								campeonato.clearPartidaEmpatada(nomeJogador);
								Thread.sleep(3000);
								break;
							case VITORIA:
								System.out.println("\nVoce ganhou esta partida ...\n");
								
								// Caso tenha vencido, vai para proxima etapa
								campeonato.addJogadorToNextRound(nomeJogador);
								Thread.sleep(4000);
								campeonato.markLastPartidaAsDone(nomeJogador);
								break;
						}
					}
				}
				
				if(campeonato.getVencedor().equals(nomeJogador))
					System.out.println("Parabens, " + nomeJogador + "! Voce venceu o Campeonato de JanKenPon!");
			}
			else {
				// O Campeonato ja esta cheio
				if(campeonato.isFull())
					System.out.println("O campeonato atual ja esta cheio");
				// Ou ja existe um Jogador com mesmo nome
				else
					System.out.println("Ja existe um jogador com este nome.");
				System.exit(1);
			}
		}
		catch(Exception e) {
			System.out.println("JanKenPonCliente has failed.");
			e.printStackTrace();
		}
	}

}
