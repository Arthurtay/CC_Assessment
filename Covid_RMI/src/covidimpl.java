
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.*;

public class covidimpl extends java.rmi.server.UnicastRemoteObject  implements covid, officer {

	
	private ArrayList<Person> Database = new ArrayList<Person>() ;
	
    public covidimpl() throws java.rmi.RemoteException {
        super();
       
    
  	  File file = new File("D:\\javaworkspace\\Covid_RMI\\src\\Database.txt");
	 
  	  try {
  		  BufferedReader br = new BufferedReader(new FileReader(file));
  		  String st;
  	  
  	  //continuous read file 
  		  while ((st = br.readLine()) != null) 
  		  {
  		 //For each line being read. split the text by delimiter comma.
  			  String[] tokens = st.split(","); 
  			  System.out.println(tokens[0]);
  			  System.out.println(tokens[1]);
  			  System.out.println(tokens[2]);
  			  Person ppl = new Person(tokens[0],tokens[1],tokens[1],tokens[1]); 
  		 
  			  Database.add(ppl); 
  		 
  			  System.out.println("This is the end of 1 line" );
  		
  		  }
  	  }
  	  catch(Exception e) {
  		  System.out.println(e);
  	  }
        
        //Implement and load data from text file
    }

    

	public void checkIn(Person p) throws RemoteException {
		// TODO Auto-generated method stub
	 // Person test = (Person) p; 
		 try {
		      
			  
			  File f  = new File("D:\\javaworkspace\\Covid_RMI\\src\\Database.txt");
			  PrintWriter write =   new PrintWriter(new FileOutputStream(f,true));
			  write.append(p.nric +","+ p.name +","+ p.location +","+ p.time + "\n");
			  write.close();  
			  System.out.println("check in sucessfully");
			  
	  		  Person ppl = new Person(p.nric,p.name,p.location,p.time); 
	  		  Database.add(ppl); 
	  		 
			  
			  
		 }catch(Exception e) {
			  System.out.println(e);
			 
		 }
		
	}


	public void checkOut(Person p) throws RemoteException {
		// TODO Auto-generated method stub
		 try {
		      
		
		      File f  = new File("D:\\javaworkspace\\Covid_RMI\\src\\Database.txt");
			  PrintWriter write =   new PrintWriter(new FileOutputStream(f,true));
			  write.append(p.nric +","+ p.name +","+ p.location +","+ p.time + "\n");
			  write.close();  
			  System.out.println("check out sucessfully");
			  
	  		  Person ppl = new Person(p.nric,p.name,p.location,p.time); 
	  		  Database.add(ppl); 
	  		  
		 	}catch(Exception e) {
		 		System.out.println(e);
		 
		 	}
		
	}


	
	public void multipleCheckIn(ArrayList<Person> listofPeople) throws RemoteException {
		try {
		

		      File f  = new File("D:\\javaworkspace\\Covid_RMI\\src\\Database.txt");
			  PrintWriter write =   new PrintWriter(new FileOutputStream(f,true));
				
			  listofPeople.forEach((ppl) -> {
			  write.append(ppl.nric +","+ ppl.name +","+ ppl.location +","+ ppl.time + "\n");  
			  });
			  
			  write.close();
			
			
		}catch(Exception e) {
			System.out.println(e);
			
		}
 
		
		
		
	}


	//I think just remove this
	public void multipleCheckOut(Person p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public ArrayList<String> storeTextToArray() throws RemoteException, IOException {
		BufferedReader in = new BufferedReader(new FileReader("covid_location.txt"));
		String str=null;
		
		ArrayList<String> arrayOfInfectedLocations = new ArrayList<String>();
		try {
			
			
			while((str = in.readLine()) != null) {
				arrayOfInfectedLocations.add(str);
			}
			
			// display array
//			for (String i: arrayOfInfectedLocations) {
//				System.out.println(i);
//			}
			
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return arrayOfInfectedLocations;
	}



	@Override
	public String getLatestLocationDate(String locationInput, ArrayList<String> array) throws RemoteException {
		List<String> endOfInfection = new ArrayList<String>();
		String latestLocationDate = null;
		// iterates through list and returns list with specified string value
		for (String i: array) {
			if (i.trim().contains(locationInput)) {
//				System.out.println(i);
				while (true) {
					endOfInfection.add(i.substring(i.indexOf("[") +1, i.indexOf("]")));
					latestLocationDate = endOfInfection.get(endOfInfection.size()-1);
					
					System.out.println("\nLatest time: " + latestLocationDate);
					break;
				}
				
			}
			else {
				System.out.println(locationInput + " is not suspected of any covid cases.");
				latestLocationDate = null;
			}
		}
				return latestLocationDate;
	}

}
