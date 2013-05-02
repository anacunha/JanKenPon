package jankenpon;

import java.util.Random;

/**
 * 
 * @author Ana Luiza Cunha
 *
 */
public enum Jogada {
	PEDRA, PAPEL, TESOURA;
	
	private final static Jogada[] VALUES = values();
	private static final int SIZE = VALUES.length;
	private static final Random RANDOM = new Random();
	
	private static Jogada randomJogada() {
		return VALUES[RANDOM.nextInt(SIZE)];
	}
}
