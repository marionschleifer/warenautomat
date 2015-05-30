//==============================================================================
// Project   : Master of Advanced Studies in Software-Engineering 2015
// Modul     : Projektarbeit OO Softwareentwicklung "Warenautomat"
//             Teil: Design&Implementation
// Title     : CheckIn-Test-Applikation
// Tab-Width : 2
/*///===========================================================================

* Description:
Test-Applikation um den Check-In des Waren-Automaten zu testen.

$Revision    : 1.7 $  $Date: 2015/05/24 18:59:15 $ 
/*///===========================================================================
//       1         2         3         4         5         6         7         8
//345678901234567890123456789012345678901234567890123456789012345678901234567890
//==============================================================================


import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;

import warenautomat.*;

public class CheckInTest1 {

  static public void main(String pArgs[]) throws ParseException {

  DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
  
  // Test der Automaten-Schnittstelle:
  {
    Automat automat = new Automat(); 
    automat.fuelleFach(1, "Name", 1.0, df. parse(" 01.01.2100"));
    Kasse kasse = automat.gibKasse();
    automat.drehen();
    boolean b = automat.oeffnen(1);
    double d = automat.gibTotalenWarenWert();
    int anzahl = automat.gibVerkaufsStatistik("Name", df. parse(" 01.01.2100"));
  }
  
  // Test der Kassen-Schnittstelle:
  {
    Automat automat = new Automat(); 
    Kasse kasse = automat.gibKasse();
    if (kasse == null) {
      kasse = new Kasse();
    }
    int result = kasse.fuelleKasse(1.0, 10);
    kasse.fuelleKasseBestaetigung();

    kasse.einnehmen(1.0);
    kasse.gibWechselGeld();
    kasse.gibBetragVerkaufteWaren();
  }


  } // main()

} // CheckInTest

      

/* Session-Log:
SystemSoftware::zeigeWarenPreisAn():  1: 1.0
SystemSoftware::zeigeVerfallsDatum(): 1: 1
SystemSoftware::zeigeWarenPreisAn():  1: 0.0
SystemSoftware::zeigeVerfallsDatum(): 1: 0
SystemSoftware::zeigeWarenPreisAn():  2: 0.0
SystemSoftware::zeigeVerfallsDatum(): 2: 0
SystemSoftware::zeigeWarenPreisAn():  3: 0.0
SystemSoftware::zeigeVerfallsDatum(): 3: 0
SystemSoftware::zeigeWarenPreisAn():  4: 0.0
SystemSoftware::zeigeVerfallsDatum(): 4: 0
SystemSoftware::zeigeWarenPreisAn():  5: 0.0
SystemSoftware::zeigeVerfallsDatum(): 5: 0
SystemSoftware::zeigeWarenPreisAn():  6: 0.0
SystemSoftware::zeigeVerfallsDatum(): 6: 0
SystemSoftware::zeigeWarenPreisAn():  7: 0.0
SystemSoftware::zeigeVerfallsDatum(): 7: 0
Drehteller::oeffnen(): mDrehtellerNr = 1 / mFachVorOeffnung = 2
Drehteller::oeffnen() : Fach ist leer !
SystemSoftware::zeigeBetragAn(): 1.0
SystemSoftware::auswerfenWechselGeld(): 1.0
SystemSoftware::zeigeBetragAn(): 0.0

*/
