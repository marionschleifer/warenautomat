package warenautomat;

import static org.junit.Assert.*;

import org.junit.Test;

public class FachTest {

	@Test
	public void testWareImFach() {
		Ware ware = WareTest.getWare();
		Fach fach = new Fach();
		assertEquals(false, fach.istWareImFach());
		fach.fuelleFach(ware);
		assertEquals(true, fach.istWareImFach());
		assertEquals(ware, fach.getWare());
	}

}
