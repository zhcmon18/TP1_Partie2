/*partie Yousef*/
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GestionCommandes {

	//Les attributs.
	private List<Client> clients;
	private List<String> listClients;
	private List<String> listPlats;
	private List<String> listCommandes;
	private String nomFichier;
	private Path chemin;
	BufferedReader ficLecture;

	//Le constructeur.
	public GestionCommandes(String nomFichier) throws Exception {

		listClients = new ArrayList<>();
		listPlats = new ArrayList<>();
		listCommandes = new ArrayList<>();
		clients =  new ArrayList<>();
		
		this.nomFichier = nomFichier;

		this.ficLecture = ouvrirFichier(this.nomFichier);

		this.assignerTableau();
		
	}

	//Permet de lire le fichier texte.
	private BufferedReader ouvrirFichier(String nomFichier) throws IOException {

		chemin = Paths.get(nomFichier).toAbsolutePath();
		BufferedReader ficLecture = Files.newBufferedReader(chemin, Charset.defaultCharset());
		return ficLecture;
	}	
}
