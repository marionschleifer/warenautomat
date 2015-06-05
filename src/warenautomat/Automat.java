package warenautomat;

import java.util.Date;
import java.util.HashMap;

/**
 * Der Automat besteht aus 7 Drehtellern, welche wiederum je aus 16 Fächern
 * bestehen. <br>
 * Der erste Drehteller und das jeweils erste Fach haben jeweils die Nummer 1
 * (nicht 0!). <br>
 * Im Weitern hat der Automat eine Kasse. Diese wird vom Automaten instanziert.
 */
public class Automat {

	private static final int NR_DREHTELLER = 7;
	private Drehteller[] drehtellerListe = new Drehteller[NR_DREHTELLER];
	private Kasse kasse;
	private Statistik statistik = new Statistik();
	private HashMap<String, Bestellgrenze> bestellgrenzen = new HashMap<String, Bestellgrenze>();

	/**
	 * Der Standard-Konstruktor. <br>
	 * Führt die nötigen Initialisierungen durch (u.a. wird darin die Kasse
	 * instanziert).
	 */
	public Automat() {
		for (int i = 0; i < drehtellerListe.length; i++) {
			drehtellerListe[i] = new Drehteller();
		}
		kasse = new Kasse(statistik);
	}

	/**
	 * Füllt ein Fach mit Ware. <br>
	 * Wenn das Service-Personal den Automaten füllt, wird mit einem
	 * Bar-Code-Leser zuerst die Ware gescannt. <br>
	 * Daraufhin wird die Schiebe-Tür geöffnet. <br>
	 * Das Service-Personal legt die neue Ware ins Fach und schliesst das Fach. <br>
	 * Die Hardware resp. System-Software ruft die Methode
	 * <code> Automat.fuelleFach() </code> auf.
	 * 
	 * @param pDrehtellerNr
	 *            Der Drehteller bei welchem das Fach hinter der Schiebe-Türe
	 *            gefüllt wird. <br>
	 *            Nummerierung beginnt mit 1 (nicht 0)!
	 * @param pWarenName
	 *            Der Name der neuen Ware.
	 * @param pPreis
	 *            Der Preis der neuen Ware.
	 * @param pVerfallsDatum
	 *            Das Verfallsdatum der neuen Ware.
	 */
	public void fuelleFach(int pDrehtellerNr, String pWarenName, double pPreis, Date pVerfallsDatum) {
		Drehteller aufzufuellenderDrehteller = gibDrehteller(pDrehtellerNr);
		Ware ware = new Ware(pWarenName, Kasse.frankenZuRappen(pPreis), pVerfallsDatum);
		SystemSoftware.zeigeWarenPreisAn(pDrehtellerNr, pPreis);
		SystemSoftware.zeigeVerfallsDatum(pDrehtellerNr, ware.gibZustand());
		aufzufuellenderDrehteller.fuelleFach(ware);
		SystemSoftware.zeigeWareInGui(pDrehtellerNr, pWarenName, pVerfallsDatum);
	}

	/**
	 * Gibt die Objekt-Referenz auf die <em> Kasse </em> zurück.
	 */
	public Kasse gibKasse() {
		return kasse;
	}

	/**
	 * Wird von der System-Software jedesmal aufgerufen wenn der gelbe
	 * Dreh-Knopf gedrückt wird. <br>
	 * Die Applikations-Software führt die Drehteller-Anzeigen nach (Warenpreis,
	 * Verfallsdatum). <br>
	 * Das Ansteuern des Drehteller-Motors übernimmt hat die System-Software
	 * (muss nicht von der Applikations-Software gesteuert werden.). <br>
	 * Die System-Software stellt sicher, dass <em> drehen </em> nicht
	 * durchgeführt wird wenn ein Fach offen ist.
	 */
	public void drehen() {
		SystemSoftware.dreheWarenInGui();
		for (int drehtellerNr = 1; drehtellerNr <= drehtellerListe.length; drehtellerNr++) {
			Drehteller drehteller = gibDrehteller(drehtellerNr);
			drehteller.drehen();
			Ware ware = drehteller.getWare();
			if (ware == null) {
				SystemSoftware.zeigeWarenPreisAn(drehtellerNr, 0);
				SystemSoftware.zeigeVerfallsDatum(drehtellerNr, 0);
			} else {
				SystemSoftware.zeigeWarenPreisAn(drehtellerNr, Kasse.rappenZuFranken(ware.getPrice()));
				SystemSoftware.zeigeVerfallsDatum(drehtellerNr, ware.gibZustand());
			}
		}
	}

