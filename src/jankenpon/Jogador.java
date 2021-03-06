package jankenpon;

/**
 * Representa um Jogador de JanKenPon, identificado por seu nome.
 * 
 * @author Ana Luiza Cunha
 *
 */
public class Jogador {
	
	private String nome;
	
	public Jogador(String nome) {
		super();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
	public String toString() {
		return nome;
	}
}
