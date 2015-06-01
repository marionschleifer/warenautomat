package warenautomat;

import static org.junit.Assert.*;

import org.junit.Test;

public class KasseTest {

	@Test
	public void testFuelleKasseNormal() {
		Kasse kasse = new Kasse();
		assertEquals(90, kasse.fuelleKasse(1.0, 90));
		assertEquals(5, kasse.fuelleKasse(1.0, 5));
		assertEquals(40, kasse.fuelleKasse(2.0, 40));
		assertEquals(33, kasse.fuelleKasse(2.0, 33));
	}

	@Test
	public void testFuelleKasseNichtGenuegendPlatz() {
		Kasse kasse = new Kasse();
		assertEquals(-2, kasse.fuelleKasse(1.0, 102));
	}

	@Test
	public void testFuelleKasseNichtGenuegendPlatz2() {
		Kasse kasse = new Kasse();
		kasse.fuelleKasse(1.0, 80);
		kasse.fuelleKasseBestaetigung();
		assertEquals(-10, kasse.fuelleKasse(1.0, 30));
	}

	@Test
	public void testFuelleKasseCornerCases() {
		Kasse kasse = new Kasse();
		kasse.fuelleKasse(1.0, 99);
		kasse.fuelleKasseBestaetigung();
		assertEquals(1, kasse.fuelleKasse(1.0, 1));
		kasse.fuelleKasseBestaetigung();
		assertEquals(-1, kasse.fuelleKasse(1.0, 1));
	}

	@Test
	public void testFrankenZuRappen() {
		assertEquals(200, Kasse.frankenZuRappen(2.0));
		assertEquals(100, Kasse.frankenZuRappen(1.0));
		assertEquals(50, Kasse.frankenZuRappen(0.5));
		assertEquals(20, Kasse.frankenZuRappen(0.2));
		assertEquals(10, Kasse.frankenZuRappen(0.1));
	}

	@Test
	public void testNichtUnterstuetzterMuenzbetrag() {
		Kasse kasse = new Kasse();
		assertEquals(-200, kasse.fuelleKasse(1.5, 2));
		assertEquals(-200, kasse.fuelleKasse(5.0, 2));
		assertEquals(-200, kasse.fuelleKasse(0.05, 2));
		assertEquals(-200, kasse.fuelleKasse(0.01, 2));
		assertEquals(-200, kasse.fuelleKasse(0.02, 2));
	}

	@Test
	public void testEinnehmenErfolgreich() {
		Kasse kasse = new Kasse();
		assertEquals(0, kasse.gibZurZeitEingenommen());
		assertEquals(0, kasse.gibMuenzsaeule(100).getMenge());
		
		assertTrue(kasse.einnehmen(1.0));
		assertEquals(1, kasse.gibMuenzsaeule(100).getMenge());
		assertEquals(100, kasse.gibZurZeitEingenommen());
		
		assertTrue(kasse.einnehmen(1.0));
		assertEquals(2, kasse.gibMuenzsaeule(100).getMenge());
		assertEquals(200, kasse.gibZurZeitEingenommen());
		
		assertTrue(kasse.einnehmen(1.0));
		assertEquals(3, kasse.gibMuenzsaeule(100).getMenge());
		assertEquals(300, kasse.gibZurZeitEingenommen());
		
		assertEquals(0, kasse.gibMuenzsaeule(50).getMenge());
		assertTrue(kasse.einnehmen(0.5));
		assertEquals(1, kasse.gibMuenzsaeule(50).getMenge());
		assertEquals(350, kasse.gibZurZeitEingenommen());
	}

	@Test
	public void testEingenommenErfolglos() {
		Kasse kasse = new Kasse();
		kasse.fuelleKasse(1.0, 98);
		kasse.fuelleKasseBestaetigung();
		assertTrue(kasse.einnehmen(1.0));
		assertTrue(kasse.einnehmen(1.0));
		assertFalse(kasse.einnehmen(1.0));
		assertFalse(kasse.einnehmen(1.0));
	}

	@Test
	public void testRappenZuFranken() {
		assertEquals(2.0, Kasse.rappenZuFranken(200), 0.0001);
		assertEquals(1.0, Kasse.rappenZuFranken(100), 0.0001);
		assertEquals(0.5, Kasse.rappenZuFranken(50), 0.0001);
		assertEquals(0.2, Kasse.rappenZuFranken(20), 0.0001);
		assertEquals(0.1, Kasse.rappenZuFranken(10), 0.0001);
	}

