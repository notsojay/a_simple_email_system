package labs.lab9;

public class Message {

	private String subject;
	private String message;
	private String sender;
	private String recipient;
	private PRIORITY priority;
	private String date;
	
	public Message(String subject, String message, String sender, String recipient, PRIORITY priority, String date) {
		this.subject = subject;
		this.message = message;
		this.sender = sender;
		this.recipient = recipient;
		this.priority = priority;
		this.date = date;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getSender() {
		return sender;
	}
	
	public String getrecipient() {
		return recipient;
	}
	
	public PRIORITY getPriority() {
		return priority;
	}
	
	public String getDate() {
		return date;
	}
}
