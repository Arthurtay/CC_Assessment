import java.rmi.Remote;
import java.util.ArrayList;

public interface OfficerRMIClientInf extends Remote {
	
	public void confirmation(String Message ) throws java.rmi.RemoteException;
	
	public void retrieveLatestLocation(ArrayList<InfectedLocation> newIL) throws java.rmi.RemoteException;
	
}
