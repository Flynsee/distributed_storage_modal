package reseau;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ClientStockage extends Client implements Runnable {

	private String nouveauFichier;
	private InetSocketAddress adresseServerStockage;

	public ClientStockage(Option opt, String nouv, InetSocketAddress adresse, String mdp) throws IOException {
		super(opt, mdp);
		nouveauFichier = nouv;
		adresseServerStockage = adresse;
	}

	public void run() {

		// ce client s'ouvre pour actualiser la liste des fichiers sur le
		// portail
		System.out.println("Ouverture : ClientStockage");
		try {
			open();
			if(connecter(Informations.adressePortail, Informations.portPortail, Option.PASSWORD_STOCKAGE)==null){
				fermer();
				return;
			}

			if (option == Option.NOUVEAU_FICHIER) {
				emetteur.sendPrevenirNouveau(option, nouveauFichier,
						adresseServerStockage);
			} else if (option == Option.NOUVEAU_STOCKAGE) {
				emetteur.sendPrevenirNouveau(option, "", adresseServerStockage);
			} else if (option == Option.FICHIER_EFFACE) {
				emetteur.sendPrevenirNouveau(option, nouveauFichier,
						adresseServerStockage);
			}

			fermer();
		} catch (IOException e) {
		}
		System.out.println("Fermeture : ClientStockage");
	}

}
