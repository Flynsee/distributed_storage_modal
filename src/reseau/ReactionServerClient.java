package reseau;
import java.io.IOException;
import java.nio.channels.SocketChannel;

abstract class ReactionServerClient {
	
	protected Emetteur emetteur;
	protected Recepteur recepteur;
	protected String password;
	protected Server server;
	protected SocketChannel socket;

	ReactionServerClient(SocketChannel c, Server s) throws IOException {
		emetteur=new Emetteur(c);
		recepteur=new Recepteur(c);
		server=s;
		socket=c;
	}

	protected void deconnecter() throws IOException{
		socket.finishConnect();
	}
	
	protected boolean verifierPassword(Demande demande) throws IOException {
		if (demande.getOption() == Option.PASSWORD_UTILISATEUR
				&& demande.getTitle().equals(server.passwordUtilisateur)) {
			emetteur.sendDemande(Option.CONNEXION_ACCEPTEE, 0, "");
			return true;
		}
		else if (demande.getOption() == Option.PASSWORD_STOCKAGE
				&& demande.getTitle().equals(server.passwordStockage)) {
			emetteur.sendDemande(Option.CONNEXION_ACCEPTEE, 0, server.passwordUtilisateur);
			return true;
		}
		else {
			emetteur.sendDemande(Option.CONNEXION_REFUSEE, 0, "");
			return false;
		}
	}
	
	
}
