package rmi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.*;

public class covidimpl extends java.rmi.server.UnicastRemoteObject  implements covid {

	
    public covidimpl() throws java.rmi.RemoteException {
        super();
        
        //Implement and load data from text file
    }

    
	@Override
	public void checkIn(Person p) throws RemoteException {
		// TODO Auto-generated method stub
	 // Person test = (Person) p; 
		 try {
		
			  File f  = new File("D:\\javaworkspace\\Covid_RMI\\src\\rmi\\Database.txt");
			  PrintWriter write =   new PrintWriter(new FileOutputStream(f,true));
			  write.append(p.nric +","+ p.name +","+ p.location +","+ p.time + "\n");
			  write.close();  
			  System.out.println("check in sucessfully");
			  
		 }catch(Exception e) {
			  System.out.println(e);
			 
		 }
		
	}

	@Override
	public void checkOut(Person p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void multipleCheckIn(Person p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void multipleCheckOut(Person p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
