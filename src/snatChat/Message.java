package snatChat;

public class Message {
	
	private String text;
	private Account sender;
	
	public Message(String text, Account sender) {
		super();
		this.text = text;
		this.sender = sender;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Account getSender() {
		return sender;
	}

	public void setSender(Account sender) {
		this.sender = sender;
	}
	
	public static String rot13( String msg ) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < msg.length(); i++) {
			char c = msg.charAt(i);
			
			if ( c >= 'A' && c <= 'M' || c >= 'a' && c <= 'm' ) {
				sb.append((char)(c+13));
			}
			else if ( c >= 'N' && c <= 'Z' || c >= 'n' && c <= 'z' ) {
				sb.append((char)(c-13));
			}
			else {
				sb.append(c);
			}
		}
		
		return sb.toString();
	}

}
