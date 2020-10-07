package jbay;

import java.util.ArrayList;
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

	public boolean placeBid(Auction a, Bid bid) {
		var result = a.placeBid(bid);
		terminals.forEach(Terminal::updateAuctionTable);
		return result;
	}
}
