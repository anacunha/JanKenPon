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
			JanKenPonInterface jankenpon = (JanKenPonInterface) Naming.lookup("//localhost/JanKenPon");
			String nomeJogador = args[0].trim(); 
					
			// Adiciona Jogador ao Campeonato
			if(jankenpon.addJogador(nomeJogador)) {
				System.out.println("Comecando campeaonato ...");
				
				// Enquanto Campeonato nao esta cheio, aguarda novos jogadores
				while(!jankenpon.isCampeonatoFull()) {
					System.out.println("Aguardando outros jogadores ...");
					Thread.sleep(5000);
				}
				
				// Quando todos jogadores necessarios se registraram, inicia o Campeonato
				while(!jankenpon.isCampeonatoOver()) {
					
					String infoProximaPartida = jankenpon.getInfoProximaPartida(nomeJogador);
					
					// Aguarda informacoes sobre a proxima partida do Jogador
					while(infoProximaPartida == null) {
						System.out.println("Aguardando proxima partida ...");
						Thread.sleep(3000);
						infoProximaPartida = jankenpon.getInfoProximaPartida(nomeJogador);
					}
					System.out.println(jankenpon.getInfoProximaPartida(nomeJogador));
					
					// Enquanto Partida atual ainda nao terminou
					while(!jankenpon.isPartidaAtualOver(nomeJogador)) {
					
						// Recebe a Jogada do Jogador
						System.out.print("Informe sua jogada (PEDRA, PAPEL ou TESOURA): ");
						String stringJogada = System.console().readLine().toUpperCase();
						 
						// Verifica a validade da Jogada
						while(!jankenpon.recebeJogada(nomeJogador, stringJogada)) {
							
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
						while(jankenpon.getResultadoUltimaPartida(nomeJogador) == null) {
							System.out.println("Aguardando jogada do adversario ...");
							Thread.sleep(3000);
						}
						
						// Mostra resultados da Partida
						switch (jankenpon.getResultadoUltimaPartida(nomeJogador)) {
							case DERROTA:
								System.out.println("Voce perdeu ...");
								
								// Caso tenha perdido, sai do Campeonato
								jankenpon.removeJogador(nomeJogador);
								System.exit(1);
								break;
							case EMPATE:
								System.out.println("Empate!\n\nJogue novamente: ");
								
								// Caso ocorra empate, reseta os dados da Partida para jogar novamente
								jankenpon.clearPartidaEmpatada(nomeJogador);
								Thread.sleep(3000);
								break;
							case VITORIA:
								System.out.println("Voce ganhou ...");
								
								// Caso tenha vencido, vai para proxima etapa
								jankenpon.addJogadorToNextRound(nomeJogador);
								Thread.sleep(4000);
								jankenpon.markLastPartidaAsDone(nomeJogador);
								break;
						}
					}
				}
			}
			else {
				// O Campeonato ja esta cheio
				if(jankenpon.isCampeonatoFull())
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
