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
			System.out.println("Uso: java JanKenPon <jogada>");
			System.exit(1); 
		}
		
		try {
			JanKenPonInterface jankenpon = (JanKenPonInterface) Naming.lookup("//localhost/JanKenPon");
			Jogada jogada = Jogada.valueOf(args[0].toUpperCase());
			System.out.println("Voce jogou: " + jogada);
			System.out.println(jankenpon.jogar(jogada));
		}
		catch(Exception e) {
			System.out.println("JanKenPonCliente has failed.");
			
			if(e instanceof IllegalArgumentException)
				System.out.println("Escolha sua jogada entre: PEDRA, PAPEL ou TESOURA.");
			e.printStackTrace();
		}
	}

}
