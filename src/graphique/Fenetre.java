package graphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Fenetre extends JFrame{
	
	/*Les panneaux qui contiennent les champs texte.*/
	private JPanel[] panChamps;
	/*Les panneaux qui contiennent les boutons.*/
	private JPanel[] panBoutons;
	/*Les champs textes*/
	private JTextArea[] tabChampsTexte;
	/*Scroll*/
	private JScrollPane[] tabScroll;
	/*Les boutons*/
	private JButton[] tabBoutons;
	/*Les labels pours les champs texte*/
	private JLabel[] tabLabels;
	/*L’écouteur d’évènements */
	private EcouteurFenetre ecouteur;
	
	private static final long serialVersionUID = 1L;
	
	private final String[] TEXTE_BOUTONS = {"Lire le fichier", "Produire la facture", "Sauvegarder"};
	private final String[] TEXTE_LABELS = {"Les données:", "La facture:"};

	private final int NB_CHAMPS_TEXTE = 2;
	
	public Fenetre() {
		super("Chez Barette");
		setSize(850, 550);
		setMinimumSize(new Dimension(getWidth(), getHeight()));
		
		ecouteur = new EcouteurFenetre(this);
		
		tabBoutons = new JButton[TEXTE_BOUTONS.length];
		
		for (int i = 0; i < TEXTE_BOUTONS.length; i++) {
			tabBoutons[i] = new JButton(TEXTE_BOUTONS[i]);
			tabBoutons[i].addActionListener(ecouteur);
			
			if (tabBoutons[i].getText() != TEXTE_BOUTONS[0] ) {
				tabBoutons[i].setEnabled(false);
			}	
		}
		
		tabBoutons[2].setPreferredSize(new Dimension((int)tabBoutons[1].getPreferredSize().getWidth(), 
				(int)tabBoutons[1].getPreferredSize().getHeight()));

		tabLabels = new JLabel[TEXTE_LABELS.length];
		
		for (int i = 0; i < TEXTE_LABELS.length; i++) {
			tabLabels[i] = new JLabel(TEXTE_LABELS[i]);
			tabLabels[i].setFont(tabLabels[i].getFont().deriveFont(18f));;
		}
		
		tabChampsTexte = new JTextArea[NB_CHAMPS_TEXTE];
		
		for (int i = 0; i < tabChampsTexte.length; i++) {
			tabChampsTexte[i] = new JTextArea();
			tabChampsTexte[i].setFont(tabChampsTexte[i].getFont().deriveFont(16f));
			tabChampsTexte[i].setEditable(false);
			tabChampsTexte[i].setBackground(Color.WHITE);
			tabChampsTexte[i].setRows(5);
		}
		
		tabChampsTexte[0].setColumns(20);
		tabChampsTexte[1].setColumns(35);

		tabScroll = new JScrollPane[tabChampsTexte.length];
		
		for (int i = 0; i < tabScroll.length; i++) {
			tabScroll[i] = new JScrollPane(tabChampsTexte[i]);
		}

		panChamps = new JPanel[NB_CHAMPS_TEXTE];
		
		for (int i = 0; i < panChamps.length; i++) {
			panChamps[i] = new JPanel(new BorderLayout(0, 20));
			panChamps[i].setBorder(new EmptyBorder(30, 30, 50, 30));
			panChamps[i].add(tabLabels[i], BorderLayout.NORTH);
			panChamps[i].add(tabScroll[i]);
		}
		
		panBoutons = new JPanel[TEXTE_BOUTONS.length];
		
		for (int i = 0; i < panBoutons.length; i++) {
			panBoutons[i] = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
	
		}
		
		panBoutons[0].add(tabBoutons[0]);

		panChamps[0].add(panBoutons[0], BorderLayout.SOUTH);
		
		panBoutons[1].add(tabBoutons[1]);
		panBoutons[1].add(tabBoutons[2]);

		panChamps[1].add(panBoutons[1], BorderLayout.SOUTH);
		
		add(panChamps[0], BorderLayout.WEST);
		add(panChamps[1], BorderLayout.EAST);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public JTextArea[] getTabChampsTexte() {
		return tabChampsTexte;
	}

	public JButton[] getTabBoutons() {
		return tabBoutons;
	}
}
