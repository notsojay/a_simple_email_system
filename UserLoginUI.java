package labs.lab9;

import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

public class UserLoginUI extends UserDatabase {
	
	public UserLoginUI() {
		
	}
	
	public void initializeLoginWindow() {
		String username = "";
		while(username.trim().equals("")) {
			username = JOptionPane.showInputDialog(null, "Enter your usename:");
			if(username == null) {
				System.exit(0);
			}
		}
		if(!userDataSet.containsKey(username)) {
			UserDatabase.userDataSet.put(username, new UserData(username));
		}
		UserDatabase.currentUser = userDataSet.get(username);
		UserDatabase.currentContacts = new TreeSet<>(userDataSet.keySet()
                .stream()
                .collect(Collectors.toSet()));
		HomePageUI mainWindow = new HomePageUI();
		mainWindow.initializeMainWindow();
		mainWindow.setFrameVisible(true);
	}
	
}
