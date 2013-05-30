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
					
			if(jankenpon.addJogador(nomeJogador)) {
				System.out.println("Comecando campeaonato ...");
				
				// Enquanto Campeonato nao esta cheio, aguardamos novos jogadores
				while(!jankenpon.isCampeonatoFull()) {
					System.out.println("Aguardando outros jogadores ...");
					Thread.sleep(10000);
				}
				
				// Quando todos jogadores necessarios se registraram, informa a proxima partida
				System.out.println(jankenpon.getInfoProximaPartida(nomeJogador));
				
				// Enquanto partida atual ainda nao terminou
				while(!jankenpon.isPartidaAtualOver(nomeJogador)) {
				
					// Recebe Jogada
					System.out.print("Informe sua jogada (PEDRA, PAPEL ou TESOURA): ");
					String stringJogada = System.console().readLine().toUpperCase();
					 
					// Verifica validade da jogada
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
					
					// Aguarda jogada do adversario
					while(jankenpon.getResultadoUltimaPartida(nomeJogador) == null) {
						System.out.println("Aguardando jogada do adversario ...");
						Thread.sleep(5000);
					}
					
					// Mostra resultados
					switch (jankenpon.getResultadoUltimaPartida(nomeJogador)) {
						case DERROTA:
							System.out.println("Voce perdeu ...");
							System.exit(1);
							break;
						case EMPATE:
							System.out.println("Empate!\nJogue novamente: ");
							jankenpon.clearPartidaEmpatada(nomeJogador);
							break;
						case VITORIA:
							System.out.println("Voce ganhou ...");
							break;
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
