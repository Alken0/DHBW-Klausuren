package snatChat;

import java.awt.Color;
import java.util.Random;

public class Account {

	private String name;
	private State state = State.AVAILABLE;
	private Color color;
	
	public Account(String name) {
		this.name = name;
//		this.state = State.AVAILABLE; // oder hier :-)
		Random rand = new Random();
		int blue = (int)(Math.random()*201);  // alternative Zufallsmoeglichkeit
		this.color = new Color(rand.nextInt(201), rand.nextInt(201), blue);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	

}
