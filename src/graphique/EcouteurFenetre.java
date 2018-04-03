package graphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.GestionCommandes;

public class EcouteurFenetre implements ActionListener {

	private final String TITRE = "Chez Barette";
	
	private JFileChooser choixFichier = new JFileChooser(System.getProperty("user.dir"));
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers texte", "txt", "texte");
	private Fenetre fenetre;
	private GestionCommandes gestionCmd;

	public EcouteurFenetre(Fenetre fenetre) {
		super();
		this.fenetre = fenetre;
		choixFichier.setFileFilter(filter);
		choixFichier.setAcceptAllFileFilterUsed(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fenetre.getTabBoutons()[0]) {

			if (choixFichier.showOpenDialog(fenetre) == JFileChooser.APPROVE_OPTION) {

				File f = choixFichier.getSelectedFile();
				
				if (f.exists()) {
					
					String nomFichier = f.getName();
					fenetre.setTitle(nomFichier + " - " + TITRE);
					
					try {
						gestionCmd = new GestionCommandes(nomFichier);
						gestionCmd.lireDonnees();
						
						gestionChampsTexte();

						gestionBoutons(true, 1);
						gestionBoutons(false, 2);
						
					} catch (Exception ex) {
						gestionChampsTexte();
						
						String message = gestionCmd.messageErreur;
						
						JOptionPane.showMessageDialog(fenetre, "Le format des données invalide.\n" + message, "Ouvrir",
								JOptionPane.WARNING_MESSAGE);
						
						gestionBoutons(false, 1);
						gestionBoutons(false, 2);
						
					}
					
				} else {
					JOptionPane.showMessageDialog(fenetre, "Le fichier " + f.getName() + " n'existe pas.", "Ouvrir",
							JOptionPane.WARNING_MESSAGE);
					gestionBoutons(false, 1);
					gestionBoutons(false, 2);
					fenetre.getTabChampsTexte()[0].setText("");
					fenetre.getTabChampsTexte()[1].setText("");
					
				}

			}
		
		} else if (e.getSource() == fenetre.getTabBoutons()[1]) {
			
			fenetre.getTabChampsTexte()[1].setText(gestionCmd.sortieTerm());
			gestionBoutons(true, 2);
			
		} else if (e.getSource() == fenetre.getTabBoutons()[2]) {
			
			try {
				String nomFic = gestionCmd.sauvegarderFicFacture();
				
				JOptionPane.showMessageDialog(fenetre, "La facture a été sauvegardée dans le fichier\n" + nomFic, 
						"Sauvegarder", JOptionPane.INFORMATION_MESSAGE);
			
			} catch (FileNotFoundException exc) {
				JOptionPane.showMessageDialog(fenetre, "Le fichier est introuvable.", 
						"Sauvegarder", JOptionPane.INFORMATION_MESSAGE);

			}
		}
	} 

	private void gestionBoutons(boolean etat, int indice){
		fenetre.getTabBoutons()[indice].setEnabled(etat);
	}

	private void gestionChampsTexte(){
		fenetre.getTabChampsTexte()[0].setText(gestionCmd.getDonnees());
		fenetre.getTabChampsTexte()[1].setText("");
	}
}
