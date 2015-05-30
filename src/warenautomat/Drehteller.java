package warenautomat;

import java.util.Date;

import warenautomat.SystemSoftware;

public class Drehteller {

	private static final int NR_FAECHER = 16;
	private Fach[] mFach = new Fach[NR_FAECHER];
	private int fachAmAusgang = 1;
	private boolean istOffen = false;

	public Drehteller() {
		for (int i = 0; i < mFach.length; i++) {
			mFach[i] = new Fach();
		}
	}

	public boolean istOffen() {
		return istOffen;
	}

	public void fuelleFach(Ware ware) {
		Fach fach = mFach[fachAmAusgang];
		fach.fuelleFach(ware);

	}

	public void drehen() {
		if (fachAmAusgang <= NR_FAECHER) {
			fachAmAusgang++;
		} else {
			fachAmAusgang = 1;
		}
	}

	public Ware getWare() {
		return mFach[fachAmAusgang].getWare();
	}

	public int getFachAmAusgang() {
		return fachAmAusgang;
	}
	
//	TODO entriegeln nur wenn genug Guthabene

	public boolean entriegeln() {  
		if (getWare() != null) {
			istOffen = true;
			return true;
		} else {
			return false;
		}

	}

	public void schliessen() {
		istOffen = false;

	}

}
