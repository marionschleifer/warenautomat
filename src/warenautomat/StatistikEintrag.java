package warenautomat;

import java.util.Date;

public class StatistikEintrag {

	private String name;
	private int price;
	private Date verkauftAm;

	public StatistikEintrag(String name, int price, Date verkauftAm) {
		this.name = name;
		this.price = price;
		this.verkauftAm = verkauftAm;
		
	}

	public String getName() {
		return name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public Date getVerkauftAm() {
		return verkauftAm;
	}

}
