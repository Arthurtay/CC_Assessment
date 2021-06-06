
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class covidclient  extends java.rmi.server.UnicastRemoteObject implements CovidRMIClientInf{
	
	static SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");	 
	static Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 
	 
	@Override
	public void confirmation(String Message) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(Message);
	}

	@Override
	public void NotifyCovid(ArrayList<SuspectedCovid> suspectC, String nric) throws RemoteException {
		

		System.out.println("--------------------------");
		System.out.println("|          ALERT         |");
		System.out.println("--------------------------");
		for (SuspectedCovid sc : suspectC) {
			if(sc.getNric().contains(nric)) {
				
				System.out.println("You are suspected of having a risk of covid!!!");
				System.out.println("Location: " + sc.getLocation() + ", on " + sc.getDateSuspected() + "\n");
			}
		}
		System.out.println("--------------------------");
				
	}

	@Override
	public void retrieveHistory() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	public covidclient() throws RemoteException{
		
	}
	
	 public static void main(String[] args) throws IOException {
		 
	
	  String reg_host ="localhost";
	  int reg_port = 1099;
   
	    if (args.length == 1)
	    {
	    	reg_port = Integer.parseInt(args[0]);
	    } 
	    else if (args.length == 2) 
	    {
	    	reg_host = args[0];
	    	reg_port = Integer.parseInt(args[1]);
	    }
		 
		 
		 

	  
	  try {
		covidclient client = new covidclient();
		
		covid service = (covid)  Naming.lookup("rmi://" + reg_host + ":" + reg_port + "/CovidCheckInService");
		
		

	     Scanner sc =new Scanner(System.in);
		 System.out.println("Enter Your Name");
		 String name = sc.nextLine();
	
		 System.out.println("Enter your NRIC");
		 String nric = sc.nextLine();
	     
		 System.out.println("\nWelcome to the Check In client " + name + " " + nric + "!"  ); 

		 service.checkCovid(client, nric);
		 

	     while(true) {
			  SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");	 
			  Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 
			 
	     System.out.println("\nEnter Number for Selection");
		 System.out.println("1.Individual Check In");
		 System.out.println("2.Group Check In");
		 System.out.println("3.Checkout");
		 int option = Integer.parseInt(sc.nextLine());
		 
		 
		 
		 	switch(option) {
		 		case 1:
		 			 ClientCheckIn(sc,service,client,name,nric);
					 break;
		 		case 2:
		 			ClientMultipleCheckIn(sc,service,client,name,nric);
		 			break;
		 		case 3:
		 			ClientCheckOut(sc,service,client,name,nric);
		 			break;
		 		default:
		 			System.out.println("Invalid option,please Key in a valid option");
		 
		 
		 	}

	     /*
		 if(option == 1) {
			 	
			 System.out.println("\n====Invididual Check In Selected====");
			  
			 System.out.println("Enter your Location");
			 String location =  sc.nextLine();
			 
			 String time = ft.format(timestamp);
			 System.out.println(time);
			 
			 Person p = new Person(nric,name,location,time);
			 
			 service.checkIn(client,p);
			 
		 }
		 else if(option == 2) {
			 
			 System.out.println("\n====Multiple Check In Selected====");
			 ArrayList<Person> listofPeople = new ArrayList<Person>() ;
			 String state = "y" ;
	
		
			 System.out.println("Enter your Location");
			 String location =  sc.nextLine();
			 
			 String time = ft.format(timestamp);
			 System.out.println(time);
			 
			 //Include Indiviual check In 
			 Person p = new Person(nric,name,location,time);
			 listofPeople.add(p);
			 
			 //While continues to loop and read other checkin members data
			 while(state.equals("y"))
			 {
				 
				 System.out.println("Enter Family/Friend Name");
				 String extra_name = sc.nextLine();
				
				 System.out.println("Enter Family/Friend NRIC");
				 String extra_nric = sc.nextLine();
				  			 
				 Person p2 = new Person(extra_nric,extra_name,location,time);
				 
				 listofPeople.add(p2);
				 
				 System.out.println("Continue group check in?(y/n)" );
				 state = sc.nextLine();
				 
			 }; // end of while loop
			 
			 //Invoke remote method 
			 service.multipleCheckIn(client,listofPeople);
			 	
		 }
		 
		// service.checkCovid(client, nric);
		 
		 
		 
		*/
	   }//End of While Loop
	   	  
	  }catch(NotBoundException e) {
		  System.out.println(e);
		  
	  }
	  
	  

		 
		 

	}
	 
	 
	 public static void ClientCheckIn(Scanner sc,  covid service , covidclient client ,String nric, String name ) throws RemoteException{
		 
		//  SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");	 
		//  Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 
		 
		 
		 System.out.println("\n====Invididual Check In Selected====");
		  
		 System.out.println("Enter your Location");
		 String location =  sc.nextLine();
		 
		 String time = ft.format(timestamp);
		 System.out.println(time);
		 
		 Person p = new Person(nric,name,location,time); 
		 service.checkIn(client,p);
	 }
	 
	 
	 public static void ClientCheckOut(Scanner sc,  covid service , covidclient client ,String nric, String name ) throws RemoteException{
		 
		//  SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");	 
		//  Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 
		 
		 
		 System.out.println("\n====Invididual Check In Selected====");
		  
		 System.out.println("Enter your Location");
		 String location =  sc.nextLine();
		 
		 String time = ft.format(timestamp);
		 System.out.println(time);
		 
		 Person p = new Person(nric,name,location,time); 
		 service.checkOut(client, p);
		 
	 }
	 
	 
	 
	 public static void ClientMultipleCheckIn(Scanner sc,  covid service , covidclient client ,String nric, String name ) throws RemoteException{
		 
		  SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");	 
		  Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 
		 
		 System.out.println("\n====Multiple Check In Selected====");
		 ArrayList<Person> listofPeople = new ArrayList<Person>() ;
		 String state = "y" ;

	
		 System.out.println("Enter your Location");
		 String location =  sc.nextLine();
		 
		 String time = ft.format(timestamp);
		 System.out.println(time);
		 
		 //Include Indiviual check In 
		 Person p = new Person(nric,name,location,time);
		 listofPeople.add(p);
		 
		 //While continues to loop and read other checkin members data
		 while(state.equals("y"))
		 {
			 
			 System.out.println("Enter Family/Friend Name");
			 String extra_name = sc.nextLine();
			
			 System.out.println("Enter Family/Friend NRIC");
			 String extra_nric = sc.nextLine();
			  			 
			 Person p2 = new Person(extra_nric,extra_name,location,time);
			 
			 listofPeople.add(p2);
			 
			 System.out.println("Continue group check in?(y/n)" );
			 state = sc.nextLine();
			 
		 }; // end of while loop
		 
		 //Invoke remote method 
		 service.multipleCheckIn(client,listofPeople);
		 	
	 }

	


	 
	 
	 

}
