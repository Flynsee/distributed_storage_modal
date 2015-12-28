package reseau;
import java.io.IOException;
import java.nio.channels.SocketChannel;

public class ReactionStockageUtilisateur extends ReactionServerClient implements
		Runnable {
	
	ReactionStockageUtilisateur(SocketChannel c, ServerStockage serverStockage)
			throws IOException {
		super(c, serverStockage);
	}

	public void run() {
		System.out.println("Ouverture : ReactionStockageUtilisateur");

		try {
			if (verifierPassword(recepteur.readDemande())) {
				reactionMessage(recepteur.readDemande());
			}

			deconnecter();

		} catch (IOException e) {
		}
		System.out.println("Fermeture : ReactionStockageUtilisateur");
	}

	private void reactionMessage(Demande demande) throws IOException {
		Option option = demande.getOption();
		switch (option) {
		case DOWNLOAD:
			emetteur.sendFichier(option, demande.getTitle(), Informations.repertoireStockage);
			break;
		case UPDATE:
			recepteur.readFichier(demande, Informations.repertoireStockage);
			break;
		case UPLOAD:
			recepteur.readFichier(demande, Informations.repertoireStockage);
			Thread prevenirNouvFich = new Thread(new ClientStockage(Option.NOUVEAU_FICHIER, demande.getTitle(), server.local, server.passwordStockage));
			prevenirNouvFich.start(); // nouveau Thread pour actualiser la liste des/ fichiers sur le Portail 
			break;
		case DELETE:
			recepteur.deleteFichier(demande.getTitle(), Informations.repertoireStockage);
			Thread prevenirDeletionFich = new Thread(new ClientStockage(Option.FICHIER_EFFACE, demande.getTitle(), server.local, server.passwordStockage));
			prevenirDeletionFich.start(); // nouveau Thread pour actualiser la liste des/ fichiers sur le Portail 
			break;
		case CONNEXION_ACCEPTEE:
			break;
		default:
			break;
		}
	}

}
