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
	private List<Table> listTables;
	private List<String> listCommandes;
	private List<String> listCommandesIncorrecte;
	private BufferedReader ficLecture;
	private PrintWriter ficEcriture;
	private String nomFichier, donnees;
	private int nbCmdValides;
	
	public String messageErreur = "";

	/*Le constructeur.*/
	public GestionCommandes(String nomFichier) throws Exception {
		this.nomFichier = nomFichier;
		
		listClients = new ArrayList<>();
		listPlats = new ArrayList<>();
		listTables = new ArrayList<>();
		listCommandes = new ArrayList<>();
		listCommandesIncorrecte = new ArrayList<>();
		clients = new ArrayList<>();		
	}

	/*Lit et définit dans quelle partie du fichier la lecture est rendue.*/
	private void assignerTableau() throws IOException  {

		int listCourant = -1;
		boolean clientVu, platVu, tableVu, commandeVu, format = true;

		clientVu = platVu = tableVu = commandeVu = false;

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
					format = false;
					messageErreur += "\nClients: apparait plus d'une fois.";
				}

				listCourant = 0;
				clientVu = true;

			} else if (ligne.equals("Plats:")) {

				if (platVu) {
					format = false;
					messageErreur += "\nPlat: apparait plus d'une fois.";
				}

				listCourant = 1;
				platVu = true;

			} else if (ligne.equals("Tables:")) {

				if (tableVu) {
					format = false;
					messageErreur += "\nTables: apparait plus d'une fois.";
				}

				listCourant = 2;
				tableVu = true;

			} else if (ligne.equals("Commandes:")) {

				if (commandeVu) {
					format = false;
					messageErreur += "\nCommande: apparait plus d'une fois.";
				}

				listCourant = 3;
				commandeVu = true;

			} else {
				format = assignerLigne(listCourant, ligne);	
			}
		}
		if (!format) {
			throw new IOException(messageErreur);
		} else {
			for (Table table : listTables) {
				table.calculerFacture();
			}
		}
	}

	/*Distribue l’information et effectue la vérification selon la partie du
	  fichier.*/
	private boolean assignerLigne(int list, String ligne) throws IOException {

		boolean formatDonnes = true;
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
			listTables.add(new Table(ligne, this));
			break;
		case 3:

			if (commandeValide(ligne)) {
				listCommandes.add(ligne);
				nbCmdValides++;

				Client client = null;
				
				for (Client c : clients) {
					if (c.getNom().equals(ligne.split(" ")[0])) {
						client = c;
					}
				}
				
				client.setFacture(client.getFacture() + montantCommande(ligne));
				
				Table table = null;
				
				for (Table t : listTables) {
					if (t.toString().equals(ligne.split(" ")[3])) {
						table = t;
						break;
					}
				}
				
				table.ajouterCommande(ligne);

			} else {
				formatDonnes = false;
			}
			break;
		default:
			formatDonnes = false;
		}
		
		return formatDonnes;
	}
	
	

	/*Vérifie la commande si elle contient le client, le plat, et la quantité valides.*/
	public boolean commandeValide(String commande) {
		boolean cmdValide = true;
		
		if (commande.split(" ").length != 4) {
			messageErreur += "\nla commande - "+commande+" - ne respecte pas le format demand\u00e9.";
			cmdValide = false;
		
		} else {			
			boolean clientValide, clientExiste, platValide, platExiste, tableValide, tableExiste;

			clientValide = clientExiste = platValide = platExiste = tableValide = tableExiste = true;
			
			String clientCmd = commande.split(" ")[0];
			String platCmd = commande.split(" ")[1];
			String quantiteCmd = commande.split(" ")[2];
			String tableCmd = commande.split(" ")[3];
			
			clientValide = clientCmd.matches("[a-zA-ZÀ-ÿ]+");
			clientExiste = donneeExiste(listClients, clientCmd, 0);
			
			platValide = platCmd.matches("[a-zA-ZÀ-ÿ_]+");
			platExiste = donneeExiste(listPlats, platCmd, 0);
			
			if(!isInt(tableCmd)){
				messageErreur += "\nle format de la table " + tableCmd + " de la commande n'est pas valide.";
				tableValide = false;
			}
			tableExiste = donneeExiste(listTables, tableCmd, 0);
			
			if (!clientValide) {
				cmdValide = false;
				messageErreur += "\nle format du client " + clientCmd + " n'est pas valide.";
			
			} else {
				if (!clientExiste) {
					cmdValide = false;
					messageErreur += "\nle client " + clientCmd + " n'existe pas.";
				}
			}

			if (!platValide) {
				cmdValide = false;
				messageErreur += "\nle format du plat " + platCmd + " n'est pas valide.";
			
			} else {
				if (!platExiste) {
					cmdValide = false;
					messageErreur += "\nle plat " + platCmd + " n'existe pas.";
				}
			}

			if (!tableValide) {
				cmdValide = false;
				messageErreur += "\nle format de la table " + tableCmd + " n'est pas valide.";
			
			} else {
				if (!tableExiste) {
					cmdValide = false;
					messageErreur += "\nla table " + tableCmd + " n'existe pas.";
				}
			}
			boolean quantZero = false;
			if(!isInt(quantiteCmd)){
				messageErreur += "\nle format de la quantité " + quantiteCmd + " de la commande n'est pas valide.";
				cmdValide = false;
			} else {
				if (Integer.parseInt(quantiteCmd) == 0) {
					quantZero = true;
				}
			}
				
			if (cmdValide) {
				if (quantZero) {
					cmdValide = false;
					messageErreur += "\nLa quantité ne peut pas être zero.";
				}
			}
		}
		
		if (!cmdValide) {
			this.listCommandesIncorrecte.add(commande + " - " + messageErreur);
		}
		
		return cmdValide;
	}
	
	//Méthode qui vérifie si les données existent et retourne un boolean.
	private <E> boolean donneeExiste(List<E> liste, String donnee, int indice) {
		
		boolean existe = false;
		
		for (E element : liste) {
			if (element.toString().split(" ")[indice].equals(donnee)) {
				existe = true;
				break;
			}
		}
		
		return existe;
	}
	
	// Méthode qui vérifie si la chaine reçus est un entier.
	private boolean isInt(String chaine) {
		boolean value = true;
		for (char car : chaine.toCharArray()) {
			if (!Character.isDigit(car)) {
				value = false;
				break;
			} 
		}
		return value;
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
		double taxes;

		if(facture > 100) {
			facture *= 1.15;
		}
		
		taxes = calculTaxes(facture);
		
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

			for (Table table : listTables) {
				facture += verifierFacturesZero(table);
			}
		}
		return facture;
	}

	public String verifierFacturesZero(Table table) {
		String facture = "";
		
		if (table.getMontantFacture() != 0) {
			facture = table.afficher() + "\r\n";
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
	public void lireDonnees() throws IOException {

		boolean ficReussi = true;

		try {
			ficLecture = ouvrirFichier(nomFichier);

		} catch (IOException exc) {
			ficReussi = false;
		}

		if (ficReussi) {
			assignerTableau();
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
	
	public String formaterLigneCommande (String commande) {
		
		String clientCmd = commande.split(" ")[0];
		String platCmd = commande.split(" ")[1];
		String quantiteCmd = commande.split(" ")[2];
		
		return clientCmd + ", " + platCmd + ", qte " + quantiteCmd + " = " + String.format("%.2f$", montantCommande(commande));
	}
	
	public double montantCommande (String commande) {
		double montant;
		
		double prixPlat = 0;
		int nbPlat = Integer.parseInt(commande.split(" ")[2]);
		
		for (String plat : listPlats) {
			if (plat.split(" ")[0].equals(commande.split(" ")[1])) {
				prixPlat = Double.parseDouble(plat.split(" ")[1]);
			}
		}

		double prixBrut = prixPlat * nbPlat;

		montant = calculerTotal(prixBrut);
		
		return montant;
	}
	
}
