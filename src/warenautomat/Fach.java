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

}
