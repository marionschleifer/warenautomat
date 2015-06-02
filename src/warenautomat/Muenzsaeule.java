package warenautomat;

public class Muenzsaeule {

	private static final int MAX_MUENZEN = 100;
	private int muenzart;
	private int menge = 0;

	public Muenzsaeule(int muenzart) {
		this.muenzart = muenzart;
	}

	public int getMuenzart() {
		return muenzart;
	}

	public int getMenge() {
		return menge;
	}

	public void addCoins(int anzahlNeueMuenzen) {
		if (anzahlNeueMuenzen < 0) {
			throw new RuntimeException("Anzahl Muenzen muss positiv sein");
		}
		if (!hatPlatz(anzahlNeueMuenzen)) {
			throw new RuntimeException("Es hat kein Platz fuer weitere Muenzen");
		}

		menge += anzahlNeueMuenzen;
		
		SystemSoftware.zeigeMuenzenInGui(getMuenzart() / 100.0, getMenge());
	}

	public boolean hatPlatz(int anzahlNeueMuenzen) {
		return menge + anzahlNeueMuenzen <= MAX_MUENZEN;
	}

	public void removeCoins(int anzahlMuenzen) {
		if (menge - anzahlMuenzen < 0) {
			throw new RuntimeException("Zu wenig Muenzen im Fach");
		}
		menge -= anzahlMuenzen;

		SystemSoftware.zeigeMuenzenInGui(getMuenzart() / 100.0, getMenge());
	}

	public int vorhandenerPlatz() {
		return MAX_MUENZEN - menge;
	}
}
