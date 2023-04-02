package labs.lab9;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JScrollPane;

public class HomePageUI extends UserDatabase {
	
	private JFrame frame;
	private JTextField subjectText;
	private JTextArea mailDraft;
	private JTextArea inboxContent;
	private JRadioButton hiPrioRButton;

	/**
	 * Create the application.
	 */
	public HomePageUI() {
		
	}
	
	public void setFrameVisible(boolean b) {
		frame.setVisible(b);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	public void initializeMainWindow() {
		frame = new JFrame();
		frame.setBounds(100, 100, 689, 726);
		frame.setTitle("Email System - " + currentUser.getUsername());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		sendEmail();
		checkInbox();
		switchUserAndLogout();
	}
	
	private void sendEmail() {
		JPanel bottemPanel = new JPanel();
		bottemPanel.setBorder(new TitledBorder(null
				, "New Message"
				, TitledBorder.LEADING
				, TitledBorder.TOP
				, null
				, null));
		bottemPanel.setBounds(40, 267, 602, 368);
		frame.getContentPane().add(bottemPanel);
		bottemPanel.setLayout(null);
		
		createSubjectPanel(bottemPanel);	
		createPriorityPanel(bottemPanel);
		createToRecipientPanel(bottemPanel);
		createSendAndClearPanel(bottemPanel);
		createDraftPanel(bottemPanel);
	}
	
	private void checkInbox() {
		JPanel highPanel = new JPanel();
		highPanel.setBorder(new TitledBorder(null
				, "Inbox"
				, TitledBorder.LEADING
				, TitledBorder.TOP
				, null
				, null));
		highPanel.setBounds(40, 56, 602, 212);
		frame.getContentPane().add(highPanel);
		highPanel.setLayout(null);
		
		createInboxListPanel(highPanel);
		createInboxContentPanel(highPanel);
	}
	
	private void switchUserAndLogout() {
		createCurrentUserPanel();
		createMenuBar();
	}
	
	private void createSubjectPanel(JPanel bottemPanel) {
		JPanel subjectPanel = new JPanel();
		subjectPanel.setBounds(125, 90, 346, 31);
		bottemPanel.add(subjectPanel);
		subjectPanel.setLayout(null);
		
		JLabel subjectLabel = new JLabel("Subject:");
		subjectLabel.setBounds(17, 5, 50, 24);
		subjectPanel.add(subjectLabel);
		
		
		subjectText = new JTextField();
		subjectText.setBounds(77, 4, 256, 26);
		subjectPanel.add(subjectText);
		subjectText.setColumns(10);
	}
	
	private void createPriorityPanel(JPanel bottemPanel) {
		JPanel priorityPanel = new JPanel();
		priorityPanel.setBounds(124, 57, 346, 31);
		bottemPanel.add(priorityPanel);
		priorityPanel.setLayout(null);
		
		hiPrioRButton = new JRadioButton("High");
		hiPrioRButton.setBounds(100, 7, 62, 21);
		hiPrioRButton.setSelected(true);
		priorityPanel.add(hiPrioRButton);
		hiPrioRButton.addActionListener(AL -> {
			UserDatabase.currentPriority = PRIORITY.High;
		});
		
		JRadioButton midPrioRButton = new JRadioButton("Medium");
		midPrioRButton.setBounds(165, 7, 83, 21);
		priorityPanel.add(midPrioRButton);
		midPrioRButton.addActionListener(AL -> {
			UserDatabase.currentPriority = PRIORITY.Medium;
		});
		
		JRadioButton lowPrioRButton = new JRadioButton("Low");
		lowPrioRButton.setBounds(250, 7, 57, 21);
		priorityPanel.add(lowPrioRButton);
		lowPrioRButton.addActionListener(AL -> {
			UserDatabase.currentPriority = PRIORITY.Low;
		});
		
		ButtonGroup priorityGroup = new ButtonGroup();
		priorityGroup.add(hiPrioRButton);
		priorityGroup.add(midPrioRButton);
		priorityGroup.add(lowPrioRButton);
		
		JLabel priorityLabel = new JLabel("Priority:");
		priorityLabel.setBounds(44, 6, 57, 24);
		priorityPanel.add(priorityLabel);
	}
	
	private void createToRecipientPanel(JPanel bottemPanel) {
		JPanel toRecipientPanel = new JPanel();
		toRecipientPanel.setBounds(124, 18, 346, 37);
		bottemPanel.add(toRecipientPanel);
		toRecipientPanel.setLayout(null);
		
		JLabel toLabel = new JLabel("To:");
		toLabel.setBounds(97, 6, 20, 24);
		toRecipientPanel.add(toLabel);
		
		String[] tempArr = UserDatabase.currentContacts
				.stream()
				.filter(i -> !i.equals(currentUser.getUsername()))
				.sorted(String.CASE_INSENSITIVE_ORDER)
				.toArray(String[]::new);
		UserDatabase.currentRecipient = tempArr[0];
		JComboBox<String> toRecipientComboBox = new JComboBox<>(tempArr);
		toRecipientComboBox.setBounds(123, 6, 145, 27);
		toRecipientPanel.add(toRecipientComboBox);
		toRecipientComboBox.addActionListener(AL -> {
			UserDatabase.currentRecipient = (String)toRecipientComboBox.getSelectedItem();
		});
	}
	
	private void createSendAndClearPanel(JPanel bottemPanel) {
		JPanel sendAndClearPanel = new JPanel();
		sendAndClearPanel.setBounds(204, 325, 165, 31);
		bottemPanel.add(sendAndClearPanel);
		sendAndClearPanel.setLayout(null);
		
		JButton sendButton = new JButton("Send");
		sendButton.setBounds(0, 0, 80, 31);
		sendAndClearPanel.add(sendButton);
		sendButton.addActionListener(AL -> {
			if(!isValidateInputs()) {
				return;
			}
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String formattedDateTime = now.format(formatter);	
			UserDatabase.userDataSet.get(currentRecipient).receiveMessage(currentPriority
					, new Message(subjectText.getText()
					, mailDraft.getText()
					, UserDatabase.currentUser.getUsername()
					, UserDatabase.currentRecipient
					, UserDatabase.currentPriority
					, formattedDateTime));		
			JOptionPane.showMessageDialog(null, "Message sent");
			subjectText.setText("");
			mailDraft.setText("");
			hiPrioRButton.setSelected(true);
		});
		
		JButton clearButton = new JButton("Clear");
		clearButton.setBounds(89, 0, 80, 31);
		sendAndClearPanel.add(clearButton);
		clearButton.addActionListener(AL -> {
			mailDraft.setText("");
			subjectText.setText("");
		});	
	}
	
	private boolean isValidateInputs() {
	    if(subjectText.getText().isEmpty() || subjectText.getText().trim().isEmpty() || 
	        mailDraft.getText().isEmpty() || mailDraft.getText().trim().isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Subject and mail draft can not be blank!", "Error", JOptionPane.ERROR_MESSAGE);
	            return false;
	    }
	    return true;
	}
	
	private void createDraftPanel(JPanel bottemPanel) {
		JScrollPane draftPanel = new JScrollPane();
		draftPanel.setBounds(7, 144, 587, 176);
		bottemPanel.add(draftPanel);
		
		mailDraft = new JTextArea();
		draftPanel.setViewportView(mailDraft);
		mailDraft.setTabSize(4);
	}
	
	private void createInboxListPanel(JPanel highPanel) {
		JScrollPane inboxListPanel = new JScrollPane();
		inboxListPanel.setBounds(7, 29, 287, 165);
		highPanel.add(inboxListPanel);
		
		List<Message> tempList = UserDatabase.currentUser.getInbox();
		if(tempList.isEmpty() || tempList == null) {
			JList<String> inboxJList = new JList<String>();
			inboxListPanel.setViewportView(inboxJList);
			return;
		}
		String[] tempArr = tempList.stream()
				.map(i -> "From: " + i.getSender() + ", Subject: " + i.getSubject())
				.toArray(String[]::new);
		HashMap<Integer, Message> tempMap = new HashMap<>();
		for(int i = 0; i < tempList.size(); ++i) {
			tempMap.put(i, tempList.get(i));
		}
		JList<String> inboxJList = new JList<String>(tempArr);
		inboxListPanel.setViewportView(inboxJList);
		inboxJList.addListSelectionListener(LSL -> {
			if(!inboxJList.isSelectionEmpty()) {
				int index = inboxJList.getSelectedIndex();
				if(tempMap.containsKey(index)) {
					StringBuilder sb = new StringBuilder();
					sb.append("From: " + tempMap.get(index).getSender());
					sb.append("\nTo: " + tempMap.get(index).getrecipient());
					sb.append("\nPriority: " + tempMap.get(index).getPriority().name());
					sb.append("\nSubject: " + tempMap.get(index).getSubject());
					sb.append("\n" + tempMap.get(index).getDate());
					sb.append("\n\n" + tempMap.get(index).getMessage());
					inboxContent.setText(sb.toString());
				}
			}
		});
	}
	
	private void createInboxContentPanel(JPanel highPanel) {
		JScrollPane inboxContentPanel = new JScrollPane();
		inboxContentPanel.setBounds(306, 29, 287, 165);
		highPanel.add(inboxContentPanel);
		
		inboxContent = new JTextArea();
		inboxContent.setEditable(false);
		inboxContentPanel.setViewportView(inboxContent);
	}
	
	private void createCurrentUserPanel() {
		JPanel currentUserPanel = new JPanel();
		currentUserPanel.setBounds(222, 20, 299, 34);
		frame.getContentPane().add(currentUserPanel);
		currentUserPanel.setLayout(null);
		
		JLabel currentUserLabel = new JLabel("Current User:");
		currentUserLabel.setBounds(30, 6, 91, 22);
		currentUserPanel.add(currentUserLabel);
		
		JLabel lblNewLabel = new JLabel(currentUser.getUsername());
		lblNewLabel.setBounds(127, 6, 100, 22);
		currentUserPanel.add(lblNewLabel);
	}
	
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem fileMenuItem = new JMenuItem("Exit");
		fileMenu.add(fileMenuItem);
		fileMenuItem.addActionListener(AL -> {
			System.exit(0);
		});
		
		JMenu usersMenu = new JMenu("Users");
		menuBar.add(usersMenu);
		JMenuItem usersMenuItem = new JMenuItem("Switch User");
		usersMenu.add(usersMenuItem);
		usersMenuItem.addActionListener(AL -> {
			frame.dispose();
			UserDatabase.currentPriority = PRIORITY.High;
			UserLoginUI loginWindow = new UserLoginUI();
			loginWindow.initializeLoginWindow();
		});
	}
}
