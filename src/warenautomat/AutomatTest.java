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

//	@Test
//	public void testGetDrehtellerNr() {
//		Automat automat = new Automat();
//		assertEquals(1, automat.getmDrehtellerNr());
//	}
//	
//	public void testFuelleFach() throws ParseException {
//		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
//		Automat automat = new Automat();
//		automat.fuelleFach(1, "Mars", 200, df.parse("01.01.2016"));
//	}

}
