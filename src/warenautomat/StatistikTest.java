package warenautomat;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;

import org.junit.Test;

public class StatistikTest {

	@Test
	public void testGibBetragVerkaufteWare() throws ParseException {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
		Statistik statistik = new Statistik();
		{
			Ware ware = new Ware("Mars", 120, df.parse("01.01.2016"));
			statistik.verkaufteWareHinzufuegen(ware, df.parse("02.03.2015"));
		}
		assertEquals(120, statistik.gibBetragVerkaufteWare());
		{
			Ware ware = new Ware("Twix", 140, df.parse("01.01.2016"));
			statistik.verkaufteWareHinzufuegen(ware, df.parse("02.03.2015"));
		}
		assertEquals(260, statistik.gibBetragVerkaufteWare());
	}

	@Test
	public void testGibVerkaufsstatistik() throws ParseException {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
		Statistik statistik = new Statistik();
		Ware mars = new Ware("Mars", 120, df.parse("03.01.2016"));
		statistik.verkaufteWareHinzufuegen(mars, df.parse("02.03.2015"));
		assertEquals(1, statistik.gibVerkaufsStatistik("Mars", df.parse("01.01.2015")));
		statistik.verkaufteWareHinzufuegen(mars, df.parse("01.01.2015"));
		assertEquals(2, statistik.gibVerkaufsStatistik("Mars", df.parse("01.01.2015")));
		assertEquals(1, statistik.gibVerkaufsStatistik("Mars", df.parse("02.01.2015")));

		assertEquals(0, statistik.gibVerkaufsStatistik("Twix", df.parse("01.01.2015")));
		Ware twix = new Ware("Twix", 140, df.parse("01.02.2016"));
		statistik.verkaufteWareHinzufuegen(twix, df.parse("01.01.2015"));
		assertEquals(1, statistik.gibVerkaufsStatistik("Twix", df.parse("01.01.2015")));
		statistik.verkaufteWareHinzufuegen(twix, df.parse("02.01.2015"));
		assertEquals(2, statistik.gibVerkaufsStatistik("Twix", df.parse("01.01.2015")));
		assertEquals(1, statistik.gibVerkaufsStatistik("Twix", df.parse("02.01.2015")));
	}

}
