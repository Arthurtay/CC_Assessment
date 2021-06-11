import java.rmi.Remote;
import java.util.ArrayList;

public interface CovidRMIClientInf extends Remote {
	
	/**
	 * Function's signature to be implemented in the client
	 * @param Message- contains message from server
	 * @throws java.rmi.RemoteException
	 */
	public void confirmation(String Message ) throws java.rmi.RemoteException;
	/**
	 * Function's signature to be implemented in the client
	 * @param nric
	 * @param sc
	 * @throws java.rmi.RemoteException
	 */
	public void NotifyCovid(ArrayList<SuspectedCovid> sc, String nric) throws java.rmi.RemoteException;

	
}
