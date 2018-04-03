package test;

import java.io.IOException;

import org.junit.Test;

import main.GestionCommandes;
/**
 * @author Youcef
 * 
 * Feuille de test désormait désuet - Le fichier test111.txt n'existe plus.
 *
 */
public class TestOuvrirFicException {
	
	@Test(expected = IOException.class)
	public void testException() throws IOException {
		GestionCommandes.ouvrirFichier("test111.txt");
	}

}
