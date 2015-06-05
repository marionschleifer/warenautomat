package warenautomat;

import java.util.*;

import warenautomat.SystemSoftware;

/**
 * Die Kasse verwaltet das eingenommene Geld sowie das Wechselgeld. <br>
 * Die Kasse hat fünf Münz-Säulen für: <br>
 * - 10 Rappen <br>
 * - 20 Rappen <br>
 * - 50 Rappen <br>
 * - 1 Franken <br>
 * - 2 Franken <br>
 */
public class Kasse {

	private Map<Integer, Muenzsaeule> muenzsaeulen = new HashMap<Integer, Muenzsaeule>();
	private Muenzsaeule zuFuellendeMuenzsaeule;
	private int zuFuellendeMuenzen;
	private int zurZeitEingenommen;
	private Statistik statistik;

	/**
	 * Standard-Konstruktor. <br>
	 * Führt die nötigen Initialisierungen durch.
	 */
	public Kasse() {
		this.statistik = new Statistik();
		erstelleMuenzsaeulen();
	}

	public Kasse(Statistik statistik) {
		this.statistik = statistik;
		erstelleMuenzsaeulen();
	}

	public void erstelleMuenzsaeulen() {
		muenzsaeulen.put(200, new Muenzsaeule(200));
		muenzsaeulen.put(100, new Muenzsaeule(100));
		muenzsaeulen.put(50, new Muenzsaeule(50));
		muenzsaeulen.put(20, new Muenzsaeule(20));
		muenzsaeulen.put(10, new Muenzsaeule(10));
	}

	/**
	 * Diese Methode wird aufgerufen nachdem das Personal beim Geldauffüllen die
	 * Münzart und die Anzahl der Münzen über die Tastatur eingegeben hat (siehe
	 * Use-Case "Kasse auffüllen").
	 * 
	 * @param pMuenzenBetrag
	 *            Betrag der Münzart in Franken. Muss positiv sein.
	 * @param pAnzahl
	 *            Anzahl der neu eingelegten Münzen.
	 * @return Wenn es genügend Platz in der Münzsäule hat: die Anzahl Münzen
	 *         welche eingelegt werden (d.h. pAnzahl). <br>
	 *         Wenn es nicht genügend Platz hat: die Anzahl Münzen welche nicht
	 *         Platz haben werden, als negative Zahl (z.B. -20). <br>
	 *         Wenn ein nicht unterstützter Münzbetrag übergeben wurde: -200
	 */
	public int fuelleKasse(double pMuenzenBetrag, int pAnzahl) {
		zuFuellendeMuenzsaeule = null;

		int rappen = Kasse.frankenZuRappen(pMuenzenBetrag);
		Muenzsaeule muenzsaeule = muenzsaeulen.get(rappen);
		if (muenzsaeule == null) {
			return -200;
		}
		if (!muenzsaeule.hatPlatz(pAnzahl)) {
			zuFuellendeMuenzsaeule = muenzsaeule;
			zuFuellendeMuenzen = muenzsaeule.vorhandenerPlatz();
			return -pAnzahl + muenzsaeule.vorhandenerPlatz();
		}
		zuFuellendeMuenzsaeule = muenzsaeule;
		zuFuellendeMuenzen = pAnzahl;

		return pAnzahl;
	}

	/**
	 * Diese Methode wird aufgerufen nachdem das Personal beim Geldauffüllen den
	 * Knopf "Bestätigen" gedrückt hat. (siehe Use-Case "Kasse auffüllen"). <br>
	 * Verbucht die Münzen gemäss dem vorangegangenen Aufruf der Methode
	 * <code> fuelleKasse() </code>.
	 */
	public void fuelleKasseBestaetigung() {
		zuFuellendeMuenzsaeule.addCoins(zuFuellendeMuenzen);
		zuFuellendeMuenzsaeule = null;
	}

