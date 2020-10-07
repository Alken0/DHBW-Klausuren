package jbay;

import java.util.Calendar;

public class Auction {
	private final double increment = 1d;

	private Product product;
	private double price = 0d;
	private Bid highestBid;
	private Calendar end = Calendar.getInstance();

	public Auction(Product product, int durationInMinutes) {
		this.product = product;
		end.setTimeInMillis(System.currentTimeMillis() + 60000 * durationInMinutes);
	}


	public boolean placeBid(Bid bid) {
		if (price + increment > bid.getAmount()) {
			return false;
		}
		if (highestBid == null) {
			price = increment;
			highestBid = bid;
			return true;
		}
		if (highestBid.getOwner().equals(bid.getOwner()) && highestBid.getAmount() < bid.getAmount()) {
			highestBid = bid;
			return true;
		}

		if (highestBid.getAmount() >= bid.getAmount()) {
			price = Math.min(bid.getAmount() + increment, highestBid.getAmount());
			return false;
		} else {
			price = Math.min(bid.getAmount() + increment, highestBid.getAmount());
			highestBid = bid;
			return true;
		}
	}

	public Product getProduct() {
		return product;
	}

	public double getPrice() {
		return price;
	}

	public Calendar getEnd() {
		return end;
	}

	public String getCurrentOwnerName() {
		return highestBid == null ? "---" : highestBid.getOwner().getFullName();
	}
}
