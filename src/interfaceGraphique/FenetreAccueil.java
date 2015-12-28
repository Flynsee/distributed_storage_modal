package interfaceGraphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import reseau.Informations;

public class FenetreAccueil extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel panneau = new JPanel();
	private JButton portail = new JButton("Portail");
	private JButton stockage = new JButton("Stockage");
	private JButton utilisateur = new JButton("Utilisateur");

	public FenetreAccueil() {
		this.setTitle("Accueil");
	    this.setSize(290, 65);
	    this.setResizable(false);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    try {
			Informations.updateInformations();
		} catch (UnknownHostException e1) {
		}
		panneau.add(portail);
		panneau.add(stockage);
		panneau.add(utilisateur);
		ActionListener clicPortail = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FenetreDefinirPasswords();
				dispose();
			}
		};
		ActionListener clicStockage = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FenetreDemanderAdressePortail(true);
				dispose();
			}
		};
		ActionListener clicUtilisateur = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FenetreDemanderAdressePortail(false);
				dispose();
			}
		};
		portail.addActionListener(clicPortail);
		stockage.addActionListener(clicStockage);
		utilisateur.addActionListener(clicUtilisateur);
	    this.setContentPane(panneau);
		this.setVisible(true);
	}
}
