package interfaceGraphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import reseau.ClientUtilisateur;
import reseau.Option;

public class FenetreUtilisateurDownload extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JPanel panneau=new JPanel();
	private JLabel demanderNomFichier = new JLabel();
	private JComboBox<String> menuDeroulant=new JComboBox<String>();
	private JButton download=new JButton("Download");
	private JButton refresh=new JButton("Râfraichir");
	private JFileChooser browser=new JFileChooser();

	FenetreUtilisateurDownload(final String adresse, final String password) throws IOException {
		this.setTitle("Veuillez choisir le fichier à télécharger et le répertoire de destination");
	    this.setSize(600, 390);	    
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    demanderNomFichier.setText("Choisissez un fichier :");  
	   
	    ClientUtilisateur client=new ClientUtilisateur(Option.DEMANDER_LISTE, password);
	    Thread utilisateur=new Thread(client);
	    utilisateur.start();
	    
	    while(utilisateur.isAlive()){
	    	
	    }
	    LinkedList<String> l=new LinkedList<String>(client.getListeFichiers());
	    
	    menuDeroulant.removeAllItems();
	    while(!l.isEmpty()){
	        menuDeroulant.addItem(l.poll());
	    }
	    ActionListener selectText=new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				demanderNomFichier.setText((String) menuDeroulant.getSelectedItem());
			}
		};
		menuDeroulant.addActionListener(selectText);
	    
	   
		ActionListener clicRefresh=new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
						new FenetreUtilisateurDownload(adresse, password);
						dispose();
				} catch (IOException e) {
				}
			}
		};
		ActionListener clicDownload=new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(demanderNomFichier.getText()=="Choisissez un fichier :"){
						return;
					}
					new FenetreTelechargementEnCours(adresse, Option.DOWNLOAD,demanderNomFichier.getText(),
							browser.getCurrentDirectory().toString()+File.separator, password);
					dispose();
				} catch (IOException e) {
				}
			}
		};
		
		 ActionListener actionBrowser=new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getActionCommand()=="CancelSelection"){
					new FenetreUtilisateur(adresse, password);
					dispose();
				}
			}
		};
		download.addActionListener(clicDownload);
		refresh.addActionListener(clicRefresh);
	    browser.addActionListener(actionBrowser);
	    
	    browser.setApproveButtonText("Choisir destination");
	    browser.setApproveButtonToolTipText("Choisissez le répertoire de destination");

	    
	    panneau.add(demanderNomFichier);
	    panneau.add(menuDeroulant);
	    panneau.add(download);
	    panneau.add(refresh);	   
	    panneau.add(browser);

	    panneau.setVisible(true);
	    this.setContentPane(panneau);
	    this.setVisible(true);
		
	}

}
