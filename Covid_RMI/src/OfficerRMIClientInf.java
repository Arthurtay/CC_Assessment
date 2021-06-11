import java.rmi.Remote;
import java.util.ArrayList;

public interface OfficerRMIClientInf extends Remote {
	/**
	 * Function's signature to be implemented in the client
	 * @param Message- contains message from server
	 * @throws java.rmi.RemoteException
	 */
	public void confirmation(String Message ) throws java.rmi.RemoteException;
	/**
	 * Function's signature to be implemented in the client
	 * @param  newIL
	 * @throws java.rmi.RemoteException
	 */
	public void retrieveLatestLocation(ArrayList<InfectedLocation> newIL) throws java.rmi.RemoteException;
	
}
