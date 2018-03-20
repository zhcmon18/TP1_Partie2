package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.GestionCommandes;

public class TestCommandesValides {

	GestionCommandes gestionCmd;

	@Before
	public void AvantChaqueTest() throws Exception {
		gestionCmd = new GestionCommandes("test.txt");
	}

	@After
	public void ApresChaqueTest() {
		gestionCmd = null;
	}

	String[] cmdValides = {"Roger Poutine 1", "C�line Frites 2", "C�line Repas_Poulet 1"};
	
	String[] cmdNonValides = {"Roger1 Poutine 1", "C�line Fritessss 2", "C�line Repas_Poulet 1aaa", 
																							 "C�line 1 Repas_Poulet 1"};
	
	@Test
	public void testCommandesValides() throws IOException {
		
		for (int i = 0; i < cmdValides.length; i++) {
			
			assertTrue(gestionCmd.commandeValide(cmdValides[i]));
		}
	}
	
	@Test
	public void testCommandesNonValides() throws IOException {
		
		for (int i = 0; i < cmdNonValides.length; i++) {
			
			assertFalse(gestionCmd.commandeValide(cmdNonValides[i]));
		}	
	}
}
