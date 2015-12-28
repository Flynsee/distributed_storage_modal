package reseau;

import java.io.IOException;

public class ClientVerificationPassword extends Client implements Runnable {
	private Option option;
	protected boolean isPasswordCorrect=false;
	protected String passwordAppris=null;

	public ClientVerificationPassword(Option opt, String mdp) throws IOException {
		super(opt, mdp);
		option = opt;
	}


	public void run() {
		try {
			System.out.println("Ouverture : ClientVerificationPassword");
			if (option == Option.PASSWORD_UTILISATEUR){
				open();
				isPasswordCorrect=connecter(Informations.adressePortail, Informations.portPortail, option)!=null;
				emetteur.sendDemande(Option.CONNEXION_ACCEPTEE, 0, "");
				fermer();
			}  else if(option == Option.PASSWORD_STOCKAGE){
				open();
				passwordAppris=connecter(Informations.adressePortail, Informations.portPortail, option);
				isPasswordCorrect=passwordAppris!=null;
				emetteur.sendDemande(Option.CONNEXION_ACCEPTEE, 0, "");
			}
		} catch (IOException e) {
		}
		System.out.println("Fermeture : ClientVerificationPassword");

	}

	
	public boolean getIsPasswordCorrect(){
		return isPasswordCorrect;
	}


	public String getPasswordAppris() {
		return passwordAppris;
	}
	
}
