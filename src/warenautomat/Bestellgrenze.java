package warenautomat;

public class Bestellgrenze {

	private String name;
	private int bestellGrenze;
	private int bestellAnzahl;

	public Bestellgrenze(String name, int bestellGrenze, int bestellAnzahl) {
		this.name = name;
		this.bestellGrenze = bestellGrenze;
		this.bestellAnzahl = bestellAnzahl;
		
	}
	
	public String getName() {
		return name;
	}
	
	public int getBestellGrenze() {
		return bestellGrenze;
	}
	
	public int getBestellAnzahl() {
		return bestellAnzahl;
	}

}
