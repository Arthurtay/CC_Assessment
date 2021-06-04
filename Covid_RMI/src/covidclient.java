
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class covidclient  extends java.rmi.server.UnicastRemoteObject implements CovidRMIClientInf{
	
	
	@Override
	public void confirmation(String Message) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(Message);
	}

	@Override
	public void NotifyCovid(ArrayList<SuspectedCovid> suspectC, String nric) throws RemoteException {
		
//		if(covidLocation.isEmpty()) 
//		{
//			System.out.println("There is no suspected exposure of covid");			
//		}
//		else {
//			
//			System.out.println("You are suspected of covid, please monitor your health for the next 14 days");
//			System.out.println("======Location of Exposure=====");
//			for(InfectedLocation loc : covidLocation) {
//				System.out.println("Location : " + loc.location + "Time" + loc.dateInfection);	
//			}
//			
//		}
		for (SuspectedCovid sc : suspectC) {
			if(sc.getNric().contains(nric)) {
				System.out.println("You are suspected of covid.");
				System.out.println("\n"+ sc.getNric() + "\n" + sc.getLocation() + "\n" + sc.getDateSuspected());
			}
		}
				
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
		 
		 
		 
	  SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");	 
	  Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 
	  
	  try {
		covidclient client = new covidclient();
		
		covid service = (covid)  Naming.lookup("rmi://" + reg_host + ":" + reg_port + "/CovidCheckInService");
		
		

	     Scanner sc =new Scanner(System.in);
		 System.out.println("Enter Your Name");
		 String name = sc.nextLine();
	
		 System.out.println("Enter your NRIC");
		 String nric = sc.nextLine();
	     
		 System.out.println("Welcome to the Check In client " + name + " " + nric + "!"  ); 

		 service.checkCovid(client, nric);
		 
		 /*
		 //Periodically check for covid status if there is suspected covidcases  
		 Thread checkcovid_thread = new Thread(new Runnable() {

				@Override
				public void run() {
					Random rg = new Random();
					int timer = rg.nextInt(10000);  				
					try {
						
						while(true) {
					  	Thread.sleep(timer);
						service.checkCovid(client, nric);
						}
					} catch (java.rmi.RemoteException e) {
						e.printStackTrace();
					} catch(InterruptedException ee) {}
				}    
				});
		
		 checkcovid_thread.start();		
		 */
	     
	     while(true) {
		 
	     System.out.println("Enter Number for Selection");
		 System.out.println("1.Individual Check In");
		 System.out.println("2.Group Check In");
		 System.out.println("3.Checkout");
		 int option = Integer.parseInt(sc.nextLine());
		 
		 

	    
		 if(option == 1) {
			 	
			 System.out.println("====Invididual Check In Selected====");
			  
			 System.out.println("Enter your Location");
			 String location =  sc.nextLine();
			 
			 String time = ft.format(timestamp);
			 System.out.println(time);
			 
			 Person p = new Person(nric,name,location,time);
			 
			 service.checkIn(client,p);
			 
		 }
		 else {
			 
			 System.out.println("====Multiple Check In Selected====");
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
		 
		 service.checkCovid(client, nric);
		 
		 
		 
		
	   }//End of While Loop
		  
	  }catch(Exception e) {
		  System.out.println(e);
		  
	  }
	  
	  

		 
		 
	//	 insert(name,nric,location,time);
		 
		
	  /*
	  File file = new File("D:\\javaworkspace\\Covid_RMI\\src\\rmi\\Database.txt");
	  
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
		// Person a = new person 
		 
		 System.out.println("This is the end of 1 line" );
		
	  }
	  */
	}
	 
	 public static void MultipleCheckIn() {
		 
		 

		 
		 
	 }

	


	 
	 
	 

}
