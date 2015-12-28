package reseau;

import java.nio.ByteBuffer;


public class Demande {

	private ByteBuffer tailleDemande; //=1+tailleChemin+5=6+c.lenght
	private ByteBuffer option;
	private ByteBuffer tailleContenu;
	private ByteBuffer chemin;
	
	Demande(Option opt, int tailleCont, String c){
		
		tailleDemande= ByteBuffer.allocate(2);
		tailleDemande.put((byte) ((c.length()+6) / 128));
		tailleDemande.put((byte) ((c.length()+6) %128));
		
		option= ByteBuffer.allocate(1);
		option.put(opt.toByte());
		
		tailleContenu= ByteBuffer.allocate(5);
		tailleContenu.put((byte) (tailleCont / (128*128*128*128)));
		tailleContenu.put((byte) ((tailleCont / (128*128*128))%128));
		tailleContenu.put((byte) ((tailleCont / (128*128))%128));
		tailleContenu.put((byte) ((tailleCont / (128))%128));
		tailleContenu.put((byte) (tailleCont % 128));
		
		chemin= ByteBuffer.allocate(c.length());
		for (int i = 0; i < c.length(); i++) {
			chemin.put((byte) c.charAt(i));
		}		

		rewind();
	}
	
	
	Demande(ByteBuffer b){
		b.rewind();
		
		tailleDemande=ByteBuffer.allocate(2);
		byte t1=(byte) (b.capacity()/128);
		byte t2=(byte) (b.capacity()%128);
		tailleDemande.put(t1);
		tailleDemande.put(t2);
		
		option=ByteBuffer.allocate(1);
		option.put(b.get());
		
		tailleContenu=ByteBuffer.allocate(5);
		tailleContenu.put(b.get());
		tailleContenu.put(b.get());
		tailleContenu.put(b.get());
		tailleContenu.put(b.get());
		tailleContenu.put(b.get());

		int tailleChe=t1*128+t2 - 6;
		chemin=ByteBuffer.allocate(tailleChe);
		for (int i = 0; i < tailleChe; i++) {
			chemin.put(b.get());
		}
		
		rewind();
	}

	
	protected ByteBuffer toByteBuffer(){
		rewind();
		ByteBuffer b=ByteBuffer.allocate(tailleDemande.capacity()+option.capacity()+tailleContenu.capacity()+chemin.capacity());
		b.put(tailleDemande);
		b.put(option);
		b.put(tailleContenu);
		b.put(chemin);

		return b;
	}
	
	protected void rewind(){
		tailleDemande.rewind();
		option.rewind();
		tailleContenu.rewind();		
		chemin.rewind();
	}
	
	protected String getTitle(){
		rewind();
		int longueurTitle=128*tailleDemande.get()+tailleDemande.get()-6;
		String title="";
		for(int i=0; i<longueurTitle; i++){
			title+=(char) chemin.get();
		}
		rewind();
		return title;
	}
	
	protected Option getOption(){
		rewind();
		Option o=Option.toOption(option.get());
		rewind();
		return o;
	}	
	
	protected int getTailleContenu(){
		rewind();
		int t= tailleContenu.get()*128*128*128*128 + tailleContenu.get()*128*128*128 + tailleContenu.get()*128*128 + tailleContenu.get()*128 + tailleContenu.get();
		rewind();
		return t;
	}
	
	protected void print(){
		rewind();
		System.out.print("Option : " +getOption() +" - ");
		System.out.print("Titre : " +getTitle()+" - ");
		System.out.println("TailleContenu : " +getTailleContenu());
		rewind();
	}
}
