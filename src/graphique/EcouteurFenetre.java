package graphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import main.GestionCommandes;

public class EcouteurFenetre implements ActionListener {

	private final String TITRE = "Chez Barette";
	
	private JFileChooser choixFichier = new JFileChooser(System.getProperty("user.dir"));
	private Fenetre fenetre;
	private GestionCommandes gestionCmd;

	public EcouteurFenetre(Fenetre fenetre) {
		super();
		this.fenetre = fenetre;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fenetre.getLireFic()) {

			if (choixFichier.showOpenDialog(fenetre) == JFileChooser.APPROVE_OPTION) {

				File f = choixFichier.getSelectedFile();
				
				String nomFichier = f.getName();
				
				try {
					gestionCmd = new GestionCommandes(nomFichier);
					gestionCmd.lireDonnees();
					
					fenetre.getChampTexteFic().setText(gestionCmd.getDonnees());
					
					fenetre.setTitle(nomFichier + " - " + TITRE); 
					
				} catch (Exception ex) {

					JOptionPane.showMessageDialog(fenetre, "Impossible d'ouvrir le ficher.", "Ouvrir",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		} else if (e.getSource() == fenetre.getProduireFact()) {
			
			if (gestionCmd != null) {
				
				fenetre.getChampTexteFact().setText(gestionCmd.sortieTerm());
			}
		} else if (e.getSource() == fenetre.getSauvegarder()) {
			
			try {
				String nomFic = gestionCmd.sauvegarderFicFacture();
				
				JOptionPane.showMessageDialog(fenetre, "La facture a été sauvegardée dans le fichier\n" + nomFic, 
						"Sauvegarder", JOptionPane.INFORMATION_MESSAGE);
			
			} catch (FileNotFoundException exc) {
				System.out.println("Le fichier est introuvable.");
			}
		}
	} 
}
