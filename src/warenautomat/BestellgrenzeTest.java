package warenautomat;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;

import org.junit.Test;

public class BestellgrenzeTest {

	@Test
	public void testgetWarenName() throws ParseException {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
		Ware ware = new Ware("Mars", 230, df.parse("01.01.2016"));
		Bestellgrenze bestellgrenze = new Bestellgrenze(ware.getName(), 10, 30);
		assertEquals("Mars", bestellgrenze.getName());
		assertEquals(10, bestellgrenze.getBestellGrenze());
		assertEquals(30, bestellgrenze.getBestellAnzahl());
	}
	

}
