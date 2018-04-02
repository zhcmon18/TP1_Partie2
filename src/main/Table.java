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
	
	public void ajouterCommande(String commande) {
		listCommandes.add(commande);
	}
	
	public void calculerFacture() {
		montantFacture = 0;
		for (String commande : listCommandes) {
			montantFacture += gestionnaire.montantCommande(commande);
		}
	}
	
	public String sortieFacture() {
		String facture = "";
		
		return facture;
	}
	
	@Override
	public String toString() {
		return id;
	}
	
}
