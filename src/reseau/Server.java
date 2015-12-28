package reseau;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


abstract class Server {
	
	protected String passwordUtilisateur;
	protected String passwordStockage;

	protected InetSocketAddress local;
	private ServerSocketChannel	serverSocket;
	private int port;
	private String adresse;
	
	
	Server(String a, int p, String mdpS, String mdpU) throws IOException{
		port=p;
		adresse=a;
		passwordUtilisateur=mdpU;
		passwordStockage=mdpS;
	}
	
	protected void open() throws IOException{
		serverSocket=ServerSocketChannel.open();// ouvre un channel
		local=new InetSocketAddress(adresse, port);//cree une socket sur un port dispo
		serverSocket.bind(local); //lie le channel et la socket
	}
	
	protected void fermer() throws IOException{
		serverSocket.close();
	}
	
	protected SocketChannel attendreClient() throws IOException{
		return serverSocket.accept(); //attend un client
	}
	
	
	
	
}	