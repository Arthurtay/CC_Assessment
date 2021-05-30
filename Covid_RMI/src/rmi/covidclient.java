package rmi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class covidclient {
	
	
	 public static void main(String[] args) throws IOException {
		 
	  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");	 
	  Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 
	  
	  /*
	    Scanner sc=new Scanner(System.in);
		 System.out.println("Welcome to the client");
	
		 
		 System.out.println("Enter Your Name");
		 String name = sc.nextLine();
		
		 System.out.println("Enter your NRIC");
		 String nric = sc.nextLine();
		  
		 System.out.println("Enter your Location");
		 String location =  sc.nextLine();
		 
		 String time = ft.format(timestamp);
		 System.out.println(time);

		 insert(name,nric,location,time);
		*/ 
		
	  
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
