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
		assertTrue(kasse.einnehmen(1.0));
		assertEquals(100, kasse.gibZurZeitEingenommen());
		assertTrue(kasse.einnehmen(1.0));
		assertEquals(200, kasse.gibZurZeitEingenommen());
		assertTrue(kasse.einnehmen(1.0));
		assertEquals(300, kasse.gibZurZeitEingenommen());
		assertTrue(kasse.einnehmen(0.5));
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

}
