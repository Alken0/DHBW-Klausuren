package jbay;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class Terminal extends JFrame {
	private Person owner;
	private Auctionhouse house;
	private JPanel table = new JPanel();

	public Terminal(Person owner, Auctionhouse house) {
		this.owner = owner;
		this.house = house;

		setLayout(new BorderLayout());

		createUpdatedTimeStamp();
		resetAuctionTable();
		add(table, BorderLayout.CENTER);

		initFrameSettings();
	}

	private void createUpdatedTimeStamp() {
		var timeStamp = new JLabel("");
		new Thread(() -> {
			while (true) {
				timeStamp.setText(Calendar.getInstance().getTime().toString());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		add(timeStamp, BorderLayout.NORTH);
	}

	private void resetAuctionTable() {
		table.removeAll();
		table.setLayout(new GridLayout(house.getAuctions().size(), 5));

		for (var auction : house.getAuctions()) {
			table.add(new JLabel(auction.getProduct().getTitle()));
			table.add(new JLabel("" + auction.getPrice()));
			table.add(new JLabel(auction.getCurrentOwnerName()));
			table.add(new JLabel(auction.getEnd().getTime().toString()));

			var button = new JButton("Gebot");
			button.addActionListener(a -> {
				var input = JOptionPane.showInputDialog(
						"Bitte neues Gebot eingeben\n" +
								"Mindestens " + auction.getPrice() + " EUR"
				);

				if (auction.getEnd().after(System.currentTimeMillis())) {
					JOptionPane.showMessageDialog(null, "Auktion vorbei!");
					input = "";
				}

				try {
					var amount = Double.parseDouble(input);
					var isSuccessful = house.placeBid(auction, new Bid(owner, amount));

					if (isSuccessful) {
						JOptionPane.showMessageDialog(null, "Sie sind Höchstbietender!");
					} else {
						JOptionPane.showMessageDialog(null, "Gebot zu gering!");
					}

				} catch (NumberFormatException | NullPointerException ignored) {
				}

			});
			table.add(button);
		}

		table.validate();
	}

	private void initFrameSettings() {
		setTitle(owner.getFullName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		// order is important for the following settings!!!
		pack();
		setSize(500, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void update() {
		for (int i = 0; i < house.getAuctions().size(); i++) {
			var priceLabel = (JLabel) table.getComponent(i * 5 + 1);
			priceLabel.setText("" + house.getAuctions().get(i).getPrice());

			var ownerLabel = (JLabel) table.getComponent(i * 5 + 2);
			ownerLabel.setText(house.getAuctions().get(i).getCurrentOwnerName());
		}
	}
}
