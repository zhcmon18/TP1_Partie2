package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.GestionCommandes;

public class TestGestionDuCalculDesTaxes {

	GestionCommandes gestionCmd;

	@Before
	public void AvantChaqueTest() throws Exception {
		gestionCmd = new GestionCommandes("test.txt");
	}

	@After
	public void ApresChaqueTest() {
		gestionCmd = null;
	}
	
	@Test
	public void testCalculerTaxeDe1000() {
		// pour TPS = 5% et TVQ = 9.975%
		// 1000 -> 50 + 99.75 -> 149.75
		
		assertEquals(149.75, gestionCmd.calculTaxes(1000), 0);
	}
	
	@Test
	public void testCalculerTaxeDe0() {
		assertEquals(0, gestionCmd.calculTaxes(0), 0);
	}
	
	@Test
	public void testCalculerTotalDe1000() {
		assertEquals(1149.75, gestionCmd.calculerTotal(1000), 0);
	}

}
