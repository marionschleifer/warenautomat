package warenautomat;

public class Fach {

	private Ware ware;

	public boolean istWareImFach() {
		return getWare() != null;
	}

	public Ware getWare() {
		return ware;
	}

	public void fuelleFach(Ware ware) {
		this.ware = ware;
		
	}
	
	public int zustand() {
		if (getWare() == null) {
			return 1;
		} else {
			getWare().getDate(); 
		} 
		
	}
//	
//	methode fuer lampe get zustand: ist es leer, sonst abgelaufen oder gut?

}
