package snatChat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

public class SnatChatRoom {
	
	private String roomName;
	private List<SnatChatFrontend> frontends = new LinkedList<>();

	public SnatChatRoom(String name) {
		roomName = name; // da hier kein Namenskonflikt reicht ohne this. vorne
	}

	public void register(SnatChatFrontend s) {
		this.frontends.add(s);
		
		try ( BufferedReader br = new BufferedReader(new FileReader(roomName+".txt"))){
			List<String> lines = new LinkedList<String>();
			while ( br.ready() ) {
				lines.add(Message.rot13(br.readLine()));
			}
			// erweiterung: nur letzte 10
			if ( lines.size() > 10 ) {
				lines = lines.subList(lines.size()-10, lines.size());
			}
			
			for ( String line : lines ) {
				sendMessage(line,false); // verteilen OHNE erneutes loggen
			}
			
		} catch (Exception e) {
		}
	}
	public void unregister(SnatChatFrontend s) {
		this.frontends.remove(s);
	}
	
	public String getRoomName() {
		return roomName;
	}
	
	public void sendMessage(Message msg) {
		for ( SnatChatFrontend f : this.frontends ) {
			f.receiveMessage(msg);
		}
		log(msg.getSender().getName()+": " + msg.getText());
	}
	
	
	public void sendMessage(String text) {
		sendMessage(text,true); // mit logging
	}
	
	// hier neu: internes flag das erneutes loggen des einlesenen log files verhindern kann!
	private void sendMessage(String text, boolean log) {
		for ( SnatChatFrontend f : this.frontends ) {
			f.receiveMessage(text);
		}
		if ( log ) log(text);
	}
	
	private void log(String line) {
		try ( BufferedWriter bw = new BufferedWriter(new FileWriter(roomName+".txt",true))){
			bw.write(Message.rot13(line));
			bw.newLine();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
