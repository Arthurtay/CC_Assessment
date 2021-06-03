import java.io.Serializable;

public class Person implements Serializable{

	String nric;
	String name;
	String location;
	String time;
    int suspectCovid;
    //check out or in
    int recordType;
    
	 public Person(String nric, String name, String location,String time) {
		 
		 this.nric = nric;
		 this.name = name;
		 this.location = location;
		 this.time = time;
		 this.suspectCovid = suspectCovid;
		 this.recordType = recordType;
	 } 
	 
	 
	 public void setNric(String newNric) {
		 this.nric = newNric;
	 }
	 
	 public void setName(String newName) {
		 this.name = newName;
	 }
	 
	 public void setLocation(String newLocation) {
		 this.location = newLocation;
	 }
	 
	 public void setTime(String newTime) {
		 this.time = newTime;
	 }
	 
	 public void setSuspect(int newSuspect) {
		 this.suspectCovid = newSuspect;
	 }
	 
	 public void setRecordType(int newRecordType) {
		 this.recordType = newRecordType;
	 }
	 
	 public String getNric() {
		 return this.nric;
	 }
	 
	 public String getName() {
		 return this.name;
	 }
	 
	 public String getLocation() {
		 return this.location;
	 }
	 
	 public String getTime() {
		 return this.time;
	 }
	 
	 public int getSuspect() {
		 return this.suspectCovid;
	 }
	 
	 public int getRecordType() {
		 return this.recordType;
	 }	
}
