package interfaceGraphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import reseau.Option;

public class FenetreUtilisateurUpload extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panneau = new JPanel();
	private JFileChooser browser = new JFileChooser();

	FenetreUtilisateurUpload(final String adresse, final String password)
			throws IOException {
		this.setTitle("Veuillez selectionner un fichier à mettre en ligne");
		this.setSize(600, 380);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		ActionListener actionBrowser = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getActionCommand() == "CancelSelection") {
					new FenetreUtilisateur(adresse, password);
					dispose();
				} else if (arg0.getActionCommand() == "ApproveSelection") {
					try {
						new FenetreTelechargementEnCours(adresse,
								Option.UPLOAD, browser.getSelectedFile()
										.getName(), browser
										.getCurrentDirectory().toString()
										+ File.separator, password);
						dispose();
					} catch (IOException e) {
					}
				}
			}
		};

		browser.addActionListener(actionBrowser);
		browser.setApproveButtonText("Upload");
		browser.setApproveButtonToolTipText("Met en ligne le fichier sélectionné");

		panneau.add(browser);
		panneau.setVisible(true);
		this.setContentPane(panneau);
		this.setVisible(true);

	}

}
