package warenautomat;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;

import org.junit.Test;

public class WareTest {
	public static Ware getWare() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
		try {
			return new Ware("Mars", 200, df.parse("01.01.2016"));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testGetName() {
		assertEquals("Mars", getWare().getName());
	}

	@Test
	public void testGetPreis() {
		assertEquals(200, getWare().getPrice());
	}

	@Test
	public void testGetDate() throws ParseException {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
		assertEquals(df.parse("01.01.2016"), getWare().getDate());
	}

}
