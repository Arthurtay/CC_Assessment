import java.io.Serializable;

public class InfectedLocation implements Serializable {

	String location;
	String dateInfection;
	String datePostInfection;
	
	public InfectedLocation(String location, String dateInfection, String datePostInfection) {
		this.location = location;
		this.dateInfection = dateInfection;
		this.datePostInfection = datePostInfection;
	}
}
