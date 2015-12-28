package interfaceGraphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;

import reseau.ClientVerificationPassword;
import reseau.Option;

public class FenetreDemanderPassword extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFormattedTextField demanderPassword = new JFormattedTextField();
	private JButton valider = new JButton("Valider");
	private JButton annuler = new JButton("Annuler");
	private JPanel panneau = new JPanel();

	public FenetreDemanderPassword(final boolean isStockage,
			final String adresse) {

		if (isStockage) {
			this.setTitle("Veuillez entrer le mot de passe Stockage");
		} else {
			this.setTitle("Veuillez entrer le mot de passe Utilisateur");
		}
		this.setSize(350, 65);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		demanderPassword.setColumns(8);
		;

		panneau.add(demanderPassword);

		panneau.add(valider);
		panneau.add(annuler);

		ActionListener clicAnnuler = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FenetreAccueil();
				dispose();
			}
		};
		ActionListener clicValider = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String password = demanderPassword.getText();
					if (isStockage) {
						ClientVerificationPassword cli = new ClientVerificationPassword(
								Option.PASSWORD_STOCKAGE, password);
						Thread utilisateur = new Thread(cli);
						utilisateur.start();
						while (utilisateur.isAlive()) {
						}
						if (cli.getIsPasswordCorrect()) {
							new FenetreDemanderCheminStockage(adresse,
									password, cli.getPasswordAppris());
							dispose();
						}
					} else if (!isStockage) {
						ClientVerificationPassword cli = new ClientVerificationPassword(
								Option.PASSWORD_UTILISATEUR, password);
						Thread utilisateur = new Thread(cli);
						utilisateur.start();
						while (utilisateur.isAlive()) {
						}
						if (cli.getIsPasswordCorrect()) {
							new FenetreUtilisateur(adresse, password);
							dispose();
						}
					}
				} catch (IOException e) {
				}

			}
		};

		valider.addActionListener(clicValider);
		annuler.addActionListener(clicAnnuler);

		this.setContentPane(panneau);
		this.setVisible(true);
	}
}
