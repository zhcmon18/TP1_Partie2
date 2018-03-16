package main;

/*partie Youcef*/
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GestionCommandes {

	// Les attributs.
	private List<Client> clients;
	private List<String> listClients;
	private List<String> listPlats;
	private List<String> listCommandes;
	private String nomFichier;
	private Path chemin;
	BufferedReader ficLecture;

	// Le constructeur.
	public GestionCommandes(String nomFichier) throws Exception {

		listClients = new ArrayList<>();
		listPlats = new ArrayList<>();
		listCommandes = new ArrayList<>();
		clients = new ArrayList<>();

		this.nomFichier = nomFichier;

		this.ficLecture = ouvrirFichier(this.nomFichier);

		this.assignerTableau();

	}

	// Permet de lire le fichier texte.
	private BufferedReader ouvrirFichier(String nomFichier) throws IOException {

		chemin = Paths.get(nomFichier).toAbsolutePath();
		BufferedReader ficLecture = Files.newBufferedReader(chemin, Charset.defaultCharset());
		return ficLecture;
	}
	
	//Lit et définit dans quelle partie du fichier la lecture est rendue.
	private void assignerTableau() throws IOException {

		int listCourant = -1;
		boolean clientVu, platVu, commandeVu,
			fichierValide = true;

		clientVu = platVu = commandeVu = false;

		try {
			String ligne = "";
			while ((ligne = this.ficLecture.readLine()) != null) {
				if (ligne.equals("Fin")) {
					break;
					
				} else if (ligne.equals("Clients:")) {
					
					if (clientVu) {
						throw new IOException("Clients: apparait plus d'une fois.");
					}
					
					listCourant = 0;
					clientVu = true;
				
				} else if (ligne.equals("Plats:")) {
					
					if (platVu) {
						throw new IOException("Plat: apparait plus d'une fois.");
					}
					
					listCourant = 1;
					platVu = true;

				} else if (ligne.equals("Commandes:")) {
					
					if (commandeVu) {
						throw new IOException("Commande: apparait plus d'une fois.");
					}
					
					listCourant = 2;
					commandeVu = true;
	
				} else {
					assignerLigne(listCourant, ligne);
				}
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());	
			fichierValide = false;
		}

		if (fichierValide) {
			System.out.println("Bienvenue chez Barette!");
			System.out.println("Facture:");
			for (Client cli : clients) {
				cli.afficher();
			}
		}
	}

	// Distribue l’information et effectue la vérification selon la partie du
	// fichier.
	private void assignerLigne(int list, String ligne) throws IOException {

		switch (list) {
		case 0:

			if (this.listClients.contains(ligne)) {
				throw new IOException("client apparait deux fois");
			} else {
				listClients.add(ligne);
				this.clients.add(new Client(ligne));
			}

			break;
		case 1:
			listPlats.add(ligne);
			break;
		case 2:

			if (commandeValide(ligne)) {
				listCommandes.add(ligne);

				Client client = null;
				double prixPlat = 0;
				int nbPlat = Integer.parseInt(ligne.split(" ")[2]);

				for (Client c : clients) {
					if (c.getNom().equals(ligne.split(" ")[0])) {
						client = c;
					}
				}

				for (String plat : this.listPlats) {
					if (plat.split(" ")[0].equals(ligne.split(" ")[1])) {
						prixPlat = Double.parseDouble(plat.split(" ")[1]);
					}
				}

				client.setFacture(client.getFacture() + prixPlat * nbPlat);
			}

			break;
		default:
			System.out.println("L’indice de la liste inattendu.");
		}
	}

	// Vérifier la commande si elle contient le client et le plat existant.
	private boolean commandeValide(String commande) throws IOException {

		if (commande.split(" ").length != 3) {
			throw new IOException("Le fichier ne respecte pas le format demand\u00e9 !");
		}

		boolean clientValide, platValide;

		clientValide = platValide = false;

		clientValide = (listClients.contains(commande.split(" ")[0]));

		if (!clientValide) {
			throw new IOException("Le fichier ne respecte pas le format demand\u00e9 !");
		}

		for (String plat : listPlats) {
			if (plat.split(" ")[0].equals(commande.split(" ")[1])) {
				platValide = true;
				break;
			}
		}

		if (!platValide) {
			throw new IOException("Le fichier ne respecte pas le format demand\\u00e9 !");
		}

		for (char car : commande.split(" ")[2].toCharArray()) {
			if (!Character.isDigit(car)) {
				throw new IOException("Le fichier ne respecte pas le format demand\\u00e9 !");
			}
		}

		if (!platValide) {
			throw new IOException("Le fichier ne respecte pas le format demand\\u00e9 !");
		}

		return clientValide && platValide;
	}
}
