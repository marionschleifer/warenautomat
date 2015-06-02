import warenautomat.Automat;
import warenautomat.SystemSoftware;
import warenautomat.WareTest;

public class Main {
	public static void main(String[] args) {
		Automat automat = new Automat();
		SystemSoftware.erzeugeGUI(automat);
		automat.fuelleFach(1, "Mars", 1.5, WareTest.getDate("1.1.2015"));
		automat.fuelleFach(2, "Snickers", 3.5, WareTest.getDate("1.1.2016"));
		automat.drehen();
		automat.fuelleFach(6, "KitKat", 2.1, WareTest.getDate("1.1.2018"));
	}

}
