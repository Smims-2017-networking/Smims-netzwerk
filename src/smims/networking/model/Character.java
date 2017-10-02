package smims.networking.model;

public class Character implements ICharacter {
	private int position;
	private final Player player;
	
	public Character(Player player) {
		this.player = player; 
	}
	
	
	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public Player getPlayer() {
		return null;
	}

	@Override
	public void setPosition(int value) {
		position = value;
	}
}
