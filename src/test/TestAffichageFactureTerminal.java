package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import main.GestionCommandes;

/**
 * @author Youcef
 * 
 * Feuille de test d�sormait d�suet - La facture a un format diff�rent.
 *
 */
public class TestAffichageFactureTerminal {

	GestionCommandes gestionCmd;

	@Before
	public void AvantChaqueTest() throws Exception {
		gestionCmd = new GestionCommandes("test2.txt");
	}

	@After
	public void ApresChaqueTest() {
		gestionCmd = null;
	}
	
	//La chaine est vide ou pas.
	@Test
	public void testSortieTerm() {
		
		assertNotNull(gestionCmd.sortieTerm());
		
	}
	
	@Test
	public void testFactureValide() {
		
		String factureRetournee =  gestionCmd.sortieTerm();
		
		String factureAttendue = "Bienvenue chez Barette!\r\n" + 
				"\r\n"+
				"Les commandes incorrectes:\r\n" + 
				"Roger1 Poutine 1 - le format du client Roger1 n'est pas valide.\r\n" + 
				"C�linee Frites 2 - le client C�linee n'existe pas.\r\n" + 
				"\r\n"+
				"Facture:\r\n" +
				"C�line 18,11$\r\n";
		System.out.println(factureRetournee);
		assertEquals(factureRetournee, factureAttendue );
		
	}	
}