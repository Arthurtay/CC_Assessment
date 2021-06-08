

import java.rmi.Naming;

public class covidserver {
	static int port = 1099;
	public covidserver() {
    try {
    	
       	covid c = new covidimpl();
       	Naming.rebind("rmi://localhost:" + port + "/CovidCheckInService", c);
       	

       	} 
     catch (Exception e) {
       System.out.println("Server Error: " + e);
     }
	
	}
	
	
	
	  public static void main(String args[]) {
	   if (args.length == 1)
		port = Integer.parseInt(args[0]);
		
	   // Create covid server
		new covidserver();
	  
	  
	  }
	
	
	
}
