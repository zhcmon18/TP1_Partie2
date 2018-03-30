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
		if (e.getSource() == fenetre.getTabBoutons()[0]) {

			if (choixFichier.showOpenDialog(fenetre) == JFileChooser.APPROVE_OPTION) {

				File f = choixFichier.getSelectedFile();
				
				String nomFichier = f.getName();
				
				try {
					gestionCmd = new GestionCommandes(nomFichier);
					gestionCmd.lireDonnees();
					
					fenetre.getTabChampsTexte()[0].setText(gestionCmd.getDonnees());
					
					fenetre.setTitle(nomFichier + " - " + TITRE);
					fenetre.getTabChampsTexte()[1].setText("");
					fenetre.getTabBoutons()[1].setEnabled(true);
					
					if (fenetre.getTabBoutons()[2].isEnabled()) {
						fenetre.getTabBoutons()[2].setEnabled(false);
					}
					
				} catch (Exception ex) {

					JOptionPane.showMessageDialog(fenetre, "Impossible d'ouvrir le ficher.", "Ouvrir",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		} else if (e.getSource() == fenetre.getTabBoutons()[1]) {
			
			fenetre.getTabChampsTexte()[1].setText(gestionCmd.sortieTerm());
			fenetre.getTabBoutons()[2].setEnabled(true);
			
		} else if (e.getSource() == fenetre.getTabBoutons()[2]) {
			
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
