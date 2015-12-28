package interfaceGraphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;

import reseau.Informations;

public class FenetreDemanderAdressePortail extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFormattedTextField demanderAdressePortail1 = new JFormattedTextField();
	private JFormattedTextField demanderAdressePortail2 = new JFormattedTextField();
	private JFormattedTextField demanderAdressePortail3 = new JFormattedTextField();
	private JFormattedTextField demanderAdressePortail4 = new JFormattedTextField();
	private JButton valider=new JButton("Valider");
	private JButton annuler=new JButton("Annuler");
	private JPanel panneau=new JPanel();

	public FenetreDemanderAdressePortail(final boolean isStockage) {
		
		this.setTitle("Veuillez entrer l'adresse du portail");
	    this.setSize(350, 65);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
		
		int i1 = Informations.adresseLocale.indexOf(".", 0);
		int i2 = Informations.adresseLocale.indexOf(".", i1 + 1);
		int i3 = Informations.adresseLocale.indexOf(".", i2 + 1);
		
		demanderAdressePortail1.setText(Informations.adresseLocale.substring(0,i1));
		demanderAdressePortail2.setText(Informations.adresseLocale.substring(i1 + 1, i2));
		demanderAdressePortail3.setText(Informations.adresseLocale.substring(i2 + 1, i3));
		demanderAdressePortail4.setText(Informations.adresseLocale.substring(i3 + 1));
		
		demanderAdressePortail1.setColumns(3);
		demanderAdressePortail2.setColumns(3);
		demanderAdressePortail3.setColumns(3);
		demanderAdressePortail4.setColumns(3);
		
		panneau.add(demanderAdressePortail1);
		panneau.add(demanderAdressePortail2);
		panneau.add(demanderAdressePortail3);
		panneau.add(demanderAdressePortail4);
	    panneau.add(valider);
	    panneau.add(annuler);
	    
	    ActionListener clicAnnuler=new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FenetreAccueil();
				dispose();
			}
		};
		ActionListener clicValider=new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String adresse=demanderAdressePortail1.getText()+"."+demanderAdressePortail2.getText()+"."+demanderAdressePortail3.getText()+"."+demanderAdressePortail4.getText();
				new FenetreDemanderPassword(isStockage, adresse);
				dispose();
			}
		};
		valider.addActionListener(clicValider);
	    annuler.addActionListener(clicAnnuler);
		
		this.setContentPane(panneau);
		this.setVisible(true);
	}

}
