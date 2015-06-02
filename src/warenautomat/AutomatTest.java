package warenautomat;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;

import org.junit.Test;

public class AutomatTest {

	@Test
	public void getVerkaufsStatistik() throws ParseException {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
		Ware ware = new Ware("Mars", 120, df.parse("02.03.2016"));
		Automat automat = new Automat();
		assertEquals(0, automat.gibVerkaufsStatistik("Mars", df.parse("01.01.2015")));
		Kasse kasse = automat.gibKasse();
		kasse.einnehmen(2.0);
		kasse.bezahleWare(ware);
		assertEquals(1, automat.gibVerkaufsStatistik("Mars", df.parse("01.01.2015")));
		kasse.einnehmen(2.0);
		kasse.bezahleWare(ware);
		assertEquals(2, automat.gibVerkaufsStatistik("Mars", df.parse("01.01.2015")));
	}

	@Test
	public void testFuelleFach() throws ParseException {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
		Automat automat = new Automat();
		automat.fuelleFach(1, "Mars", 2.0, df.parse("01.01.2016"));
		assertEquals("Mars", automat.gibFachInhalt(1).getName());
		assertEquals(200, automat.gibFachInhalt(1).getPrice());
		assertEquals(df.parse("01.01.2016"), automat.gibFachInhalt(1).getDate());
		assertEquals(null, automat.gibFachInhalt(2));

		automat.fuelleFach(2, "Snickers", 1.4, df.parse("01.01.2016"));
		assertEquals("Snickers", automat.gibFachInhalt(2).getName());
		assertEquals("Mars", automat.gibFachInhalt(1).getName());
	}

	@Test
	public void testDrehen() throws ParseException {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
		Automat automat = new Automat();
		automat.fuelleFach(1, "Mars", 200, df.parse("01.01.2016"));
		assertNotNull(automat.gibFachInhalt(1));
		for (int fachNr = 2; fachNr <= 16; fachNr++) {
			automat.drehen();
			assertNull(automat.gibFachInhalt(1));
		}
		automat.drehen();
		assertNotNull(automat.gibFachInhalt(1));
		assertEquals("Mars", automat.gibFachInhalt(1).getName());
	}

	@Test
	public void testGibTotalenWarenWert() {
		Automat automat = new Automat();
		assertEquals(0.0, automat.gibTotalenWarenWert(), 0.000001);
		automat.fuelleFach(1, "Mars", 1.50, WareTest.getDate("01.01.2015"));
		assertEquals(1.5, automat.gibTotalenWarenWert(), 0.000001);
		automat.fuelleFach(2, "Snickers", 1.20, WareTest.getDate("01.01.2015"));
		assertEquals(2.7, automat.gibTotalenWarenWert(), 0.000001);
		automat.drehen();

		automat.fuelleFach(1, "Kitkat", 1.0, WareTest.getDate("01.01.2015"));
		assertEquals(3.7, automat.gibTotalenWarenWert(), 0.000001);
		automat.drehen();

		automat.fuelleFach(1, "Mars", 1.5, WareTest.getDate("01.01.2015"));
		assertEquals(5.2, automat.gibTotalenWarenWert(), 0.000001);
		automat.fuelleFach(7, "Mars", 1.5, WareTest.getDate("01.01.2015"));
		assertEquals(6.7, automat.gibTotalenWarenWert(), 0.000001);

		for (int fachNr = 3; fachNr <= 15; fachNr++) {
			automat.drehen();
		}

		assertNull(automat.gibFachInhalt(1));
		automat.fuelleFach(7, "Red Bull", 3.3, WareTest.getDate("01.01.2015"));
		assertEquals(10.0, automat.gibTotalenWarenWert(), 0.000001);
		automat.drehen();
		assertNotNull(automat.gibFachInhalt(1));

	}

	@Test
	public void testOeffnenEmpty() {
		Automat automat = new Automat();
		assertEquals(false, automat.oeffnen(1));
		assertEquals(false, automat.oeffnen(2));
	}

