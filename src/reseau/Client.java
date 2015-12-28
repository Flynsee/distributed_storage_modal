package reseau;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

abstract class Client implements Runnable{

	private InetSocketAddress local;
	private SocketChannel clientSocket;
	protected Emetteur emetteur;
	protected Recepteur recepteur;
	private String password;
	protected Option option;

	
	Client(Option opt, String mdp) throws IOException {
	password=mdp;
	option=opt;
	}

	protected void open() throws IOException {
		clientSocket = SocketChannel.open();
		emetteur=new Emetteur(clientSocket);
		recepteur=new Recepteur(clientSocket);
		local = new InetSocketAddress(Informations.adresseLocale, 0);
		clientSocket.bind(local);
	}

	protected void fermer() throws IOException {
		clientSocket.close();
	}

	protected String connecter(String adresse, int port, Option option) throws IOException {
		InetSocketAddress serverAdresse = new InetSocketAddress(adresse, port);
		return connecter(serverAdresse, option);
	}

	protected String connecter(SocketAddress serverAdresse, Option option) throws IOException {
		clientSocket.connect(serverAdresse);
		emetteur.sendDemande(option, 0, password);
		Demande demande=recepteur.readDemande();
		if (demande.getOption() == Option.CONNEXION_ACCEPTEE){
			return demande.getTitle();
		}
		return null;
	}
	
	

}
