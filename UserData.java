package labs.lab9;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserData {
	
	private String username;
	private LinkedList<Message> hiInbox;
	private LinkedList<Message> midInbox;
	private LinkedList<Message> loInbox;
	
	public UserData(String username) {
		this.username = username;
		hiInbox = new LinkedList<>();
		midInbox = new LinkedList<>();
		loInbox = new LinkedList<>();
	}
	
	public String getUsername() {
		return username;
	}
	
	public void receiveMessage(PRIORITY p, Message message) {
		switch(p) {
		case High: {
			hiInbox.addFirst(message);
			break;
		}
		case Medium: {
			midInbox.addFirst(message);
			break;
		}
		case Low: {
			loInbox.addFirst(message);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + p);
		}
	}
	
	public List<Message> getInbox() {
		return Stream.of(hiInbox, midInbox, loInbox)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());

	}
}
