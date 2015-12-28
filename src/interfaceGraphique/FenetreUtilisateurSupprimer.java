package interfaceGraphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import reseau.ClientUtilisateur;
import reseau.Option;

public class FenetreUtilisateurSupprimer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panneau = new JPanel();
	private JLabel demanderNomFichier = new JLabel();
	private JComboBox<String> menuDeroulant = new JComboBox<String>();
	private JButton supprimer = new JButton("Supprimer");
	private JButton annuler = new JButton("Annuler");
	private JButton refresh = new JButton("Râfraichir");

	FenetreUtilisateurSupprimer(final String adresse, final String password)
			throws IOException {
		this.setTitle("Veuillez choisir le fichier à supprimer");
		this.setSize(500, 65);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		demanderNomFichier.setText("Choisissez un fichier :");

		ClientUtilisateur client = new ClientUtilisateur(Option.DEMANDER_LISTE,
				password);
		Thread utilisateur = new Thread(client);
		utilisateur.start();

		while (utilisateur.isAlive()) {

		}
		LinkedList<String> l = new LinkedList<String>(client.getListeFichiers());

		menuDeroulant.removeAllItems();
		while (!l.isEmpty()) {
			menuDeroulant.addItem(l.poll());
		}
		ActionListener selectText = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				demanderNomFichier.setText((String) menuDeroulant
						.getSelectedItem());
			}
		};
		menuDeroulant.addActionListener(selectText);

		ActionListener clicRefresh = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new FenetreUtilisateurSupprimer(adresse, password);
					dispose();
				} catch (IOException e) {
				}
			}
		};
		ActionListener clicSupprimer = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (demanderNomFichier.getText() == "Choisissez un fichier :") {
					return;
				}
				try {
					new FenetreTelechargementEnCours(adresse, Option.DELETE,
							demanderNomFichier.getText(), "", password);
					dispose();
				} catch (IOException e) {
				}
			}
		};
		ActionListener clicAnnuler = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FenetreUtilisateur(adresse, password);
				dispose();
			}
		};
		supprimer.addActionListener(clicSupprimer);
		refresh.addActionListener(clicRefresh);
		annuler.addActionListener(clicAnnuler);

		panneau.add(demanderNomFichier);
		panneau.add(menuDeroulant);
		panneau.add(supprimer);
		panneau.add(refresh);
		panneau.add(annuler);

		panneau.setVisible(true);
		this.setContentPane(panneau);
		this.setVisible(true);

	}

}
