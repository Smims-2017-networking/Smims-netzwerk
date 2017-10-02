package smims.networking.model;

public class Character implements ICharacter {
	
	private Position position;
	private final Player player;
	
	public Character(Player player) {
		this.player = player; 
	}
	
	
	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public void setPosition(Position p) {
		position = p;
	}
	
	public boolean canMove() {
		boolean re=true;
		if(position==0) {
			re=false;
		}
		return re;
	}
}
