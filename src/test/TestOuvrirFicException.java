package test;

import java.io.IOException;

import org.junit.Test;

import main.GestionCommandes;

public class TestOuvrirFicException {
	
	@Test(expected = IOException.class)
	public void testException() throws IOException {
		GestionCommandes.ouvrirFichier("test111.txt");
	}

}
