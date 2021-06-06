import java.rmi.Naming;

public class TestRMIConnection {

	private static final String RMI_SERVER_URL = "rmi://localhost:1099/CovidCheckInService";
	   
	public static void main(String args[]) throws Exception {	
		
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            System.out.println("Connecting to " + RMI_SERVER_URL);
            
            long beginTime = System.currentTimeMillis();
            covid service = (covid) Naming.lookup(RMI_SERVER_URL);
            long endTime = System.currentTimeMillis();

            System.out.println("end printing response"); 
        }
        catch (Exception e) {
            System.out.println("Exception occured: " + e + " - " + RMI_SERVER_URL);
            System.out.println(e);
            e.printStackTrace();
        }
        System.out.println("The test complete");
    }
}