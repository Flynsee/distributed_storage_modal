package reseau;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.UnknownHostException;


public abstract class Informations {
	public static String adresseLocale;
	public static String adressePortail;
	public static int portPortail = 5858;
	public static String repertoireStockage;
	public static String repertoireUtilisateur;
	public static int portStockage = trouverPortLibreStockage();
	
	
	public static void updateInformations(String adressePorta) throws UnknownHostException{
		adressePortail=adressePorta;
		adresseLocale=Inet4Address.getLocalHost().getHostAddress();
		
	}
	
	public static void updateInformations() throws UnknownHostException{
		adressePortail=Inet4Address.getLocalHost().getHostAddress();
		adresseLocale=Inet4Address.getLocalHost().getHostAddress();
	}
	
	public static void updateInformations(String adressePorta, String repertoireSto) throws UnknownHostException{
		adressePortail=adressePorta;
		adresseLocale=Inet4Address.getLocalHost().getHostAddress();
		repertoireStockage=repertoireSto;
	}
	
	public static int trouverPortLibreStockage(){
		try {
			ServerSocket server = new ServerSocket(0); 
			int portLibre = server.getLocalPort();
			server.close();
			return portLibre;
		} catch (IOException e) {
		}
		return 5959;
	}
}