	@Test
	public void testOeffnenErfolgreich() {
		Automat automat = new Automat();
		Kasse kasse = automat.gibKasse();
		kasse.einnehmen(2.0);
		SystemSoftware.setzeAktuellesDatum(WareTest.getDate("01.06.2015"));
		automat.fuelleFach(1, "Mars", 2.0, WareTest.getDate("01.01.2016"));
		assertEquals(false, automat.oeffnen(2));
		assertNotNull(automat.gibFachInhalt(1));
		assertEquals(true, automat.oeffnen(1));
		assertNull(automat.gibFachInhalt(1));
		assertEquals(false, automat.oeffnen(1));
	}

	@Test
	public void testOeffnenWareAbgelaufen() {
		Automat automat = new Automat();
		Kasse kasse = automat.gibKasse();
		kasse.einnehmen(2.0);
		SystemSoftware.setzeAktuellesDatum(WareTest.getDate("01.06.2015"));
		automat.fuelleFach(1, "Mars", 2.0, WareTest.getDate("01.01.2015"));
		assertEquals(false, automat.oeffnen(1));
		assertNotNull(automat.gibFachInhalt(1));
	}

	@Test
	public void testOeffnenZuwenigGeld() {
		Automat automat = new Automat();
		Kasse kasse = automat.gibKasse();
		kasse.einnehmen(0.5);
		SystemSoftware.setzeAktuellesDatum(WareTest.getDate("01.06.2015"));
		automat.fuelleFach(1, "Mars", 2.0, WareTest.getDate("01.01.2016"));
		assertEquals(false, automat.oeffnen(1));
		assertNotNull(automat.gibFachInhalt(1));
	}

	@Test
	public void testOeffnenZuWenigWechselgeld() {
		Automat automat = new Automat();
		Kasse kasse = automat.gibKasse();
		kasse.einnehmen(2.0);
		SystemSoftware.setzeAktuellesDatum(WareTest.getDate("01.06.2015"));
		automat.fuelleFach(1, "Mars", 1.8, WareTest.getDate("01.01.2016"));
		assertEquals(false, automat.oeffnen(1));
		assertNotNull(automat.gibFachInhalt(1));
		automat.fuelleFach(2, "Mars", 2.0, WareTest.getDate("01.01.2016"));
		assertEquals(true, automat.oeffnen(2));
		assertNull(automat.gibFachInhalt(2));
	}

	@Test
	public void testBestellGrenzen() {
		Automat automat = new Automat();
		automat.konfiguriereBestellung("Mars", 10, 20);
		automat.konfiguriereBestellung("Snickers", 10, 20);
		assertEquals(2, automat.getBestellGrenzen().size());
		assertEquals(10, automat.getBestellGrenzen().get("Mars").getBestellGrenze());
	}

	@Test
	public void testBestellGrenzenNameUeberschreiben() {
		Automat automat = new Automat();
		automat.konfiguriereBestellung("Mars", 10, 20);
		automat.konfiguriereBestellung("Mars", 20, 20);
		assertEquals(1, automat.getBestellGrenzen().size());
		assertEquals(20, automat.getBestellGrenzen().get("Mars").getBestellGrenze());
	}

	@Test
	public void testGrenzeErreicht() throws ParseException {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
		Automat automat = new Automat();
		automat.konfiguriereBestellung("Mars", 3, 20);
		automat.konfiguriereBestellung("Snickers", 2, 20);
		automat.fuelleFach(1, "Mars", 1.2, df.parse("01.01.2016"));
		automat.fuelleFach(2, "Mars", 1.2, df.parse("01.01.2016"));
		assertEquals(false, automat.grenzeErreicht("Mars"));
		// automat.fuelleFach(3, "Mars", 1.2, df.parse("01.01.2016"));
		// automat.fuelleFach(4, "Mars", 1.2, df.parse("01.01.2016"));
	}
}
