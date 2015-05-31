package warenautomat;

import static org.junit.Assert.*;

import org.junit.Test;

public class MuenzsaeuleTest {

	@Test
	public void testCreateMuenzsaeule() {
		Muenzsaeule muenzsaeule = new Muenzsaeule(10);
		assertEquals(10, muenzsaeule.getMuenzart());
		assertEquals(0, muenzsaeule.getMenge());
	}

	@Test
	public void testAddCoins() {
		Muenzsaeule muenzsaeule = new Muenzsaeule(10);
		muenzsaeule.addCoins(5);
		assertEquals(5, muenzsaeule.getMenge());
		muenzsaeule.addCoins(50);
		assertEquals(55, muenzsaeule.getMenge());
		muenzsaeule.addCoins(45);
		assertEquals(100, muenzsaeule.getMenge());
		try {
			muenzsaeule.addCoins(1);
			assertTrue(false);
		} catch (Exception ex) {

		}
		assertEquals(100, muenzsaeule.getMenge());
	}

	@Test
	public void testHatPlatz() {
		Muenzsaeule muenzsaeule = new Muenzsaeule(10);
		muenzsaeule.addCoins(90);
		assertEquals(true, muenzsaeule.hatPlatz(9));
		muenzsaeule.addCoins(9);
		assertEquals(true, muenzsaeule.hatPlatz(1));
		muenzsaeule.addCoins(1);
		assertEquals(false, muenzsaeule.hatPlatz(1));
	}

	@Test
	public void testKeineNegativenZahlen() {
		Muenzsaeule muenzsaeule = new Muenzsaeule(10);
		try {
			muenzsaeule.addCoins(-2);
			assertTrue(false);
		} catch (Exception ex) {
		}
	}

	@Test
	public void testRemoveCoins() {
		Muenzsaeule muenzsaeule = new Muenzsaeule(10);
		muenzsaeule.addCoins(9);
		muenzsaeule.removeCoins(4);
		assertEquals(5, muenzsaeule.getMenge());
		try {
			muenzsaeule.removeCoins(6);
			assertTrue(false);
		} catch (Exception ex) {
		}
	}
	
	@Test
	public void testVorhandenerPlatz() {
		Muenzsaeule muenzsaeule = new Muenzsaeule(10);
		assertEquals(100, muenzsaeule.vorhandenerPlatz());
		muenzsaeule.addCoins(20);
		assertEquals(80, muenzsaeule.vorhandenerPlatz());
		muenzsaeule.addCoins(79);
		assertEquals(1, muenzsaeule.vorhandenerPlatz());
		muenzsaeule.addCoins(1);
		assertEquals(0, muenzsaeule.vorhandenerPlatz());
	}

}
