package labs.lab9;

public class EmailSystem {
	/**
	 * Launch the application.
	 */
	private UserLoginUI loginWindow;
	
	public static void main(String[] args) {
		try {
			EmailSystem emailSystem = new EmailSystem();
			emailSystem.runProgram();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public EmailSystem() {

	}
	public void runProgram() {
		loginWindow = new UserLoginUI();
		loginWindow.initializeLoginWindow();
	}
}