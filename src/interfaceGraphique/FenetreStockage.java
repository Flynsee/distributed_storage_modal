package interfaceGraphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import reseau.Informations;
import reseau.ServerStockage;

public class FenetreStockage extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel panneau=new JPanel();
	private JButton annuler=new JButton("Annuler");
	private JLabel afficherRepertoire=new JLabel();
	
	public FenetreStockage(String adresse, String cheminStockage, String passwordStockage, String passwordUtilisateur) throws IOException {
		
		this.setTitle("Stockage connecté");
	    afficherRepertoire.setText(cheminStockage);
	    this.setSize(90+cheminStockage.length()*7, 65);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    
		Informations.updateInformations(adresse, cheminStockage);
	    panneau.add(afficherRepertoire);
	    panneau.add(annuler);
	    final Thread portail=new Thread(new ServerStockage(passwordStockage, passwordUtilisateur));
	    portail.start();
	    ActionListener clicAnnuler=new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FenetreAccueil();
				portail.interrupt();
				dispose();
			}
		};
	    annuler.addActionListener(clicAnnuler);
	    this.setContentPane(panneau);
		this.setVisible(true);
	}

}
