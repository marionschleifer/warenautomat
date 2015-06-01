package warenautomat;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;

import org.junit.Test;

public class StatistikEintragTest {

	@Test
	public void testGetName() throws ParseException {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
		StatistikEintrag neuerEintrag = new StatistikEintrag("Mars", 120, df.parse("01.01.2015"));
		assertEquals("Mars", neuerEintrag.getName());
	}
	
	@Test
	public void testGetPrice() throws ParseException {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
		StatistikEintrag neuerEintrag = new StatistikEintrag("Mars", 120, df.parse("01.01.2015"));
		assertEquals(120, neuerEintrag.getPrice());
	}
	
	@Test
	public void testGetDate() throws ParseException {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
		StatistikEintrag neuerEintrag = new StatistikEintrag("Mars", 120, df.parse("01.01.2015"));
		assertEquals(df.parse("01.01.2015"), neuerEintrag.getVerkauftAm());
	}

}
