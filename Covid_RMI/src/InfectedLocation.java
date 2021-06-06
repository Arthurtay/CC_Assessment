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
	
	public void setLocation(String newLocation) {
		this.location = newLocation;
	}
	
	public void setDateInfection(String newDateInfection) {
		this.dateInfection = newDateInfection;
	}
	
	public void setNewDatePost(String newDatePost) {
		this.datePostInfection = newDatePost;
	}
	
	public String getLocation(){
		return this.location;
	}
	
	public String getDateInfection() {
		return this.dateInfection;
	}
	
	public String getDatePost() {
		return this.datePostInfection;
	}
}
