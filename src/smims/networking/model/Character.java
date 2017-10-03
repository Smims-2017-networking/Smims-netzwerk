package smims.networking.model;
import smims.networking.model.*;

public class Character{
	
	private Position position = new Position();
	private final Player player;
	private Board b=new Board(null);
	
	public Character(Player player) {
		this.player = player; 
	}
	
	
	public Position getCurrentPosition() {
		return position;
	}
	public Player getPlayer() {
		return player;
	}

	private void setPosition(Position p) {
		position = p;
	}
	
	public boolean isOnField(int pFieldNumber) {
		return getCurrentPosition().isOnField(pFieldNumber);
	}
	
	public boolean isAtStartingPosition() {
		return getCurrentPosition().isAtStartingPosition();
	}
	
	public void moveIntoHouse(int possibleNewDistanz) {
		setPosition(Position.movedIntoHouse(possibleNewDistanz));
	}
	
	public void move(int possibleNewPosition, int possibleNewDistanz) {
		setPosition(getCurrentPosition().movedBy(possibleNewPosition, possibleNewDistanz));
	}
	
	public void moveOutOfBase(int characterSpawnPosition) {
		setPosition(Position.spawnedAt(characterSpawnPosition));
	}
	
	public void werdeGeschlagen() 
	{
		setPosition(Position.thrownOut());
	}
	
	public boolean isInBase() {
		return getCurrentPosition().getStatus() == CharacterStatus.BASE;
	}
	
	public boolean canMove(int dist) {
		boolean re=true;
		if(b.characterCanMove(this, dist)) {
			re=false;
		}
		return re;
	}
}
