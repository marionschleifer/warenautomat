package warenautomat;

import java.util.Date;

/**
 * Der Automat besteht aus 7 Drehtellern welche wiederum je aus 16 Fächern
 * bestehen. <br>
 * Der erste Drehteller und das jeweils erste Fach haben jeweils die Nummer 1
 * (nicht 0!). <br>
 * Im Weitern hat der Automat eine Kasse. Diese wird vom Automaten instanziert.
 */
public class Automat {

	private static final int NR_DREHTELLER = 7;
	private Drehteller[] mDrehteller;
	private Kasse mKasse;

	/**
	 * Der Standard-Konstruktor. <br>
	 * Führt die nötigen Initialisierungen durch (u.a. wird darin die Kasse
	 * instanziert).
	 */
	public Automat() {

		for (int i = 0; i < mDrehteller.length; i++) {
			mDrehteller[i] = new Drehteller();
		}
		mKasse = new Kasse();

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

		Drehteller aufzufuellenderDrehteller = mDrehteller[pDrehtellerNr];
//		Ware ware = new Ware(pWarenName, pPreis, pVerfallsDatum);
//		aufzufuellenderDrehteller.fuelleFach(ware);

	}

	/**
	 * Gibt die Objekt-Referenz auf die <em> Kasse </em> zurück.
	 */
	public Kasse gibKasse() {
		return mKasse;
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

		for (Drehteller drehteller : mDrehteller) {
			int pDrehtellerNr;
			for (Drehteller einzelnerDrehteller : mDrehteller) {
				einzelnerDrehteller.drehen();
			}
			Ware ware = drehteller.getWare();
//			 SystemSoftware.zeigeWarenPreisAn(pDrehtellerNr, ware.getPrice());
//			 SystemSoftware.zeigeVerfallsDatum(pDrehtellerNr, pZustand);
			// // fach, da es leer sein koennte -> aus

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

		return false; // TODO

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

		return 0.0; // TODO

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

		return 0; // TODO

	}

}