	/**
	 * Diese Methode wird aufgerufen wenn ein Kunde eine Münze eingeworfen hat. <br>
	 * Führt den eingenommenen Betrag entsprechend nach. <br>
	 * Stellt den nach dem Einwerfen vorhandenen Betrag im Kassen-Display dar. <br>
	 * Eingenommenes Geld steht sofort als Wechselgeld zur Verfügung. <br>
	 * Die Münzen werden von der Hardware-Kasse auf Falschgeld, Fremdwährung und
	 * nicht unterstützte Münzarten geprüft, d.h. diese Methode wird nur
	 * aufgerufen wenn ein Münzeinwurf soweit erfolgreich war. <br>
	 * Ist die Münzsäule voll (d.h. 100 Münzen waren vor dem Einwurf bereits
	 * darin enthalten), so wird mittels
	 * <code> SystemSoftware.auswerfenWechselGeld() </code> unmittelbar ein
	 * entsprechender Münz-Auswurf ausgeführt. <br>
	 * Hinweis: eine Hardware-Münzsäule hat jeweils effektiv Platz für 101
	 * Münzen.
	 * 
	 * @param pMuenzenBetrag
	 *            Der Betrag der neu eingeworfenen Münze in Franken.
	 * @return <code> true </code>, wenn er Einwurf erfolgreich war. <br>
	 *         <code> false </code>, wenn Münzsäule bereits voll war.
	 */
	public boolean einnehmen(double pMuenzenBetrag) {
		int rappen = Kasse.frankenZuRappen(pMuenzenBetrag);
		Muenzsaeule muenzsaeule = muenzsaeulen.get(rappen);
		if (muenzsaeule.hatPlatz(1)) {
			muenzsaeule.addCoins(1);
			zurZeitEingenommen += rappen;
			SystemSoftware.zeigeBetragAn(Kasse.rappenZuFranken(gibZurZeitEingenommen()));
			return true;
		} else {
			SystemSoftware.auswerfenWechselGeld(pMuenzenBetrag);
			return false;
		}
	}

	/**
	 * Bewirkt den Auswurf des Restbetrages.
	 */
	public void gibWechselGeld() {
		while (zurZeitEingenommen != 0) {
			if (genugGrossUndGenugMuenzen(200)) {
				gibMuenzeZurueck(200);
			} else if (genugGrossUndGenugMuenzen(100)) {
				gibMuenzeZurueck(100);
			} else if (genugGrossUndGenugMuenzen(50)) {
				gibMuenzeZurueck(50);
			} else if (genugGrossUndGenugMuenzen(20)) {
				gibMuenzeZurueck(20);
			} else if (genugGrossUndGenugMuenzen(10)) {
				gibMuenzeZurueck(10);
			}
		}
		SystemSoftware.zeigeBetragAn(0);
	}

	private void gibMuenzeZurueck(int muenzBetragRappen) {
		muenzsaeulen.get(muenzBetragRappen).removeCoins(1);
		zurZeitEingenommen -= muenzBetragRappen;
		SystemSoftware.auswerfenWechselGeld(Kasse.rappenZuFranken(muenzBetragRappen));
	}

	private boolean genugGrossUndGenugMuenzen(int muenzBetragRappen) {
		return zurZeitEingenommen >= muenzBetragRappen && muenzsaeulen.get(muenzBetragRappen).getMenge() > 0;
	}

	/**
	 * Gibt den Gesamtbetrag der bisher verkauften Waren zurück. <br>
	 * Analyse: Abgeleitetes Attribut.
	 * 
	 * @return Gesamtbetrag der bisher verkauften Waren.
	 */
	public double gibBetragVerkaufteWaren() {
		return Kasse.rappenZuFranken(statistik.gibBetragVerkaufteWare());
	}

	public static int frankenZuRappen(double franken) {
		return (int) Math.round(franken * 100);
	}

	public static double rappenZuFranken(int rappen) {
		return rappen / 100.0;
	}

	public int gibZurZeitEingenommen() {
		return zurZeitEingenommen;
	}

	public void bezahleWare(Ware ware) {
		zurZeitEingenommen -= ware.getPrice();
		statistik.verkaufteWareHinzufuegen(ware, SystemSoftware.gibAktuellesDatum());
	}

	public Muenzsaeule gibMuenzsaeule(int rappen) {
		return muenzsaeulen.get(rappen);
	}

	public boolean hatGenugWechselgeld(int restbetrag) {
		int bezogene200 = 0;
		int bezogene100 = 0;
		int bezogene50 = 0;
		int bezogene20 = 0;
		int bezogene10 = 0;
		while (restbetrag != 0) {
			if (restbetrag >= 200 && muenzsaeulen.get(200).getMenge() > bezogene200) {
				bezogene200 += 1;
				restbetrag -= 200;
			} else if (restbetrag >= 100 && muenzsaeulen.get(100).getMenge() > bezogene100) {
				bezogene100 += 1;
				restbetrag -= 100;
			} else if (restbetrag >= 50 && muenzsaeulen.get(50).getMenge() > bezogene50) {
				bezogene50 += 1;
				restbetrag -= 50;
			} else if (restbetrag >= 20 && muenzsaeulen.get(20).getMenge() > bezogene20) {
				bezogene20 += 1;
				restbetrag -= 20;
			} else if (restbetrag >= 10 && muenzsaeulen.get(10).getMenge() > bezogene10) {
				bezogene10 += 1;
				restbetrag -= 10;
			} else {
				return false;
			}
		}
		return true;
	}

}
