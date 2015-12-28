package reseau;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;


public class Recepteur{
	private static int taillePaquet=10000;
	private int avancement;
	private SocketChannel socket;
	
	Recepteur(SocketChannel s){
		socket=s;
	}
	
	protected Demande readDemande() throws IOException{
		ByteBuffer b=ByteBuffer.allocate(2);
		while(b.position()<2){
			socket.read(b);
		}
		b.flip();
		int tailleDemande=b.get()*128+b.get();
		ByteBuffer suiteDemande=ByteBuffer.allocate(tailleDemande);
		while(suiteDemande.position()<tailleDemande){
			socket.read(suiteDemande);
		}
		Demande demande=new Demande(suiteDemande);	
		System.out.println("Demande reçue sur "+socket);
		demande.print();
		return demande;
	}
	
	

	protected void readFichier(Demande demande, String repertoireDestination) throws IOException {	
		String chemin=null;
		int tailleFichier=demande.getTailleContenu();
		
		chemin = repertoireDestination+demande.getTitle();
		File p=new File(chemin);
		p.createNewFile();
		
		FileChannel fichier = FileChannel.open(Paths.get(chemin), StandardOpenOption.WRITE);
		
		ByteBuffer b;

		for(int i=0; i<tailleFichier/taillePaquet;i++){
			b=ByteBuffer.allocate(taillePaquet);
			while(b.position()<taillePaquet){
				socket.read(b);
			}
			b.flip();
			fichier.write(b);
			avancement=i*taillePaquet/ (tailleFichier/100);
		}
		b=ByteBuffer.allocate(tailleFichier%taillePaquet);
		while(b.position()<tailleFichier%taillePaquet){
			socket.read(b);
		}
		b.flip();
		fichier.write(b);
		System.out.println("Téléchargement complet");
		fichier.close();
	}
	
	
	

	protected InetSocketAddress readAdresse(Demande demande) throws IOException {		
		int tailleAdresse=demande.getTailleContenu();
		ByteBuffer contenu=ByteBuffer.allocate(tailleAdresse);
		while(contenu.position()<tailleAdresse){
			socket.read(contenu);
		}
		contenu.flip();
		
		while ((contenu.get()) != 47){
		}
		String adresse = "";
		char temp = (char) contenu.get();
		while (temp != ':') {
			adresse += temp;
			temp = (char) contenu.get();
		}
		String port = "";
		byte t = (contenu.get());
		while (contenu.hasRemaining() && t != 0) {
			port += (char) t;
			t = (contenu.get());
		}
		if (t != 0) {
			port += (char) t;
		}
		return new InetSocketAddress(adresse, Integer.parseInt(port));
	}
	
	


	public LinkedList<String> readListeFichiers(Demande demande) throws IOException {	
		LinkedList<String> liste=new LinkedList<String>();
		int position=0;
		int fin=demande.getTailleContenu();
		String nomFichier="";
		int tailleTit=0;
		ByteBuffer tailleTitle=ByteBuffer.allocate(2);
		ByteBuffer nomFich;
		while(position<fin){
			nomFichier="";
			tailleTitle.clear();
			while(tailleTitle.position()<2){
				socket.read(tailleTitle);
			}
			tailleTitle.flip();
			tailleTit=128*tailleTitle.get()+tailleTitle.get();
			nomFich=ByteBuffer.allocate(tailleTit);
			while(nomFich.position()<tailleTit){
				socket.read(nomFich);
			}
			nomFich.flip();
			for(int i=0; i<tailleTit; i++){
				nomFichier+=(char) nomFich.get();
			}
			position+=2+tailleTit;
			liste.add(nomFichier);
		}
		
		
		
		return liste;
	}
	
	public int getAvancement(){
		return avancement;
	}

	public void deleteFichier(String title, String repertoire) {
		File p=new File(repertoire+title);
		p.delete();
	}
		
	
}
