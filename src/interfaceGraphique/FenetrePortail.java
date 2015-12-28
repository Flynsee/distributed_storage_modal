package interfaceGraphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Inet4Address;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import reseau.Informations;
import reseau.ServerPortail;

public class FenetrePortail extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panneau = new JPanel();
	private JButton annuler = new JButton("Annuler");
	private JFormattedTextField afficherPasswordStockage = new JFormattedTextField();
	private JFormattedTextField afficherPasswordUtilisateur = new JFormattedTextField();
	private JLabel textePasswordStockage = new JLabel(
			"Mot de passe stockage : ");
	private JLabel textePasswordUtilisateur = new JLabel(
			"Mot de passe utilisateur : ");

	public FenetrePortail(String passwordStockage, String passwordUtilisateur)
			throws IOException {

		this.setTitle("Portail sur "
				+ Inet4Address.getLocalHost().getHostAddress());
		this.setSize(
				Math.max(passwordStockage.length(),
						passwordUtilisateur.length()) * 8 + 170, 115);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		Informations.updateInformations();

		afficherPasswordStockage.setText(passwordStockage);
		afficherPasswordUtilisateur.setText(passwordUtilisateur);
		// afficherPasswordStockage.setColumns(passwordStockage.length());
		// afficherPasswordUtilisateur.setColumns(passwordUtilisateur.length());
		afficherPasswordStockage.setEditable(false);
		afficherPasswordUtilisateur.setEditable(false);

		panneau.add(textePasswordStockage);
		panneau.add(afficherPasswordStockage);
		panneau.add(textePasswordUtilisateur);
		panneau.add(afficherPasswordUtilisateur);
		panneau.add(annuler);
		final Thread portail = new Thread(new ServerPortail(passwordStockage,
				passwordUtilisateur));
		portail.start();
		ActionListener clicAnnuler = new ActionListener() {
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
