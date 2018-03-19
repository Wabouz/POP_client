import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

//Code BDYB

public class Client {
	Socket connexion;
	PrintWriter sortie;
	BufferedReader entree;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			new Client();
		} catch (Exception e) {
			System.err.println("Une erreur est survenue : " + e);
		}
	}
	Client() throws IOException {
		String ServerName = new String();
		ServerName = "si-linux.supelec.fr";
		int ServerPort = 1110;
		byte msg[];
		int s;
		OutputStream sout = null;
		InputStream sin = null;
		try {
			connexion = new Socket(ServerName, ServerPort);
		} catch (UnknownHostException e) {
			System.err.println("Serveur introuvable, vérifiez que vous avez écrit son nom correctement.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("La connexion au serveur : " + ServerName + "-" + ServerPort + " has failed !");
			System.exit(1);
		}
		try {
			sin = this.connexion.getInputStream();
			sout = this.connexion.getOutputStream();
			sortie = new PrintWriter(sout, true);
			entree=  new BufferedReader(new InputStreamReader(sin));
			System.out.println("Input and Output streams get on client");
		} catch (IOException e) {
			System.err.println("Socket streams impossible to get");
			System.exit(1);
		}
		
		//        AUTHENTIFICATION
		System.out.println(entree.readLine()); //+OK SI-POP server ready.
		sortie.println("USER Ernesto"); 
		System.out.println(entree.readLine()); //+OK Username is valid, go ahead with password.
		sortie.println("PASS otsenrE");
		System.out.println(entree.readLine()); //+OK Mailbox locked and ready.
		
		
		int MsgSize = 10;
		sortie.println("LIST");
		String LIST = entree.readLine();
		//System.out.println(LIST); //Affiche la premiere ligne du LIST
		int NbMsg = Integer.valueOf(LIST.split(" ")[1]);
		System.out.println(NbMsg + " messages");
		// Boucle pour afficher le nombre et la taille de chaque message
		for (int i = 1; i <= NbMsg; i++) {
			String RETR = entree.readLine();
			//System.out.println(RETR);
		}
		entree.readLine(); //C'est un point
		
		
		//Afficher le sujet de chaque message
		for (int i = 1; i <= NbMsg; i++) {
			System.out.println("Message "+i+" :");
			sortie.println("TOP "+i+" 0");
			System.out.println(entree.readLine()); //+OK size
			String Subject = entree.readLine(); //Subject:
			entree.readLine(); //To:
			entree.readLine(); //From:
			entree.readLine(); //Date:
			entree.readLine(); //Ligne vide
			entree.readLine(); //.
			System.out.println(Subject);
		}
		
		
		sortie.println("QUIT");
		System.out.println(entree.readLine());
	}
}
