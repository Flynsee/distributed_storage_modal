package interfaceGraphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FenetreDemanderCheminStockage extends JFrame{

	private static final long serialVersionUID = 1L;
	private JFileChooser browser=new JFileChooser();
	private JButton valider=new JButton("Choisir ce répertoire");
	private JPanel panneau=new JPanel();

	public FenetreDemanderCheminStockage(final String adresse, final String passwordStockage, final String passwordUtilisateur) {
		
		this.setTitle("Veuillez choisir le répertoire de stockage");
	    this.setSize(600, 400);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
		
	    panneau.add(browser);
	    panneau.add(valider);
	    
	    ActionListener actionBrowser=new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getActionCommand()=="CancelSelection"){
					new FenetreAccueil();
					dispose();
				}
			}
		};
		ActionListener clicValider=new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new FenetreStockage(adresse, browser.getCurrentDirectory().toString()+File.separator, passwordStockage, passwordUtilisateur);
					dispose();
				} catch (IOException e) {
				}
			}
		};
	
		browser.addActionListener(actionBrowser);
		valider.addActionListener(clicValider);
		this.setContentPane(panneau);
		this.setVisible(true);
	}

}
