package soeder;

import lib.CL_Random;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MemoryGame {
	private List<Player> players = new ArrayList<>();
	private List<MemoryImages.MemoryImage> images = new ArrayList<>();
	private int rows;
	private int cols;

	private Player currentPlayer;

	public MemoryGame(List<Player> players, List<MemoryImages.MemoryImage> images, int rows, int cols) throws MemoryException {
		this.players = players;
		this.rows = rows;
		this.cols = cols;

		if (players.size() < 2) {
			throw new MemoryException("At least two players required");
		}

		var neededImageAmount = rows * cols / 2;
		neededImageAmount += neededImageAmount % 2;

		if (neededImageAmount > images.size()) {
			throw new MemoryException("Too few images available");
		}

		initImagesRandomly(images, neededImageAmount);
		nextPlayer();
	}

	private void initImagesRandomly(List<MemoryImages.MemoryImage> images, int neededImageAmount) {
		this.images = new ArrayList<>();
		while (this.images.size() < neededImageAmount) {
			var img = images.get(CL_Random.getExclusiveRandom(0, images.size()));
			if (!this.images.contains(img)) {
				this.images.add(img);
			}
		}
	}

	public void nextPlayer() {
		//get next player
		var index = players.indexOf(currentPlayer);
		if (index == players.size() - 1) {
			currentPlayer = players.get(0);
		} else {
			currentPlayer = players.get(index + 1);
		}

		//reset status
		for (var p : players) {
			p.setStatus(PlayerStatus.WAITING);
		}
		currentPlayer.setStatus(PlayerStatus.ACTIVE);
	}

	public boolean isBlankRequired() {
		return rows * cols % 2 != 0;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<MemoryImages.MemoryImage> getImages() {
		return images;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public List<Player> getPlayersOrderedByPoints() {
		return players.stream()
				.sorted(Comparator.comparing(Player::getPoints))
				//.sorted(Collections.reverseOrder())
				.collect(Collectors.toList());
	}
}
