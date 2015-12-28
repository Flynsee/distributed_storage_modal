package interfaceGraphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import reseau.ClientUtilisateur;
import reseau.Informations;
import reseau.Option;

public class FenetreTelechargementEnCours extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel panneau=new JPanel();
	private JButton annuler=new JButton("Annuler");	
	private JButton ok=new JButton("Ok");	

    
	public FenetreTelechargementEnCours(final String adresse, Option option, String nomFichier, String repertoireUtilisateur, final String password) throws IOException {
		
		this.setTitle("Téléchargement en cours");
	    this.setSize(300, 65);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    
		Informations.updateInformations(adresse);
		
	    ClientUtilisateur cli=new ClientUtilisateur(option, nomFichier, repertoireUtilisateur, password);
	    final Thread utilisateur=new Thread(cli);
	    utilisateur.start();
	    
	    ActionListener clicAnnuler=new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FenetreAccueil();
				utilisateur.interrupt();
				dispose();
			}
		};
		ActionListener clicOk=new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(utilisateur.isAlive()){
					return;
				}
				new FenetreUtilisateur(adresse, password);
				dispose();
			}
		};
	    ok.addActionListener(clicOk);
	    annuler.addActionListener(clicAnnuler);

	    panneau.add(annuler);
	    panneau.add(ok);

	    
	    this.setContentPane(panneau);
		this.setVisible(true);
		
	}


}
