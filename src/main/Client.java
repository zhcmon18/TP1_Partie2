package main;


public class Client {

	/*Attributs*/
	private String nom;
	private double facture;

	/*Constructeur avec un paramètre*/
	public Client(String nom) {

		this.nom = nom;
		this.facture = 0;
	}

	/*Accesseur de l'attribut nom*/
	public String getNom() {
		return nom;
	}

	/*Accesseur de l'attribut nom*/
	public double getFacture() {
		return facture;

	}
	
	/*Mutateur de l'attribut facture*/
	public void setFacture(double facture) {
		this.facture = facture;

	}
	
	/*Affiche la facture*/
	public String afficher() {
		return this.getNom() + " " + String.format("%.2f$", this.getFacture());
	}

}
