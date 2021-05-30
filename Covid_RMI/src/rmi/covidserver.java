package rmi;

import java.rmi.Naming;

public class covidserver {
	static int port = 1099;
	
	public covidserver() {
    try {
    	
       	covid c = new covidimpl();
       	Naming.rebind("rmi://localhost:" + port + "/CalculatorService", c);
     } 
     catch (Exception e) {
       System.out.println("Server Error: " + e);
     }
	
	}
	
	
}
