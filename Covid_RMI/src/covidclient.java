
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
import java.util.Date;
import java.util.Scanner;
import java.util.*;

public class covidclient  extends java.rmi.server.UnicastRemoteObject {
	
	
	
	
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
		 
		 
		 
	  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");	 
	  Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 
	  
	  try {
		covidclient cc = new covidclient();
		
		covid c = (covid)  Naming.lookup("rmi://" + reg_host + ":" + reg_port + "/CovidCheckInService");
		
		

	     Scanner sc=new Scanner(System.in);
		 System.out.println("Welcome to the Check In client");
	     System.out.println("Enter Number for Selection");
		 System.out.println("1.Individual Check In");
		 System.out.println("2.Group Check In");
		 int option =  sc.nextInt();
		 
		 
		 if(option == 1) {
			 	
			 System.out.println("Enter Your Name");
			 String name = sc.nextLine();
			
			 System.out.println("Enter your NRIC");
			 String nric = sc.nextLine();
			  
			 System.out.println("Enter your Location");
			 String location =  sc.nextLine();
			 
			 String time = ft.format(timestamp);
			 System.out.println(time);
			 
			 Person p = new Person(name,nric,location,time);
			 
			 c.checkIn(p);
			 
		 }
		 else {
			 
			 ArrayList<Person> listofPeople = new ArrayList<Person>() ;
			 String state ;
			 do {
				 System.out.println("Enter Your Name");
				 String name = sc.nextLine();
				
				 System.out.println("Enter your NRIC");
				 String nric = sc.nextLine();
				  
				 System.out.println("Enter your Location");
				 String location =  sc.nextLine();
				 
				 String time = ft.format(timestamp);
				 System.out.println(time);
				 
				 Person p = new Person(name,nric,location,time);
				 
				 listofPeople.add(p);
				 
				 System.out.println("Continue group check in?(y/n)" );
				 state = sc.nextLine();
				 
				 }while(state.equals("y")); // end of while loop
			 
			 //Invoke remote method 
			 c.multipleCheckIn(listofPeople);
			 	
		 }
		 
		 
		 

		 
		
		 
		 
		
		System.out.println(); 
	
		  
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
	 
	 public static void insert(String name, String nric,String location, String time) {
		 
		 
		 try {
			  File f  = new File("D:\\javaworkspace\\Covid_RMI\\src\\rmi\\Database.txt");
			  PrintWriter write =   new PrintWriter(new FileOutputStream(f,true));
			  write.append(name +","+ nric +","+ location +","+ time + "\n");
			  write.close();
			  
			  System.out.println("check in sucessfully");
			  
		 }catch(Exception e) {
			  System.out.println(e);
			 
		 }
		 
		 
	 }
	 
	 
	 

}
