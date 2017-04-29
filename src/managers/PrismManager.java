package managers;

import prisms.Prism;

import java.util.ArrayList;
import java.util.List;

public class PrismManager {
	
	private List<Prism> prisms = new ArrayList<Prism>();
	
	public List<Prism> getPrisms() {
		return this.prisms;
	}
	
	public void addPrism(Prism prism) {
		this.prisms.add(prism);
		
	}
	
	public void removePrism(Prism prism) {
		this.prisms.remove(prism);
	}

}
