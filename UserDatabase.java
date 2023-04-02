package labs.lab9;

import java.util.TreeMap;
import java.util.TreeSet;

enum PRIORITY {
	High, Medium, Low;
}

public abstract class UserDatabase {
	protected static TreeMap<String, UserData> userDataSet;
	protected static TreeSet<String> currentContacts;
	protected static PRIORITY currentPriority;
	protected static String currentRecipient;
	protected static UserData currentUser;
	
	static {
		UserDatabase.userDataSet = new TreeMap<>();
		UserDatabase.userDataSet.put("Robert Navarro", new UserData("Robert Navarro"));
		UserDatabase.currentContacts = new TreeSet<>();
		UserDatabase.currentRecipient = "Robert Navarro";
		UserDatabase.currentPriority = PRIORITY.High;
		UserDatabase.currentUser = null;
	}
	
	public UserDatabase() {
		
	}

}