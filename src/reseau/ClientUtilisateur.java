package reseau;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.LinkedList;

public class ClientUtilisateur extends Client implements Runnable {
	private String fichier;
	private String repertoire;
	private LinkedList<String> listeFichiers = new LinkedList<String>();


	public ClientUtilisateur(Option opt, String mdp) throws IOException {
		super(opt, mdp);
		fichier = "";
		repertoire = Informations.repertoireUtilisateur;
	}

	public ClientUtilisateur(Option opt, String s, String mdp)
			throws IOException {
		super(opt, mdp);
		fichier = s;
		repertoire = Informations.repertoireUtilisateur;
	}

	public ClientUtilisateur(Option opt, String s, String rep, String mdp)
			throws IOException {
		super(opt, mdp);
		fichier = s;
		repertoire = rep;
	}

	public void run() {
		try {
			System.out.println("Ouverture : ClientUtilisateur");
			if (option == Option.DEMANDER_LISTE) {
				demanderListeFichiers();
			} else if (option == Option.UPDATE || option == Option.DOWNLOAD
					|| option == Option.UPLOAD || option == Option.DELETE) {
				envoyerRequete(option, fichier);
			}
		} catch (IOException e) {
		}
		System.out.println("Fermeture : ClientUtilisateur");

	}

	private void demanderListeFichiers() throws IOException {
		open();
		if(connecter(Informations.adressePortail, Informations.portPortail, Option.PASSWORD_UTILISATEUR)==null){
			return;
		}
		emetteur.sendDemande(Option.DEMANDER_LISTE, 0, "");
		this.listeFichiers = new LinkedList<String>(
				recepteur.readListeFichiers(recepteur.readDemande()));
		fermer();
	}

	private void envoyerRequete(Option option, String nomFichier)
			throws IOException {
		open();
		if(connecter(Informations.adressePortail, Informations.portPortail, Option.PASSWORD_UTILISATEUR)==null){
			return;
		}
		emetteur.sendDemandeFichier(Option.DEMANDER_FICHIER, nomFichier);
		InetSocketAddress adresseStockage = recepteur.readAdresse(recepteur
				.readDemande());
		fermer();
		open();
		if(connecter(adresseStockage, Option.PASSWORD_UTILISATEUR)==null){
			return;
		}
		switch (option) {
		case DOWNLOAD:
			emetteur.sendDemandeFichier(option, nomFichier);
			recepteur.readFichier(recepteur.readDemande(), repertoire);
			break;
		case UPDATE:
			emetteur.sendFichier(option, nomFichier, repertoire);
			break;
		case UPLOAD:
			emetteur.sendFichier(option, nomFichier, repertoire);
			break;
		case DELETE:
			emetteur.sendDemandeFichier(option, nomFichier);
		default:
			break;
		}
		fermer();
	}

	public LinkedList<String> getListeFichiers() {
		return listeFichiers;
	}
	
}
