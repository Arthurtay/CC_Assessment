import java.io.Serializable;

public class Person implements Serializable{

	String nric;
	String name;
	String location;
	String time;
 
    
	 public Person(String name, String nric, String location,String time) {
		 this.name = name;
		 this.nric = nric;
		 this.location = location;
		 this.time = time;

	 } 
	 
	 
	 
	 
	 public void setName(String newName) {
		 this.name = newName;
	 }
	 
	 public void setNric(String newNric) {
		 this.nric = newNric;
	 }
	 
	 public void setLocation(String newLocation) {
		 this.location = newLocation;
	 }
	 
	 public void setTime(String newTime) {
		 this.time = newTime;
	 }
	 
	 
	 public String getName() {
		 return this.name;
	 }
	 
	 public String getNric() {
		 return this.nric;
	 }
	 
	 public String getLocation() {
		 return this.location;
	 }
	 
	 public String getTime() {
		 return this.time;
	 }
	 

}
