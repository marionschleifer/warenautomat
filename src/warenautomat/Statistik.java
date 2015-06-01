package warenautomat;

import java.util.ArrayList;
import java.util.Date;

public class Statistik {

	ArrayList<StatistikEintrag> statistik = new ArrayList<StatistikEintrag>();

	public void verkaufteWareHinzufuegen(Ware ware, Date datumVerkauft) {
		StatistikEintrag neuerEintrag = new StatistikEintrag(ware.getName(), ware.getPrice(), datumVerkauft);
		statistik.add(neuerEintrag);
	}

	public int gibBetragVerkaufteWare() {
		int betrag = 0;
		for (StatistikEintrag statistikEintrag : statistik) {
			betrag += statistikEintrag.getPrice();
		}
		return betrag;
	}

	public int gibVerkaufsStatistik(String name, Date startDatum) {
		int anzahlVerkauft = 0;
		for (StatistikEintrag statistikEintrag : statistik) {
			if (startDatum.compareTo(statistikEintrag.getVerkauftAm()) <= 0 && name.equals(statistikEintrag.getName())) {
				anzahlVerkauft += 1;
			}
		}
		return anzahlVerkauft;
	}
}
