package warenautomat;

import java.util.Date;

public class Ware {

	private String name;
	private int price;
	private Date date;

	public Ware(String name, int price, Date date) {
		this.name = name;
		this.price = price;
		this.date = date;

	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		if(!istAbgelaufen()) return price;
		double discount = 0.1;
		return (int) (5 * Math.round(price * discount / 5.0));
	}

	public Date getDate() {
		return date;
	}

	public int gibZustand() {
		if (istAbgelaufen()) {
			return 2;
		} else
			return 1;
	}

	public boolean istAbgelaufen() {
		return SystemSoftware.gibAktuellesDatum().compareTo(getDate()) > 0;
	}

}
