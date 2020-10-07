package jbay;

import lib.CL_File;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Auctionhouse {
	List<Auction> auctions = new ArrayList<>();
	List<Terminal> terminals = new ArrayList<>();

	public void addAuction(Auction a) {
		auctions.add(a);
	}

	public void removeAuction(Auction a) {
		auctions.add(a);
	}

	public List<Auction> getAuctions() {
		return auctions;
	}

	public void register(Terminal t) {
		terminals.add(t);
	}

	public boolean placeBid(Auction auction, Bid bid) {
		var result = auction.placeBid(bid);
		CL_File.appendNewLineToFile("auktionen", createLogValue(auction, bid));
		terminals.forEach(Terminal::update);
		return result;
	}

	private String createLogValue(Auction auction, Bid bid) {
		return "[" + Calendar.getInstance().getTime().toString()
				+ "] Gebot von "
				+ bid.getOwner().getFullName()
				+ " für "
				+ auction.getProduct().getTitle()
				+ ": "
				+ bid.getAmount()
				+ " Euro.";
	}
}
