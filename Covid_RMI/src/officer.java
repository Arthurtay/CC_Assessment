import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface officer extends java.rmi.Remote {

	public void storeTextToArray(InfectedLocation IL) throws java.rmi.RemoteException, IOException;
	
	public String getLatestLocationDate(String locationInput, ArrayList<InfectedLocation> LocationLog) throws java.rmi.RemoteException;

	
}
