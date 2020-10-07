package jbay;

public class JBay {
	public static void main(String[] args) {
		Auctionhouse jbay = new Auctionhouse();
		jbay.addAuction(new Auction(
				new Product("Turnschuhe", "Tolle Turnschuhe, kaum getragen"), 2
		));
		jbay.addAuction(new Auction(
				new Product("iPad", "Nagelneues iPad 3"), 4
		));
		jbay.addAuction(new Auction(
				new Product("Currywurst", "Scharf, ohne Pommes"), 5
		));

		jbay.register(new Terminal(new Person("Hans", "Wurst"), jbay));
		jbay.register(new Terminal(new Person("Max", "Mustermann"), jbay));

	}
}
