package reseau;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Emetteur {
	private static int taillePaquet=10000;
	private SocketChannel socket;
	
	Emetteur(SocketChannel s){
		socket=s;
	}
	
	protected void sendDemande(Option option, int tailleContenu, String nomFichier) throws IOException{
		ByteBuffer b=new Demande(option, tailleContenu, nomFichier).toByteBuffer();
		b.flip();
		socket.write(b);
		System.out.println("Demande envoyée par "+socket);
		new Demande(option, tailleContenu, nomFichier).print();
	}
	
	
	protected void sendFichier(Option option, String nomFichier, String repertoire) throws IOException {		
		String chemin=repertoire+nomFichier;		
		FileChannel fichier = FileChannel.open(Paths.get(chemin));
		sendDemande(option,(int) fichier.size(), nomFichier);

		int nombrePaquets=(int) (fichier.size()/taillePaquet);
		ByteBuffer temp=ByteBuffer.allocate(taillePaquet);
		
		for(int i=0; i<nombrePaquets; i++){
			temp.clear();
			fichier.read(temp);
			temp.flip();
			socket.write(temp);
		} 		
		temp=ByteBuffer.allocate((int) (fichier.size()%taillePaquet));
		fichier.read(temp);
		temp.flip();
		socket.write(temp); 
		fichier.close();
	}

	protected void sendRedirection(InetSocketAddress adr) throws IOException {
		String adresse=adr.toString();
		sendDemande(Option.REDIRIGER, adresse.length(), "");

		ByteBuffer b = ByteBuffer.allocate(adresse.length());
		for (int i = 0; i < adresse.length(); i++) {
			b.put((byte) adresse.charAt(i));
		}	
		b.flip();
		socket.write(b);
	}

	
	protected void sendDemandeFichier(Option option, String s) throws IOException {
		sendDemande(option, 0, s);
	}
	

	protected void sendPrevenirNouveau(Option option, String s, InetSocketAddress adr) throws IOException {
		String adresse=adr.toString();
		sendDemande(option, adresse.length(), s);
		ByteBuffer b = ByteBuffer.allocate(adresse.length());
		for (int i = 0; i < adresse.length(); i++) {
			b.put((byte) adresse.charAt(i));
		}	
		b.flip();
		socket.write(b);
	}
	

	protected void sendListeFichier(ConcurrentLinkedQueue<String> listeFichiers) throws IOException {

		ConcurrentLinkedQueue<String> j=new ConcurrentLinkedQueue<String>(listeFichiers);
		int tailleTotale=0;
		while(!j.isEmpty()){
			tailleTotale+=j.poll().length()+2;
		}
		sendDemande(Option.DEMANDER_LISTE, tailleTotale, "");

		ByteBuffer contenu;
		ConcurrentLinkedQueue<String> i = new ConcurrentLinkedQueue<String>(listeFichiers);
		String temp;
		while(!i.isEmpty()){
			temp=i.poll();
			contenu=ByteBuffer.allocate(temp.length()+2);
			contenu.put((byte) (temp.length()/128));
			contenu.put((byte) (temp.length()%128));
			for (int k = 0; k < temp.length(); k++) {
				contenu.put((byte) temp.charAt(k));
			}
			contenu.flip();
			socket.write(contenu);
		}
	}
	
	
}
