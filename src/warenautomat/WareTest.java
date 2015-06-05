package warenautomat;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class WareTest {

	public static Date getDate(String dateString) {
		try {
			DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
			return df.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Ware getMars() {
		return new Ware("Mars", 200, getDate("01.01.2016"));
	}

	public static Ware getSnickers() {
		return new Ware("Snickers", 140, getDate("01.01.2015"));
	}

	@Test
	public void testGetName() {
		assertEquals("Mars", getMars().getName());
	}

	@Test
	public void testGetPreis() {
		assertEquals(200, getMars().getPrice());
	}

	@Test
	public void testGetDate() throws ParseException {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
		assertEquals(df.parse("01.01.2016"), getMars().getDate());
	}

	@Test
	public void testGibZustand() throws ParseException {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
		SystemSoftware.setzeAktuellesDatum(df.parse("01.06.2015"));
		assertEquals(1, getMars().gibZustand());
		assertEquals(2, getSnickers().gibZustand());
		SystemSoftware.setzeAktuellesDatum(df.parse("01.01.2015"));
		assertEquals(1, getMars().gibZustand());
		assertEquals(1, getSnickers().gibZustand());
	}

	@Test
	public void testIstAbgelaufen() {
		SystemSoftware.setzeAktuellesDatum(getDate("01.06.2015"));
		assertEquals(false, getMars().istAbgelaufen());
		assertEquals(true, getSnickers().istAbgelaufen());
		SystemSoftware.setzeAktuellesDatum(getDate("01.01.2015"));
		assertEquals(1, getMars().gibZustand());
		assertEquals(1, getSnickers().gibZustand());
	}
	
	@Test
	public void wareWertAbgelaufen() throws ParseException {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
		SystemSoftware.setzeAktuellesDatum(df.parse("01.06.2015"));
		Ware ware1 = new Ware("Mars", 120, df.parse("01.01.2015"));
		assertEquals(10, ware1.getPrice());
		Ware ware2 = new Ware("Mars", 150, df.parse("01.01.2015"));
		assertEquals(15, ware2.getPrice());
	}
}
