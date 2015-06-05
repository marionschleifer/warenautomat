package warenautomat;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;

import org.junit.Test;

public class DrehtellerTest {
	DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);

	@Test
	public void testFuelleFach() throws ParseException {
		Drehteller mDrehteller = new Drehteller();
		Ware ware = new Ware("Mars", 120, df.parse("01.01.2016"));
		mDrehteller.fuelleFach(ware);
		assertEquals(ware, mDrehteller.getWare());
	}

	@Test
	public void testDrehen() {
		Drehteller drehteller = new Drehteller();
		assertEquals(0, drehteller.getFachAmAusgang());
		drehteller.fuelleFach(WareTest.getMars());
		assertEquals("Mars", drehteller.getWare().getName());
		drehteller.drehen();
		assertEquals(1, drehteller.getFachAmAusgang());
		assertEquals(null, drehteller.getWare());

		drehteller.fuelleFach(WareTest.getSnickers());
		assertEquals("Snickers", drehteller.getWare().getName());

		for (int fachNr = 3; fachNr <= 16; fachNr++) {
			drehteller.drehen();
			assertEquals(fachNr - 1, drehteller.getFachAmAusgang());
			assertEquals(null, drehteller.getWare());
			drehteller.fuelleFach(WareTest.getSnickers());
			assertEquals("Snickers", drehteller.getWare().getName());
		}

		drehteller.drehen();
		assertEquals("Mars", drehteller.getWare().getName());
	}

	@Test
	public void testGibTotalenWarenWert() {
		Drehteller drehteller = new Drehteller();
		assertEquals(0, drehteller.gibTotalenWarenWert());
		drehteller.fuelleFach(WareTest.getMars());
		assertEquals(200, drehteller.gibTotalenWarenWert());
	}

	@Test
	public void testgibWarenMenge() {
		Drehteller drehteller = new Drehteller();
		SystemSoftware.setzeAktuellesDatum(WareTest.getDate("1.6.2015"));
		assertEquals(0, drehteller.gibWarenMenge("Mars"));
		drehteller.fuelleFach(WareTest.getMars());
		assertEquals(1, drehteller.gibWarenMenge("Mars"));
		drehteller.drehen();
		drehteller.fuelleFach(WareTest.getMars());
		assertEquals(2, drehteller.gibWarenMenge("Mars"));
		Ware marsAbgelaufen = new Ware("Mars", 120, WareTest.getDate("1.1.2015"));
		drehteller.drehen();
		drehteller.fuelleFach(marsAbgelaufen);
		assertEquals(2, drehteller.gibWarenMenge("Mars"));
		drehteller.drehen();
		drehteller.fuelleFach(WareTest.getSnickers());
		assertEquals(2, drehteller.gibWarenMenge("Mars"));
		
	}
}
