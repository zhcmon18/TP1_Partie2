package graphique;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Fenetre extends JFrame{
	
	private JTextArea champTexteFic;
	private JTextArea champTexteFact;
	private JButton lireFic;
	private JButton produireFact;
	private JButton sauvegarder;
	private EcouteurFenetre ecouteur;
	
	private static final long serialVersionUID = 1L;

	public Fenetre() {
		super("Chez Barette");
		setSize(900, 625);
		setMinimumSize(new Dimension(getWidth(), getHeight()));
		
		ecouteur = new EcouteurFenetre(this);
		
		JPanel panFic = new JPanel(new BorderLayout(0, 20));
		JPanel panBoutonFic = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
		
		panFic.setBorder(new EmptyBorder(30, 30, 50, 30));
		
		JLabel labelDonnees = new JLabel("Les données:");
		labelDonnees.setFont(labelDonnees.getFont().deriveFont(20f));;
		
		champTexteFic = new JTextArea(20, 20);
		champTexteFic.setFont(champTexteFic.getFont().deriveFont(15f));
		
		JScrollPane scrollFic = new JScrollPane(champTexteFic);
		
		lireFic = new JButton("Lire le fichier");
		lireFic.addActionListener(ecouteur);

		panFic.add(labelDonnees, BorderLayout.NORTH);
		panFic.add(scrollFic);
		panBoutonFic.add(lireFic);
		panFic.add(panBoutonFic, BorderLayout.SOUTH);
		
		JPanel panFacture = new JPanel(new BorderLayout(0, 20));
		JPanel panBoutonFact = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
		
		panFacture.setBorder(new EmptyBorder(30, 30, 50, 30));

		JLabel labelFacture = new JLabel("La facture:");
		labelFacture.setFont(labelDonnees.getFont().deriveFont(20f));
		
		champTexteFact = new JTextArea(20, 40);
		champTexteFact.setFont(champTexteFact.getFont().deriveFont(15f));
		
		JScrollPane scrollFact = new JScrollPane(champTexteFact);
		
		produireFact = new JButton("Produire la facture");
		produireFact.addActionListener(ecouteur);
		
		sauvegarder = new JButton("Sauvegarder");
		sauvegarder.addActionListener(ecouteur);
		
		panFacture.add(labelFacture, BorderLayout.NORTH);
		panFacture.add(scrollFact);
		panBoutonFact.add(produireFact);
		panBoutonFact.add(sauvegarder);
		panFacture.add(panBoutonFact, BorderLayout.SOUTH);
		
		add(panFic, BorderLayout.WEST);
		add(panFacture, BorderLayout.EAST);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public JTextArea getChampTexteFic() {
		return champTexteFic;
	}

	public JTextArea getChampTexteFact() {
		return champTexteFact;
	}

	public JButton getLireFic() {
		return lireFic;
	}

	public JButton getProduireFact() {
		return produireFact;
	}

	public JButton getSauvegarder() {
		return sauvegarder;
	}
}
