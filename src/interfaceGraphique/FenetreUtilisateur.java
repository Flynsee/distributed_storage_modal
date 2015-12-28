package interfaceGraphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FenetreUtilisateur extends JFrame{

	
	private static final long serialVersionUID = 1L;
	private JPanel panneau=new JPanel();
	private JButton upload=new JButton("Upload");
	private JButton download=new JButton("Download");
	private JButton supprimer=new JButton("Supprimer");
	private JButton annuler=new JButton("Annuler");

	
	FenetreUtilisateur(final String adresse, final String password){
		this.setTitle("Veuillez choisir une action");
	    this.setSize(380, 65);
	    this.setResizable(false);  	   
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    
	    ActionListener clicAnnuler=new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FenetreAccueil();
				dispose();
			}
		};
		ActionListener clicUpload=new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new FenetreUtilisateurUpload(adresse, password);
					dispose();
				} catch (IOException e) {
				}
			}
		};
		ActionListener clicDownload=new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new FenetreUtilisateurDownload(adresse, password);
					dispose();
				} catch (IOException e) {
				}
			}
		};
		ActionListener clicSupprimer=new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new FenetreUtilisateurSupprimer(adresse, password);
					dispose();
				} catch (IOException e) {
				}
			}
		};

		download.addActionListener(clicDownload);
		upload.addActionListener(clicUpload);
	    supprimer.addActionListener(clicSupprimer);
	    annuler.addActionListener(clicAnnuler);
	    
	    panneau.add(upload);
	    panneau.add(download);
	    panneau.add(supprimer);	    
	    panneau.add(annuler);

	    panneau.setVisible(true);
	    this.setContentPane(panneau);
	    this.setVisible(true);
		
	}

}
