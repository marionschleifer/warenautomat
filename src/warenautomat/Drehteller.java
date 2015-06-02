package warenautomat;

public class Drehteller {

	public static final int NR_FAECHER = 16;
	private Fach[] mFach = new Fach[NR_FAECHER];
	private int fachAmAusgang = 0;

	public Drehteller() {
		for (int i = 0; i < mFach.length; i++) {
			mFach[i] = new Fach();
		}
	}

	public void fuelleFach(Ware ware) {
		Fach fach = mFach[fachAmAusgang];
		fach.fuelleFach(ware);
	}

	public void drehen() {
		if (fachAmAusgang < NR_FAECHER - 1) {
			fachAmAusgang++;
		} else {
			fachAmAusgang = 0;
		}
	}

	public Ware getWare() {
		return mFach[fachAmAusgang].getWare();
	}

	public int getFachAmAusgang() {
		return fachAmAusgang;
	}

	public int gibTotalenWarenWert() {
		int wert = 0;
		for (Fach fach : mFach) {
			if (fach.istWareImFach()) {
				wert += fach.getWare().getPrice();
			}
		}
		return wert;
	}

	public int gibWarenMenge(String warenName) {
		int menge = 0;
		for (Fach fach : mFach) {
			if ((fach.istWareImFach()) && (warenName.equals(fach.getWare().getName()))) {
				menge += 1;
			}
		}
		return menge;
	}

}
