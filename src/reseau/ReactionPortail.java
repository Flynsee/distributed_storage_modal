package reseau;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ReactionPortail extends ReactionServerClient implements Runnable {

	ReactionPortail(SocketChannel c, ServerPortail serverPortail)
			throws IOException {
		super(c, serverPortail);
	}

	public void run() {

		System.out.println("Ouverture : ReactionPortail");

		try {
			if (verifierPassword(recepteur.readDemande())) {
				reactionMessage(recepteur.readDemande());
			}
			deconnecter();
		} catch (IOException e) {
		}

		System.out.println("Fermeture : ReactionPortail");

	}

	private void reactionMessage(Demande demande) throws IOException {
		switch (demande.getOption()) {
		case DEMANDER_FICHIER:
			InetSocketAddress adresseStockage = null;
			if (ServerPortail.mapFichiers.contains(demande.getTitle())) {
				adresseStockage = ServerPortail.mapFichiers.get(demande
						.getTitle());
			} else {
				ConcurrentLinkedQueue<InetSocketAddress> listeTemp = new ConcurrentLinkedQueue<InetSocketAddress>(
						ServerPortail.listeStockages);

				int rand = (int) (Math.random() * ServerPortail.listeStockages
						.size());
				System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+listeTemp + " "+ rand);
				for (int i = 0; i <= rand; i++) {
					adresseStockage = listeTemp.poll();
				}
			}
			emetteur.sendRedirection(adresseStockage);
			break;
		case NOUVEAU_STOCKAGE:
			InetSocketAddress adresse = recepteur.readAdresse(demande);
			ServerPortail.listeStockages.add(adresse);
			break;
		case NOUVEAU_FICHIER:
			InetSocketAddress adresse1 = recepteur.readAdresse(demande);
			ServerPortail.mapFichiers.put(demande.getTitle(), adresse1);
			ServerPortail.listeFichiers.add(demande.getTitle());
			break;
		case FICHIER_EFFACE:
			InetSocketAddress adresse2 = recepteur.readAdresse(demande);
			ServerPortail.mapFichiers.remove(demande.getTitle(), adresse2);
			ServerPortail.listeFichiers.remove(demande.getTitle());
			break;
		case DEMANDER_LISTE:
			emetteur.sendListeFichier(ServerPortail.listeFichiers);
			break;
		case CONNEXION_ACCEPTEE:
			break;
		default:
			break;
		}
	}

	

}
