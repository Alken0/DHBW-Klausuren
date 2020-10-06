package soeder;

public class Player {
	private final String name;
	private int points = 0;
	private PlayerStatus status = PlayerStatus.WAITING;

	public Player(String name) {
		this.name = name;
	}

	void addPoint(){
		points++;
	}

	public String getName() {
		return name;
	}

	public int getPoints() {
		return points;
	}

	public PlayerStatus getStatus() {
		return status;
	}

	public void setStatus(PlayerStatus status) {
		this.status = status;
	}
}
