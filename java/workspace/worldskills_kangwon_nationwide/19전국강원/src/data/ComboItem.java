package data;

public class ComboItem {
	
	public int id;
	public String text;
	
	
	
	public ComboItem(int id, String text) {
		this.id = id;
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
