package reseau;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class ServerStockage extends Server implements Runnable {
		
	public ServerStockage(String mdpSt,String mdpUt) throws IOException {
		super(Informations.adresseLocale, Informations.portStockage, mdpSt, mdpUt);
	}

	public void run() {
		try {
			open();
			System.out.println("Ouverture : ServerStockage");
			Thread clientStockageInitial = new Thread(new ClientStockage(
					Option.NOUVEAU_STOCKAGE, "", local, passwordStockage));
			clientStockageInitial.start();			

			for (;;) {
				System.out.println("ServerStockage disponible");
				attendre();
			}
			//fermer();
			//System.out.println("Fermeture : ServerStockage");
		} catch (IOException e) {
		}

	}

	private void attendre() throws IOException {
		SocketChannel client = this.attendreClient(); // attend un client
		Thread thread = new Thread(
				new ReactionStockageUtilisateur(client, this));
		// lance un nouveau Thread correspondant
		thread.start();
	}
}
