package jankenpon;

import java.rmi.Naming;

/**
 * 
 * @author Ana Luiza Cunha
 *
 */
public class JanKenPonServer {
	
	public static void main(String[] args) {
		
		try {
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("RMI Registry ready.");
			
			Naming.rebind("JanKenPon", new JanKenPon());
			System.out.println("JanKenPonServer is ready.");
		}
		catch(Exception e) {
			System.out.println("JanKenPonServer has failed.");
			e.printStackTrace();
		}
	}

}
