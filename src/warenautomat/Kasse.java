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

	/**
	 * Standard-Konstruktor. <br>
	 * Führt die nötigen Initialisierungen durch.
	 */
	public Kasse() {
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
	 *            Betrag der Münzart in Franken.
	 * @param pAnzahl
	 *            Anzahl der neu eingelegten Münzen.
	 * @return Wenn es genügend Platz in der Münzsäule hat: die Anzahl Münzen
	 *         welche eingelegt werden (d.h. pAnzahl). <br>
	 *         Wenn es nicht genügend Platz hat: die Anzahl Münzen welche nicht
	 *         Platz haben werden, als negative Zahl (z.B. -20). <br>
	 *         Wenn ein nicht unterstützter Münzbetrag übergeben wurde: -200
	 */
	public int fuelleKasse(double pMuenzenBetrag, int pAnzahl) {
		int rappen = Kasse.frankenZuRappen(pMuenzenBetrag);
		Muenzsaeule muenzsaeule = muenzsaeulen.get(rappen);
		if (muenzsaeule == null) {
			return -200;
		}
		if (muenzsaeule.hatPlatz(pAnzahl)) {
			muenzsaeule.addCoins(pAnzahl);
			return pAnzahl;
		}
		return -pAnzahl + muenzsaeule.vorhandenerPlatz();

	}

	/**
	 * Diese Methode wird aufgerufen nachdem das Personal beim Geldauffüllen den
	 * Knopf "Bestätigen" gedrückt hat. (siehe Use-Case "Kasse auffüllen"). <br>
	 * Verbucht die Münzen gemäss dem vorangegangenen Aufruf der Methode
	 * <code> fuelleKasse() </code>.
	 */
	public void fuelleKasseBestaetigung() {

		// TODO

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

		return false; // TODO

	}

	/**
	 * Bewirkt den Auswurf des Restbetrages.
	 */
	public void gibWechselGeld() {

		// TODO

	}

	/**
	 * Gibt den Gesamtbetrag der bisher verkauften Waren zurück. <br>
	 * Analyse: Abgeleitetes Attribut.
	 * 
	 * @return Gesamtbetrag der bisher verkauften Waren.
	 */
	public double gibBetragVerkaufteWaren() {

		return 0.0; // TODO

	}

	public static int frankenZuRappen(double franken) {
		return (int) Math.round(franken * 100);
	}

}