	/**
	 * Beim Versuch eine Schiebetüre zu öffnen ruft die System-Software die
	 * Methode <code> oeffnen() </code> der Klasse <em> Automat </em> mit der
	 * Drehteller-Nummer als Parameter auf. <br>
	 * Es wird überprüft ob alles o.k. ist: <br>
	 * - Fach nicht leer <br>
	 * - Verfallsdatum noch nicht erreicht <br>
	 * - genug Geld eingeworfen <br>
	 * - genug Wechselgeld vorhanden <br>
	 * Wenn nicht genug Geld eingeworfen wurde, wird dies mit
	 * <code> SystemSoftware.zeigeZuWenigGeldAn() </code> signalisiert. <br>
	 * Wenn nicht genug Wechselgeld vorhanden ist wird dies mit
	 * <code> SystemSoftware.zeigeZuWenigWechselGeldAn() </code> signalisiert. <br>
	 * Wenn o.k. wird entriegelt (<code> SystemSoftware.entriegeln() </code>)
	 * und positives Resultat zurückgegeben, sonst negatives Resultat. <br>
	 * Es wird von der System-Software sichergestellt, dass zu einem bestimmten
	 * Zeitpunkt nur eine Schiebetüre offen sein kann.
	 * 
	 * @param pDrehtellerNr
	 *            Der Drehteller bei welchem versucht wird die Schiebe-Türe zu
	 *            öffnen. <br>
	 *            Nummerierung beginnt mit 1 (nicht 0)!
	 * @return Wenn alles o.k. <code> true </code>, sonst <code> false </code>.
	 */
	public boolean oeffnen(int pDrehtellerNr) {
		if (gibFachInhalt(pDrehtellerNr) != null) {
			Ware ware = gibFachInhalt(pDrehtellerNr);

			if (ware.istAbgelaufen()) {
				return false;
			}

			if (kasse.gibZurZeitEingenommen() < ware.getPrice()) {
				SystemSoftware.zeigeZuWenigGeldAn();
				return false;
			}

			if (!kasse.hatGenugWechselgeld(ware.getPrice())) {
				SystemSoftware.zeigeZuWenigWechselGeldAn();
				return false;
			}

			kasse.bezahleWare(ware);
			kasse.gibWechselGeld();

			SystemSoftware.entriegeln(pDrehtellerNr);
			SystemSoftware.zeigeWarenPreisAn(pDrehtellerNr, 0);
			SystemSoftware.zeigeVerfallsDatum(pDrehtellerNr, 0);

			gibDrehteller(pDrehtellerNr).fuelleFach(null);
			SystemSoftware.zeigeWareInGui(pDrehtellerNr, null, null);

			pruefeNachbestellung(ware);

			return true;
		}
		return false;

	}

	private void pruefeNachbestellung(Ware ware) {
		if (!grenzeErreicht(ware.getName()))
			return;
		
		Bestellgrenze bestellgrenze = getBestellGrenzen().get(ware.getName());
		SystemSoftware.bestellen(bestellgrenze.getName(), bestellgrenze.getBestellAnzahl());
	}

	/**
	 * Gibt den aktuellen Wert aller im Automaten enthaltenen Waren in Franken
	 * zurück. <br>
	 * Analyse: <br>
	 * Abgeleitetes Attribut. <br>
	 * 
	 * @return Der totale Warenwert des Automaten.
	 */
	public double gibTotalenWarenWert() {
		int totalerWert = 0;
		for (Drehteller drehteller : drehtellerListe) {
			totalerWert += drehteller.gibTotalenWarenWert();
		}
		return Kasse.rappenZuFranken(totalerWert);
	}

	/**
	 * Gibt die Anzahl der verkauften Ware <em> pName </em> seit (>=)
	 * <em> pDatum </em> Zahl zurück.
	 * 
	 * @param pName
	 *            Der Name der Ware nach welcher gesucht werden soll.
	 * @param pDatum
	 *            Das Datum seit welchem gesucht werden soll.
	 * @return Anzahl verkaufter Waren.
	 */
	public int gibVerkaufsStatistik(String pName, Date pDatum) {
		return statistik.gibVerkaufsStatistik(pName, pDatum);
	}

	public Ware gibFachInhalt(int drehtellerNr) {
		Ware ware = gibDrehteller(drehtellerNr).getWare();
		return ware;
	}

	public Drehteller gibDrehteller(int drehtellerNr) {
		return drehtellerListe[drehtellerNr - 1];
	}

	/**
	 * Konfiguration einer automatischen Bestellung. <br>
	 * Der Automat setzt automatisch Bestellungen ab mittels
	 * <code> SystemSoftware.bestellen() </code> wenn eine Ware ausgeht.
	 * 
	 * @param pWarenName
	 *            Warenname derjenigen Ware, für welche eine automatische
	 *            Bestellung konfiguriert wird.
	 * @param pGrenze
	 *            Grenze bei welcher Anzahl von verbleibenden, nicht
	 *            abgelaufener Waren im Automat eine Bestellung abgesetzt werden
	 *            soll (Bestellung wenn Waren-Anzahl nicht abgelaufener Waren <=
	 *            pGrenze).
	 * @param pBestellAnzahl
	 *            Wieviele neue Waren jeweils bestellt werden sollen.
	 */
	public void konfiguriereBestellung(String pWarenName, int pGrenze, int pBestellAnzahl) {
		Bestellgrenze bestellgrenze = new Bestellgrenze(pWarenName, pGrenze, pBestellAnzahl);
		bestellgrenzen.put(pWarenName, bestellgrenze);
	}

	public HashMap<String, Bestellgrenze> getBestellGrenzen() {
		return bestellgrenzen;
	}

	public boolean grenzeErreicht(String warenName) {
		int warenMenge = gibWarenMenge(warenName);
		Bestellgrenze bestellgrenze = bestellgrenzen.get(warenName);

		// Keine Bestellgrenze vorhanden
		if (bestellgrenze == null)
			return false;

		return warenMenge <= bestellgrenze.getBestellGrenze();
	}

	public int gibWarenMenge(String warenName) {
		int menge = 0;
		for (Drehteller drehteller : drehtellerListe) {
			menge += drehteller.gibWarenMenge(warenName);
		}
		return menge;
	}
}
