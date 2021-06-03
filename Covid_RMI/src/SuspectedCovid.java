import java.io.Serializable;

public class SuspectedCovid implements Serializable{
	String nric;
	String location;
	String dateSuspected;
	
	public SuspectedCovid(String nric, String location, String dateSuspected) {
		this.nric = nric;
		this.location = location;
		this.dateSuspected = dateSuspected;
	}
	
	public void setNric(String newNric) {
		this.nric = newNric;
	}
	
	public void setLocation(String newLocation) {
		this.location = newLocation;
	}
	
	public void setDateSuspected(String newDateSuspected) {
		this.dateSuspected = newDateSuspected;
	}
	
	public String getNric() {
		return this.nric;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public String getDateSuspected() {
		return this.dateSuspected;
	}
	
	
}
