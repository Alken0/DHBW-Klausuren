package soeder;

import lib.CL_File;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MemoryGameTerm extends JFrame {
	private int rounds = 0;
	private MemoryGame game;
	private final Map<Player, JLabel> players = new HashMap<>();
	private final Thread t;
	private long startTime = System.currentTimeMillis();

	public MemoryGameTerm(MemoryGame game) {
		this.game = game;
		setLayout(new BorderLayout());
		initPlayerNames();
		initGameField();

		t = new Thread(() -> {
			while (!game.getCurrentPlayer().getStatus().equals(PlayerStatus.FINISHED)) {
				updateTitle();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();

		initFrameSettings();
	}

	private void updateTitle() {
		setTitle("Soeder Memory (" + (System.currentTimeMillis() - startTime) / 1000 + ")");
	}

	private void initGameField() {
		var panel = new JPanel();
		panel.setLayout(new GridLayout(game.getRows(), game.getCols(), 5, 5));

		//init icons and shuffle
		var images = new ArrayList<ImageIcon>();
		game.getImages().forEach(i -> {
			images.add(i.getImage());
			images.add(i.getImage());
		});
		if (game.isBlankRequired()) {
			images.add(MemoryImages.getBlank());
		}
		Collections.shuffle(images);

		for (var img : images) {
			var card = new JToggleButton();
			card.setIcon(MemoryImages.getBackside());
			card.setSelectedIcon(img);
			card.addActionListener(a -> evaluateSelections(panel));
			panel.add(card);
		}

		add(panel, BorderLayout.CENTER);
	}

	private void evaluateSelections(JPanel gameField) {
		JToggleButton first = null;
		JToggleButton second = null;

		for (var card : gameField.getComponents()) {
			if (card.isEnabled() && ((JToggleButton) card).isSelected()) {
				if (first == null) {
					first = (JToggleButton) card;
				} else if (second == null) {
					second = (JToggleButton) card;
				}
			}
		}

		if (first != null && second != null) {
			rounds++;

			if (first.getSelectedIcon().equals(second.getSelectedIcon())) {
				first.setEnabled(false);
				second.setEnabled(false);
				game.getCurrentPlayer().addPoint();
			} else {
				JOptionPane.showMessageDialog(null, "sorry, those didn't match");

				first.setSelected(false);
				second.setSelected(false);
				game.nextPlayer();
			}
		}

		handleGameIsFinished(gameField);
		updatePlayerNames();
	}

	private void handleGameIsFinished(JPanel gameField) {
		var finished = true;
		for (var card : gameField.getComponents()) {
			if (card.isEnabled() && !((JToggleButton) card).getSelectedIcon().equals(MemoryImages.getBlank())) {
				finished = false;
			}
		}

		if (finished) {
			game.getPlayers().forEach(p -> p.setStatus(PlayerStatus.FINISHED));

			var summary = new StringBuilder("Game ends after " + this.rounds + " rounds, scores:");
			for (var p : game.getPlayersOrderedByPoints()) {
				summary.append(" ").append(p.getName()).append(" (").append(p.getPoints()).append(")");
			}
			JOptionPane.showMessageDialog(null,
					summary.toString() + "\n" + CL_File.getContentOfFile("memory"));

			CL_File.appendNewLineToFile("memory", summary.toString());
		}
	}

	private void initPlayerNames() {
		var panel = new JPanel();
		panel.setLayout(new GridLayout(game.getPlayers().size(), 1));
		for (var p : game.getPlayers()) {
			var label = new JLabel();
			players.put(p, label);
			panel.add(label);
		}
		updatePlayerNames();
		add(panel, BorderLayout.NORTH);
	}

	private void updatePlayerNames() {
		for (var p : players.keySet()) {
			var label = players.get(p);
			label.setText(p.getName() + " (" + p.getPoints() + ")");
			label.setForeground(p.getStatus().getColor());
		}
	}

	private void initFrameSettings() {
		setTitle("Soeder Memory");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		// order is important for the following settings!!!
		pack();
		setSize(500, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
