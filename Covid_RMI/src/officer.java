import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface officer extends java.rmi.Remote {

	public ArrayList<String> storeTextToArray() throws java.rmi.RemoteException, IOException;
	
	public String getLatestLocationDate(String locationInput, ArrayList<String> array) throws java.rmi.RemoteException;
	
}
