package test;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Client;

public class testClient {

	@Test
	public void testGetNom() {
		
		String nomPrevue =  "sylvain";
		
		Client client = new Client(nomPrevue);

		assertEquals("Le nom du client", nomPrevue, client.getNom());
	}

}