	@Test
	public void testBezahleWare() {
		Ware ware = WareTest.getWare();
		Kasse kasse = new Kasse();
		kasse.einnehmen(0.2);
		kasse.einnehmen(2.0);
		kasse.einnehmen(0.5);
		assertEquals(270, kasse.gibZurZeitEingenommen());
		kasse.bezahleWare(ware);
		assertEquals(70, kasse.gibZurZeitEingenommen());
	}
	
	@Test
	public void testGibWechselGeld200Und100() {
		Kasse kasse = new Kasse();
		assertEquals(0, kasse.gibMuenzsaeule(100).getMenge());
		assertEquals(0, kasse.gibZurZeitEingenommen());
		kasse.gibWechselGeld();
		assertEquals(0, kasse.gibZurZeitEingenommen());
		kasse.einnehmen(2.0);
		kasse.einnehmen(2.0);
		kasse.einnehmen(1.0);
		assertEquals(1, kasse.gibMuenzsaeule(100).getMenge());
		assertEquals(2, kasse.gibMuenzsaeule(200).getMenge());
		assertEquals(500, kasse.gibZurZeitEingenommen());
		kasse.gibWechselGeld();
		assertEquals(0, kasse.gibZurZeitEingenommen());
		assertEquals(0, kasse.gibMuenzsaeule(100).getMenge());
		assertEquals(0, kasse.gibMuenzsaeule(200).getMenge());	
	}
	
	@Test
	public void testGibWechselGeldAlle() {
		Kasse kasse = new Kasse();
		kasse.einnehmen(2.0);
		kasse.einnehmen(1.0);
		kasse.einnehmen(0.5);
		kasse.einnehmen(0.2);
		kasse.einnehmen(0.1);
		assertEquals(1, kasse.gibMuenzsaeule(200).getMenge());
		assertEquals(1, kasse.gibMuenzsaeule(100).getMenge());
		assertEquals(1, kasse.gibMuenzsaeule(50).getMenge());
		assertEquals(1, kasse.gibMuenzsaeule(20).getMenge());
		assertEquals(1, kasse.gibMuenzsaeule(10).getMenge());
		assertEquals(380, kasse.gibZurZeitEingenommen());
		kasse.gibWechselGeld();
		assertEquals(0, kasse.gibZurZeitEingenommen());
		assertEquals(0, kasse.gibMuenzsaeule(200).getMenge());
		assertEquals(0, kasse.gibMuenzsaeule(100).getMenge());
		assertEquals(0, kasse.gibMuenzsaeule(50).getMenge());	
		assertEquals(0, kasse.gibMuenzsaeule(20).getMenge());	
		assertEquals(0, kasse.gibMuenzsaeule(10).getMenge());	
	}
	
	@Test
	public void testGibWechselSpezialfall() {
		Kasse kasse = new Kasse();
		kasse.gibMuenzsaeule(200).addCoins(6);
		kasse.gibMuenzsaeule(100).addCoins(6);
		kasse.gibMuenzsaeule(50).addCoins(6);
		kasse.gibMuenzsaeule(20).addCoins(6);
		kasse.gibMuenzsaeule(10).addCoins(6);
		kasse.einnehmen(1.0);
		kasse.einnehmen(1.0);
		assertEquals(6, kasse.gibMuenzsaeule(200).getMenge());
		assertEquals(8, kasse.gibMuenzsaeule(100).getMenge());
		kasse.gibWechselGeld();
		assertEquals(5, kasse.gibMuenzsaeule(200).getMenge());
		assertEquals(8, kasse.gibMuenzsaeule(100).getMenge());
		kasse.einnehmen(0.2);
		kasse.einnehmen(0.2);
		kasse.einnehmen(0.2);
		kasse.einnehmen(0.2);
		assertEquals(10, kasse.gibMuenzsaeule(20).getMenge());
		kasse.gibWechselGeld();
		assertEquals(5, kasse.gibMuenzsaeule(50).getMenge());
		assertEquals(9, kasse.gibMuenzsaeule(20).getMenge());
		assertEquals(5, kasse.gibMuenzsaeule(10).getMenge());
	}
	
	@Test
	public void testKasseNichtGefuellt() {
		Kasse kasse = new Kasse();
		kasse.einnehmen(0.2);
		kasse.einnehmen(0.2);
		kasse.einnehmen(0.2);
		kasse.einnehmen(0.2);
		assertEquals(4, kasse.gibMuenzsaeule(20).getMenge());
		kasse.gibWechselGeld();
		assertEquals(0, kasse.gibMuenzsaeule(20).getMenge());
	}

}
