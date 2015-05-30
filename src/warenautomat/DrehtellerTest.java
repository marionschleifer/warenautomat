package warenautomat;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;

import org.junit.Test;

public class DrehtellerTest {
	DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);

	@Test
	public void testIstOffen() {
		Drehteller drehteller = new Drehteller();
		assertEquals(false, drehteller.istOffen());
	}
	
	@Test
	public void testFuelleFach() throws ParseException {
		Drehteller mDrehteller = new Drehteller();
		Ware ware = new Ware("Mars", 1.2, df.parse("01.01.2016"));
		mDrehteller.fuelleFach(ware);
		assertEquals(ware, mDrehteller.getWare());
	}
	
	@Test
	public void testDrehen() {
		Drehteller mDrehteller = new Drehteller();
		mDrehteller.drehen();
		assertEquals(2, mDrehteller.getFachAmAusgang());
		mDrehteller.drehen();
		assertEquals(3, mDrehteller.getFachAmAusgang());
	}
	
	@Test
	public void testEntriegeln() throws ParseException {
		Drehteller drehteller = new Drehteller();
		Ware ware = new Ware("Mars", 1.2, df.parse("01.01.2016"));
		drehteller.fuelleFach(ware);
		assertEquals(false, drehteller.istOffen());
		drehteller.entriegeln();
		assertEquals(true, drehteller.istOffen());
	}
	
	@Test
	public void testschliessen() throws ParseException {
		Drehteller drehteller = new Drehteller();
		Ware ware = new Ware("Mars", 1.2, df.parse("01.01.2016"));
		drehteller.fuelleFach(ware);
		drehteller.entriegeln();
		assertEquals(true, drehteller.istOffen());
		drehteller.schliessen();
		assertEquals(false, drehteller.istOffen());
	}

}
