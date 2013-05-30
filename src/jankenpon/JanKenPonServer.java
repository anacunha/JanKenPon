package jankenpon;

import java.rmi.Naming;

/**
 * 
 * @author Ana Luiza Cunha
 *
 */
public class JanKenPonServer {
	
	public static void main(String[] args) {

		if(args.length != 1) {
			System.out.println("Uso: java JanKenPonServer <numero-de-jogadores>");
			System.exit(1);
		}

		try {
			int numeroJogadores = Integer.parseInt(args[0]);
			
			if(numeroJogadores != 2 && numeroJogadores != 4 && numeroJogadores != 8 && numeroJogadores != 16 && numeroJogadores != 32) {
		        System.out.println("O campeonato deve ser disputador por 2, 4, 8, 16 ou 32 jogadores.");
		        System.exit(1);
		      }
			
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("RMI Registry ready.");
			
			Naming.rebind("JanKenPon", new JanKenPon(numeroJogadores));
			System.out.println("JanKenPonServer is ready.");
			
			System.out.println("Campeonato iniciado com " + numeroJogadores + " jogadores.");
		}
		catch(Exception e) {
			System.out.println("JanKenPonServer has failed.");
			
			if(e instanceof IllegalArgumentException)
				System.out.println("O campeonato deve ser disputador por 2, 4, 8, 16 ou 32 jogadores.");
			
			e.printStackTrace();
		}
	}

}
