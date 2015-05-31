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
		return price;
	}

	public Date getDate() {
		return date;
	}	

}
