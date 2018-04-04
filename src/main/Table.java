package main;

import java.util.ArrayList;
import java.util.List;

public class Table {
	
	private String id;
	private double montantFacture;
	
	private List<String> listCommandes;
	
	GestionCommandes gestionnaire;
	
	public Table(String id, GestionCommandes gestionnaire) {
		this.id = id;
		this.gestionnaire = gestionnaire;
		listCommandes = new ArrayList<>();
	}

	public double getMontantFacture() {
		return montantFacture;
	}

	public void ajouterCommande(String commande) {
		listCommandes.add(commande);
	}
	
	public void calculerFacture() {
		montantFacture = 0;
		for (String commande : listCommandes) {
			montantFacture += gestionnaire.montantCommande(commande);
		}
	
		if (montantFacture > 100) {
			montantFacture *= 1.15;
		}
	}
	
	public String sortieFacture() {
		String facture = "";
		
		return facture;
	}
	
	public String afficher() {
		String facture = "\r\nTable #" + id + " :\tnombre de commandes : " + listCommandes.size() + "\r\n";
		
		for (String commande : listCommandes) {
			facture += "\t" + gestionnaire.formaterLigneCommande(commande) + "\r\n";
		}
		
		facture += "\tTotal = " + String.format("%.2f$", montantFacture);
		
		return facture;
	}
	
	@Override
	public String toString() {
		return id;
	}
	
}
