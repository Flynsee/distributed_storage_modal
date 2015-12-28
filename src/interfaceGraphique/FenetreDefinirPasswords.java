package interfaceGraphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class FenetreDefinirPasswords extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFormattedTextField demanderPasswordStockage = new JFormattedTextField();
	private JFormattedTextField demanderPasswordUtilisateur = new JFormattedTextField();
	private JButton valider = new JButton("Valider");
	private JButton annuler = new JButton("Annuler");
	private JPanel panneau = new JPanel();
	private JLabel textePasswordStockage   =new JLabel(" Mot de passe stockage : ");
	private JLabel textePasswordUtilisateur=new JLabel("Mot de passe utilisateur : ");


	public FenetreDefinirPasswords() {

		this.setTitle("Veuillez définir les mots de passe");
		this.setSize(300, 115);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		demanderPasswordStockage.setColumns(8);
		demanderPasswordUtilisateur.setColumns(8);

		panneau.add(textePasswordStockage);
		panneau.add(demanderPasswordStockage);
		panneau.add(textePasswordUtilisateur);
		panneau.add(demanderPasswordUtilisateur);

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
				String passwordUtilisateur = demanderPasswordUtilisateur.getText();
				String passwordStockage = demanderPasswordStockage.getText();
				try {
					new FenetrePortail(passwordStockage, passwordUtilisateur);
					dispose();
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
