package jbay;

public class Bid {
	private Person owner;
	private double amount;

	public Bid(Person owner, double amount) {
		this.owner = owner;
		this.amount = amount;
	}


	public Person getOwner() {
		return owner;
	}

	public double getAmount() {
		return amount;
	}
}
