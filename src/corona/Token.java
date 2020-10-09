package corona;

import java.util.Date;
import java.util.UUID;

public class Token {
	private final String value;
	private final Date date;

	public Token(String value, Date date) {
		this.value = value;
		this.date = date;
	}

	public Token() {
		value = UUID.randomUUID().toString();
		date = new Date();
	}

	@Override
	public String toString() {
		return value + " @ " + date.toString();
	}

	public String getValue() {
		return value;
	}

	public Date getDate() {
		return date;
	}
}
