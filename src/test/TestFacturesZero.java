package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Client;
import main.GestionCommandes;

public class TestFacturesZero {
	GestionCommandes gestionCmd;
	Client cliFact0, cliFactNon0;
	
	@Before
	public void creerInstance() throws Exception {
		 gestionCmd = new GestionCommandes("test.txt");
		 cliFactNon0 = new Client(null);
		 cliFactNon0.setFacture(23.68);
		 cliFact0 = new Client(null);
		 cliFact0.setFacture(0);
	}
	
	@After
	public void apresLesTests() {
		gestionCmd = null;
		cliFact0 = cliFactNon0 = null;
	}
	
	/*Tester les factures non zero*/
	@Test
	public void testFactureNonZero() {
		String retour = gestionCmd.verifierFacturesZero(cliFactNon0);
		
		assertNotEquals("", retour);
	}

	/*Tester les factures zero*/
	@Test
	public void testFactureZero() {
		String retour = gestionCmd.verifierFacturesZero(cliFact0);

		assertEquals("", retour);
	}
}
