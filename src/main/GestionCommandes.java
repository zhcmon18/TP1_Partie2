package main;

import java.util.List;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GestionCommandes {

	/*Les attributs.*/
	private List<Client> clients;
	private List<String> listClients;
	private List<String> listPlats;
	private List<String> listCommandes;
	private List<String> listCommandesIncorrecte;
	private BufferedReader ficLecture;
	private PrintWriter ficEcriture;
	private String nomFichier, donnees;
	private int nbCmdValides;

	/*Le constructeur.*/
	public GestionCommandes(String nomFichier) throws Exception {
		this.nomFichier = nomFichier;
		
		listClients = new ArrayList<>();
		listPlats = new ArrayList<>();
		listCommandes = new ArrayList<>();
		listCommandesIncorrecte = new ArrayList<>();
		clients = new ArrayList<>();
		
	}

	/*Lit et définit dans quelle partie du fichier la lecture est rendue.*/
	private void assignerTableau() throws IOException  {

		int listCourant = -1;
		boolean clientVu, platVu, commandeVu = true;

		clientVu = platVu = commandeVu = false;

		donnees = "";
		
		String ligne = "";
		while ((ligne = this.ficLecture.readLine()) != null) {
			
			if (ligne == "Fin") {
				donnees += ligne;
			} else {
				donnees += ligne + "\n";
			}
						
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
	}

	/*Distribue l’information et effectue la vérification selon la partie du
	  fichier.*/
	private void assignerLigne(int list, String ligne) throws IOException {

		nbCmdValides = 0;
		
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
				nbCmdValides++;

				Client client = null;
				double prixPlat = 0;
				int nbPlat = Integer.parseInt(ligne.split(" ")[2]);

				for (Client c : clients) {
					if (c.getNom().equals(ligne.split(" ")[0])) {
						client = c;
					}
				}

				for (String plat : listPlats) {
					if (plat.split(" ")[0].equals(ligne.split(" ")[1])) {
						prixPlat = Double.parseDouble(plat.split(" ")[1]);
					}
				}

				double prixBrut = prixPlat * nbPlat;

				double total = calculerTotal(prixBrut);

				client.setFacture(client.getFacture() + total);
			}
			break;
		default:
			System.out.println("L’indice de la liste inattendu.");
		}
	}

	/*Vérifie la commande si elle contient le client, le plat, et la quantité valides.*/
	public boolean commandeValide(String commande) throws IOException {
		String messageErreur = null;
		
		boolean cmdValide = true;
		
		if (commande.split(" ").length != 3) {
			messageErreur = "la commande ne respecte pas le format demand\u00e9.";
			cmdValide = false;
		
		} else {			
			boolean clientValide, clientExiste, platValide, platExiste;

			clientValide = clientExiste = platValide = platExiste = false;

			String clientCmd = commande.split(" ")[0];
			String platCmd = commande.split(" ")[1];
			String quantiteCmd = commande.split(" ")[2];
			
			clientValide = clientCmd.matches("[a-zA-ZÀ-ÿ]+");
			clientExiste = (listClients.contains(clientCmd));
			platValide = platCmd.matches("[a-zA-ZÀ-ÿ_]+");
			
			for (String plat : listPlats) {
				if (plat.split(" ")[0].equals(platCmd)) {
					platExiste = true;
					break;
				}
			}
					
			if (!clientValide) {
				cmdValide = false;
				messageErreur = "le format du client " + clientCmd + " n'est pas valide.";
			
			} else {
				if (!clientExiste) {
					cmdValide = false;
					messageErreur = "le client " + clientCmd + " n'existe pas.";
				}
			}

			if (!platValide) {
				cmdValide = false;
				messageErreur = "le format du plat " + platCmd + " n'est pas valide.";
			
			} else {
				if (!platExiste) {
					cmdValide = false;
					messageErreur = "le plat " + platCmd + " n'existe pas.";
				}
			}

			char[] quantite = quantiteCmd.toCharArray();

			boolean quantZero = true;
			
			for (char car : quantite) {
				if (!Character.isDigit(car)) {
					cmdValide = false;
					messageErreur = "le format de la quantité " + quantiteCmd + " de la commande n'est pas valide.";
					break;
				} 
			
				if (car != '0') {
					quantZero = false;
				} 
			}	
				
			if (cmdValide) {
				if (quantZero) {
					cmdValide = false;
					messageErreur = "La quantité ne peut pas être zero.";
				}
			}
		}
		
		if (!cmdValide) {
			this.listCommandesIncorrecte.add(commande + " - " + messageErreur);
		}
		
		return cmdValide;
	}

	/*Méthode de calcul de taxes.*/
	public double calculTaxes(double facture) {
		final double TPS = 0.05;
		final double TVQ = 0.09975;

		double taxes = 0;

		taxes = (facture * (TPS + TVQ));
		
		return taxes;
	}

	/*Méthode qui calcule le total de la facture aprés l'ajout des taxes.*/
	public double calculerTotal(double facture) {
		double factureTotale = 0;
		double taxes = calculTaxes(facture);

		factureTotale = facture + taxes;

		return factureTotale;
	}

	/*Permet de lire un fichier texte.*/
	public static BufferedReader ouvrirFichier(String nomFichier) throws IOException {
		Path chemin = Paths.get(nomFichier).toAbsolutePath();
		BufferedReader ficLecture = Files.newBufferedReader(chemin, Charset.defaultCharset());
		return ficLecture;
	}

	/*Permet de préparer et retourner un fichier pour écriture.*/
	private PrintWriter preparerFicEcrire(String nomFichier) throws FileNotFoundException {
		String chemin = Paths.get(nomFichier).toAbsolutePath().toString();
		File file = new File(chemin);
		PrintWriter ficEcrire = new PrintWriter(file);
		return ficEcrire;
	}

	/*Affiche sur le terminal les commandes incorrectes et la facture.*/
	public String sortieTerm() {
		final String TITRE_BIENV = "Bienvenue chez Barette!\r\n";
		final String TITRE_FACT = "Facture:\r\n";
		final String TITRE_ERR = "\r\nLes commandes incorrectes:\r\n";
		final String MSG_AUCUNE = "Aucune facture.";

		String facture = TITRE_BIENV;

		if (!listCommandesIncorrecte.isEmpty()) {
			facture += TITRE_ERR;
			
			for (String cmd : listCommandesIncorrecte) {
				facture += cmd + "\r\n";
			}
			
			facture += "\r\n";
		}
		
		if (nbCmdValides == 0) {
			facture += MSG_AUCUNE;

		} else {
			facture += TITRE_FACT;

			for (Client client : clients) {
				facture += verifierFacturesZero(client);
			}
		}
		return facture;
	}

	public String verifierFacturesZero(Client client) {
		String facture = "";
		
		if (client.getFacture() != 0) {
			facture = client.afficher() + "\r\n";
		}
		return facture;
	}
	
	private void ecrireFacture(PrintWriter ficEcriture, String facture) {
		
		ficEcriture.println(facture);
		
		ficEcriture.close();
	}
	
	/*Lire les donnees dans un fichier*/
	public void lireDonnees() {

		boolean ficReussi = true;

		try {
			ficLecture = ouvrirFichier(nomFichier);

		} catch (IOException exc) {
			System.out.println("Impossible d'ouvrire le ficher: " + nomFichier + " n'existe pas.");
			ficReussi = false;
		}

		if (ficReussi) {

			try {
				assignerTableau();

			} catch (IOException exc) {
				System.out.println("Un erreur de lecture est survenu dans le fichier " + nomFichier + ".");
			}
		}
	}
	
	/*Sauvegarder la facture dans un fichier.*/
	public String sauvegarderFicFacture() throws FileNotFoundException {
		
		String dateHeure = (new SimpleDateFormat("dd-MM-YYYY--HH-mm-a").format(new Date()));
		
		String nomFic = "Facture-du--" + dateHeure + ".txt";
		
		String facture = sortieTerm();

		ficEcriture = preparerFicEcrire(nomFic);

		ecrireFacture(ficEcriture, facture);
		
		return nomFic;
	}

	public String getDonnees() {
		return donnees;
	}
}
