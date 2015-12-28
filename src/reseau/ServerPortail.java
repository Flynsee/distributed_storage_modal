package reseau;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerPortail extends Server implements Runnable {

	protected static ConcurrentHashMap<String, InetSocketAddress> mapFichiers= new ConcurrentHashMap<String, InetSocketAddress>();
	protected static ConcurrentLinkedQueue<InetSocketAddress> listeStockages = new ConcurrentLinkedQueue<InetSocketAddress>();
	protected static ConcurrentLinkedQueue<String> listeFichiers = new ConcurrentLinkedQueue<String>();

	public ServerPortail(String passwordSt, String passwordUt) throws IOException {
		super(Informations.adresseLocale, Informations.portPortail, passwordSt, passwordUt);
	}

	public void run() {
		try {
			open();
			System.out.println("Ouverture : ServerPortail");
			for (;;) {
				System.out.println("ServerPortail disponible");
				attendre();
			}
			//fermer();
			//System.out.println("Fermeture : ServerPortail");
		} catch (IOException e) {
		}

	}
	
	private void attendre() throws IOException {
		SocketChannel client = this.attendreClient(); // attend un client
		Thread thread = new Thread(new ReactionPortail(client, this)); // lance un nouveau Thread correspondant
		thread.start();

	}

}
