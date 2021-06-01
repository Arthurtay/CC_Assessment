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
	 
	 
	 
	
	

	
	
	
	
	
}
