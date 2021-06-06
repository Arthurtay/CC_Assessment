import java.rmi.Remote;
import java.util.ArrayList;

public interface CovidRMIClientInf extends Remote {
	
	public void confirmation(String Message ) throws java.rmi.RemoteException;
	
	public void NotifyCovid(ArrayList<SuspectedCovid> sc, String nric) throws java.rmi.RemoteException;
	
	public void retrieveHistory() throws java.rmi.RemoteException;
	
}
